package com.chocoboy.create_henry.content.blocks.kinetics.kinetic_motor;

import com.chocoboy.create_henry.content.blocks.kinetics.DirectionalMotorValueBox;
import com.chocoboy.create_henry.registry.HenryBlocks;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class KineticMotorBlockEntity extends GeneratingKineticBlockEntity {

    protected ScrollValueBehaviour generatedSpeed;

    public KineticMotorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        int max = 32;
        generatedSpeed = new KineticMotorScrollValueBehaviour(
                CreateLang.translateDirect("kinetics.creative_motor.rotation_speed"),
                this,
                new DirectionalMotorValueBox()
        );
        generatedSpeed.between(-max, max);
        generatedSpeed.value = 16;
        generatedSpeed.withCallback(i -> updateGeneratedRotation());
        behaviours.add(generatedSpeed);
    }

    @Override
    public void initialize() {
        super.initialize();
        if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed())
            updateGeneratedRotation();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if (!IRotate.StressImpact.isEnabled())
            return super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        float stressBase = calculateAddedStressCapacity();
        if (Mth.equal(stressBase, 0))
            return super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        float stressTotal = Math.abs(stressBase * getTheoreticalSpeed());

        CreateLang.translate("gui.goggles.generator_stats").forGoggles(tooltip);
        CreateLang.translate("tooltip.capacityProvided")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);
        CreateLang.number(stressTotal)
                .translate("generic.unit.stress")
                .style(ChatFormatting.AQUA)
                .space()
                .add(CreateLang.translate("gui.goggles.at_current_speed").style(ChatFormatting.DARK_GRAY))
                .forGoggles(tooltip, 1);

        return true;
    }

    public float getGeneratedSpeed() {
        if (!HenryBlocks.KINETIC_MOTOR.has(getBlockState()))
            return 0;
        return convertToDirection(generatedSpeed.getValue(), (Direction) getBlockState().getValue(KineticMotorBlock.FACING));
    }
}
