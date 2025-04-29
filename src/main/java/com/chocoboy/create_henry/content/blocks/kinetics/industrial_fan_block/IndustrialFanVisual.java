package com.chocoboy.create_henry.content.blocks.kinetics.industrial_fan_block;

import java.util.function.Consumer;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import com.chocoboy.create_henry.registry.HenryPartialModels;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.lib.instance.FlatLit;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import com.simibubi.create.content.kinetics.base.RotatingInstance;

public class IndustrialFanVisual extends KineticBlockEntityVisual<IndustrialFanBlockEntity> {

    private final RotatingInstance shaft;
    private final RotatingInstance fan;
    private final Direction direction;
    private final Direction opposite;

    public IndustrialFanVisual(VisualizationContext context, IndustrialFanBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        // Retrieve the facing direction from the block state.
        direction = blockState.getValue(BlockStateProperties.FACING);
        opposite = direction.getOpposite();

        // Create visual instances using the new instancer provider.
        shaft = instancerProvider()
                .instancer(AllInstanceTypes.ROTATING, Models.partial(HenryPartialModels.INDUSTRIAL_FAN_POWER))
                .createInstance();

        fan = instancerProvider()
                .instancer(AllInstanceTypes.ROTATING, Models.partial(HenryPartialModels.INDUSTRIAL_FAN_INNER))
                .createInstance();

        // Set up the instances with their initial speeds and orientation.
        shaft.setup(blockEntity, blockEntity.getSpeed())
                .setPosition(getVisualPosition())
                .rotateToFace(Direction.SOUTH, opposite)
                .setChanged();

        fan.setup(blockEntity, getFanSpeed())
                .setPosition(getVisualPosition())
                .rotateToFace(Direction.SOUTH, opposite)
                .setChanged();
    }

    /**
     * Calculate the fan speed based on the block entity’s speed.
     */
    private float getFanSpeed() {
        float speed = blockEntity.getSpeed() * 5;
        if (speed > 0)
            speed = Mth.clamp(speed, 80, 64 * 20);
        if (speed < 0)
            speed = Mth.clamp(speed, -64 * 20, -80);
        return speed;
    }

    @Override
    public void update(float pt) {
        // Update the rotation of both instances.
        shaft.setup(blockEntity, blockEntity.getSpeed()).setChanged();
        fan.setup(blockEntity, getFanSpeed()).setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        // Update lighting for both instances together.
        relight(new FlatLit[]{shaft, fan});
    }

    @Override
    protected void _delete() {
        // Delete both instances and clear any references.
        shaft.delete();
        fan.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        // Provide both instances for crumbling effects.
        consumer.accept(shaft);
        consumer.accept(fan);
    }
}
