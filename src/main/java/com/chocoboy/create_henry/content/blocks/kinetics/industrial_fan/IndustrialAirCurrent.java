package com.chocoboy.create_henry.content.blocks.kinetics.industrial_fan;

import com.chocoboy.create_henry.infrastructure.config.HenryConfigs;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour.TransportedResult;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.fan.AirCurrent;
import com.simibubi.create.content.kinetics.fan.IAirCurrentSource;
import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessing;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IndustrialAirCurrent extends AirCurrent {

    public IndustrialAirCurrent(IAirCurrentSource source) {
        super(source);
    }

    @Override
    protected void tickAffectedEntities(Level world) {
        if (world == null || world.isClientSide) {
            super.tickAffectedEntities(world);
            return;
        }

        for (Iterator<Entity> iterator = caughtEntities.iterator(); iterator.hasNext(); ) {
            Entity entity = iterator.next();
            if (!entity.isAlive() || !entity.getBoundingBox()
                    .intersects(bounds) || isPlayerCreativeFlying(entity)) {
                iterator.remove();
                continue;
            }

            Vec3i flow = (pushing ? direction : direction.getOpposite()).getNormal();
            float speed = Math.abs(source.getSpeed());
            float sneakModifier = entity.isShiftKeyDown() ? 4096f : 512f;
            double entityDistance = VecHelper.alignedDistanceToFace(entity.position(), source.getAirCurrentPos(), direction);
            double entityDistanceOld = entity.position().distanceTo(VecHelper.getCenterOf(source.getAirCurrentPos()));
            float acceleration = (float) (speed / sneakModifier / (entityDistanceOld / maxDistance));
            Vec3 previousMotion = entity.getDeltaMovement();
            float maxAcceleration = 5;

            double xIn = Mth.clamp(flow.getX() * acceleration - previousMotion.x, -maxAcceleration, maxAcceleration);
            double yIn = Mth.clamp(flow.getY() * acceleration - previousMotion.y, -maxAcceleration, maxAcceleration);
            double zIn = Mth.clamp(flow.getZ() * acceleration - previousMotion.z, -maxAcceleration, maxAcceleration);

            entity.setDeltaMovement(previousMotion.add(new Vec3(xIn, yIn, zIn).scale(1 / 8f)));
            entity.fallDistance = 0;

            if (entity instanceof ServerPlayer serverPlayer)
                serverPlayer.connection.aboveGroundTickCount = 0;

            FanProcessingType processingType = getTypeAt((float) entityDistance);

            if (processingType == null)
                continue;

            if (entity instanceof ItemEntity itemEntity) {
                if (FanProcessing.canProcess(itemEntity, processingType))
                    if (applyProcessing(itemEntity, processingType) && source instanceof IndustrialFanBlockEntity fan)
                        fan.award(AllAdvancements.FAN_PROCESSING);
                continue;
            }

            processingType.affectEntity(entity, world);
        }
    }

    @Override
    public void tickAffectedHandlers() {
        for (Pair<TransportedItemStackHandlerBehaviour, FanProcessingType> pair : affectedItemHandlers) {
            TransportedItemStackHandlerBehaviour handler = pair.getKey();
            Level world = handler.getWorld();
            FanProcessingType processingType = pair.getRight();
            if (processingType == null)
                continue;

            handler.handleProcessingOnAllItems(transported -> {
                if (world.isClientSide) {
                    processingType.spawnProcessingParticles(world, handler.getWorldPositionOf(transported));
                    return TransportedResult.doNothing();
                }
                TransportedResult applyProcessing = applyProcessing(transported, world, processingType);
                if (!applyProcessing.doesNothing() && source instanceof IndustrialFanBlockEntity fan)
                    fan.award(AllAdvancements.FAN_PROCESSING);
                return applyProcessing;
            });
        }
    }

    private static int processingTime(int stackCount) {
        int timeModifierForStackSize = ((stackCount - 1) / 16) + 1;
        return HenryConfigs.server().kinetics.fanProcessingTime.get() * timeModifierForStackSize + 1;
    }

    private static boolean applyProcessing(ItemEntity entity, FanProcessingType type) {
        if (decrementProcessingTime(entity, type) != 0)
            return false;
        List<ItemStack> stacks = type.process(entity.getItem(), entity.level());
        if (stacks == null)
            return false;
        if (stacks.isEmpty()) {
            entity.discard();
            return false;
        }
        entity.setItem(stacks.remove(0));
        for (ItemStack additional : stacks) {
            ItemEntity entityIn = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), additional);
            entityIn.setDeltaMovement(entity.getDeltaMovement());
            entity.level().addFreshEntity(entityIn);
        }
        return true;
    }

    private static TransportedResult applyProcessing(TransportedItemStack transported, Level world, FanProcessingType type) {
        TransportedResult ignore = TransportedResult.doNothing();
        if (transported.processedBy != type) {
            transported.processedBy = type;
            transported.processingTime = processingTime(transported.stack.getCount());
            if (!type.canProcess(transported.stack, world))
                transported.processingTime = -1;
            return ignore;
        }
        if (transported.processingTime == -1)
            return ignore;
        if (transported.processingTime-- > 0)
            return ignore;

        List<ItemStack> stacks = type.process(transported.stack, world);
        if (stacks == null)
            return ignore;

        List<TransportedItemStack> transportedStacks = new ArrayList<>();
        for (ItemStack additional : stacks) {
            TransportedItemStack newTransported = transported.getSimilar();
            newTransported.stack = additional.copy();
            transportedStacks.add(newTransported);
        }
        return TransportedResult.convertTo(transportedStacks);
    }

    private static int decrementProcessingTime(ItemEntity entity, FanProcessingType type) {
        CompoundTag nbt = entity.getCustomData();

        if (!nbt.contains("CreateData"))
            nbt.put("CreateData", new CompoundTag());
        CompoundTag createData = nbt.getCompound("CreateData");

        if (!createData.contains("Processing"))
            createData.put("Processing", new CompoundTag());
        CompoundTag processing = createData.getCompound("Processing");

        if (!processing.contains("Type") || AllFanProcessingTypes.parseLegacy(processing.getString("Type")) != type) {
            ResourceLocation key = CreateBuiltInRegistries.FAN_PROCESSING_TYPE.getKey(type);
            if (key == null)
                throw new IllegalArgumentException("Could not get id for FanProcessingType " + type + "!");

            processing.putString("Type", key.toString());
            processing.putInt("Time", processingTime(entity.getItem().getCount()));
        }

        int value = processing.getInt("Time") - 1;
        processing.putInt("Time", value);
        return value;
    }
}
