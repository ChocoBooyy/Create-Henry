package com.chocoboy.create_henry.content.blocks.kinetics.industrial_brake;

import dev.engine_room.flywheel.lib.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.math.AngleHelper;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class IndustrialBrakeBlockEntity extends GeneratingKineticBlockEntity {
    public static final int MAX_STRESS_PER_RPM = 256;

    protected ScrollValueBehaviour impactValue;

    public IndustrialBrakeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);

        this.impactValue = new IndustrialBrakeScrollValueBehaviour(
                Component.literal("SU Drawn"),
                this,
                new MotorValueBox()
        );
        this.impactValue.between(0, MAX_STRESS_PER_RPM);
        this.impactValue.value = 0; // Default value
        this.impactValue.withCallback(v -> {
            this.updateGeneratedRotation();
            this.getOrCreateNetwork().updateStressFor(this, calculateStressApplied());
            this.getOrCreateNetwork().updateStress();
        });
        this.impactValue.withCallback((i) -> {
            this.updateGeneratedRotation();
        });
        behaviours.add(this.impactValue);
    }

    @Override
    public void initialize() {
        super.initialize();
        if (!this.hasSource() && this.impactValue != null && this.impactValue.getValue() > 0) {
            this.reActivateSource = true;
            this.updateGeneratedRotation();
        }
    }

    @Override
    public void remove() {
        if (level != null && hasNetwork()) {
            KineticNetwork net = getOrCreateNetwork();
            if (net != null) net.updateStressFor(this, 0);
            if (net != null) net.updateStress();
        }
        super.remove();
    }

    @Override
    public float calculateAddedStressCapacity() {
        return 0;
    }

    @Override
    public float calculateStressApplied() {
        float draw = impactValue.getValue();
        float speed = Math.abs(getTheoreticalSpeed());
        if (speed == 0) return 0;
        return draw / speed; // Divide the scroll-picked draw by the RPM to normalize per update
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if (!IRotate.StressImpact.isEnabled())
            return super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        int draw = impactValue.getValue();
        if (draw == 0)
            return super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        CreateLang.translate("gui.goggles.generator_stats").forGoggles(tooltip);
        CreateLang.translate("tooltip.stressImpact")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        CreateLang.number(draw)
                .translate("generic.unit.stress")
                .style(ChatFormatting.RED)
                .space()
                .add(Component.literal("at current selected stress")
                        .withStyle(ChatFormatting.DARK_GRAY))
                .forGoggles(tooltip, 1);

        return true;
    }

    class MotorValueBox extends ValueBoxTransform.Sided {

        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 8, 12.5);
        }

        @Override
        public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
            Direction facing = state.getValue(IndustrialBrakeBlock.FACING);
            return super.getLocalOffset(level, pos, state).add(Vec3.atLowerCornerOf(facing.getNormal())
                    .scale(-1 / 16f));
        }

        @Override
        public void rotate(LevelAccessor level, BlockPos pos, BlockState state, PoseStack ms) {
            super.rotate(level, pos, state, ms);
            Direction facing = state.getValue(IndustrialBrakeBlock.FACING);
            if (facing.getAxis() == Axis.Y)
                return;
            if (getSide() != Direction.UP)
                return;
            TransformStack.of(ms)
                    .rotateZDegrees(-AngleHelper.horizontalAngle(facing) + 180);
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            Direction facing = state.getValue(IndustrialBrakeBlock.FACING);
            if (facing.getAxis() != Axis.Y && direction == Direction.DOWN)
                return false;
            return direction.getAxis() != facing.getAxis();
        }

    }
}