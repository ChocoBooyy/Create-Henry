package com.chocoboy.create_henry.content.blocks.kinetics.furnace_engine;

import java.util.function.Consumer;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;

public class PoweredFlywheelVisual extends KineticBlockEntityVisual<PoweredFlywheelBlockEntity> implements SimpleDynamicVisual {

    protected final RotatingInstance shaft = this.instancerProvider()
            .instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT))
            .createInstance();
    protected final TransformedInstance wheel;
    protected float lastAngle = Float.NaN;
    protected final Matrix4f baseTransform = new Matrix4f();

    public PoweredFlywheelVisual(VisualizationContext context, PoweredFlywheelBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        var axis = this.rotationAxis();

        this.shaft.setup(this.blockEntity)
                .setPosition(this.getVisualPosition())
                .rotateToFace(axis)
                .setChanged();

        this.wheel = this.instancerProvider()
                .instancer(InstanceTypes.TRANSFORMED, Models.partial(AllPartialModels.FLYWHEEL))
                .createInstance();

        Direction align = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        this.wheel.translate(this.getVisualPosition())
                .center()
                .rotate(new Quaternionf().rotateTo(0, 1, 0, align.getStepX(), align.getStepY(), align.getStepZ()));

        this.baseTransform.set(this.wheel.pose);

        this.animate(this.blockEntity.angle);
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        float partialTicks = ctx.partialTick();
        float speed = this.blockEntity.visualSpeed.getValue(partialTicks) * 3 / 10f;
        float angle = this.blockEntity.angle + speed * partialTicks;

        if (!((double) Math.abs(angle - this.lastAngle) < 0.001)) {
            this.animate(angle);
            this.lastAngle = angle;
        }
    }

    private void animate(float angle) {
        this.wheel.setTransform(this.baseTransform)
                .rotateY(AngleHelper.rad(angle))
                .uncenter()
                .setChanged();
    }

    @Override
    public void update(float pt) {
        this.shaft.setup(this.blockEntity)
                .setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        this.relight(this.shaft, this.wheel);
    }

    @Override
    protected void _delete() {
        this.shaft.delete();
        this.wheel.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(this.shaft);
        consumer.accept(this.wheel);
    }
}
