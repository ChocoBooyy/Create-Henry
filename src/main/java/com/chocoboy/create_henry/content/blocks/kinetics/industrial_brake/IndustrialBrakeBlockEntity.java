package com.chocoboy.create_henry.content.blocks.kinetics.industrial_brake;

import com.chocoboy.create_henry.content.blocks.kinetics.DirectionalMotorValueBox;
import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class IndustrialBrakeBlockEntity extends GeneratingKineticBlockEntity {

    public static final int MAX_STRESS_PER_RPM = 256;

    protected ScrollValueBehaviour impactValue;

    public IndustrialBrakeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);

        impactValue = new IndustrialBrakeScrollValueBehaviour(
                Component.literal("SU Drawn"),
                this,
                new DirectionalMotorValueBox()
        );
        impactValue.between(0, MAX_STRESS_PER_RPM);
        impactValue.value = 0;
        impactValue.withCallback(v -> updateDrawnStress());
        behaviours.add(impactValue);
    }

    // initialize() intentionally not overridden — a brake is never a rotation source,
    // so setting reActivateSource would incorrectly try to drive the network.

    @Override
    public void onSpeedChanged(float prevSpeed) {
        super.onSpeedChanged(prevSpeed);
        // calculateStressApplied() is speed-dependent (draw / speed), so the network's stored
        // SU/RPM value must be refreshed whenever shaft speed changes, otherwise the actual
        // SU drain diverges from the intended flat draw value.
        updateDrawnStress();
    }

    // Pushes the current draw into the network. Required because updateGeneratedRotation()
    // only refreshes stress when getGeneratedSpeed() != 0, and a brake never generates speed.
    private void updateDrawnStress() {
        if (level == null || level.isClientSide || !hasNetwork())
            return;
        KineticNetwork net = getOrCreateNetwork();
        if (net == null)
            return;
        net.updateStressFor(this, calculateStressApplied());
        net.updateStress();
        sendData();
    }

    @Override
    public void remove() {
        if (level != null && hasNetwork()) {
            KineticNetwork net = getOrCreateNetwork();
            if (net != null) {
                net.updateStressFor(this, 0);
                net.updateStress();
            }
        }
        super.remove();
    }

    @Override
    public float calculateAddedStressCapacity() {
        return 0;
    }

    @Override
    public float calculateStressApplied() {
        if (impactValue == null) return 0;
        float draw = impactValue.getValue();
        float speed = Math.abs(getTheoreticalSpeed());
        if (speed == 0) return 0;
        return draw / speed;
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
                .add(Component.literal("at current selected stress").withStyle(ChatFormatting.DARK_GRAY))
                .forGoggles(tooltip, 1);

        return true;
    }
}
