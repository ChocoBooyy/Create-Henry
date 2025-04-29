package com.chocoboy.create_henry.content.blocks.kinetics.furnace_engine;

import java.util.Objects;
import java.util.function.Consumer;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import com.chocoboy.create_henry.registry.HenryPartialModels;

public class FurnaceEngineVisual extends AbstractBlockEntityVisual<FurnaceEngineBlockEntity> implements SimpleDynamicVisual  {
    protected final TransformedInstance piston;
    protected final TransformedInstance linkage;
    protected final TransformedInstance connector;

    private Float lastAngle = Float.NaN;
    private Axis lastAxis = null;

    public FurnaceEngineVisual(VisualizationContext context, FurnaceEngineBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        this.piston = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(HenryPartialModels.ENGINE_PISTON))
                .createInstance();
        this.linkage = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(HenryPartialModels.ENGINE_LINKAGE))
                .createInstance();
        this.connector = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(HenryPartialModels.ENGINE_CONNECTOR))
                .createInstance();

        animate();
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        animate();
    }

    public void animate() {
        Float angle = blockEntity.getTargetAngle();
        Axis axis = Axis.Y;

        PoweredFlywheelBlockEntity flywheel = this.blockEntity.getFlywheel();
        if (flywheel != null)
            axis = KineticBlockEntityRenderer.getRotationAxisOf(flywheel);

        if (Objects.equals(angle, lastAngle) && lastAxis == axis) {
            return;
        }

        lastAngle = angle;
        lastAxis = axis;

        if (angle == null) {
            this.piston.setVisible(false);
            this.linkage.setVisible(false);
            this.connector.setVisible(false);
            return;
        } else {
            Direction facing = SteamEngineBlock.getFacing(blockState);
            Axis facingAxis = facing.getAxis();

            boolean roll90 = facingAxis.isHorizontal() && axis == Axis.Y || facingAxis.isVertical() && axis == Axis.Z;
            float sine = Mth.sin(angle);
            float sine2 = Mth.sin(angle - 1.5707964F);
            float piston = (1.0F - sine) / 4.0F * 24.0F / 16.0F;

            this.transformed(this.piston, facing, roll90)
                    .translate(0.0, piston, 0.0)
                    .setChanged();

            this.transformed(this.linkage, facing, roll90)
                    .center()
                    .translate(0, 1, 0)
                    .uncenter()
                    .translate(0, piston, 0)
                    .translate(0.0, 0.25, 0.5)
                    .rotateXDegrees(sine2 * 23.0f)
                    .translate(0.0, -0.25, -0.5)
                    .setChanged();

            this.transformed(this.connector, facing, roll90)
                    .translate(0, 2, 0)
                    .center()
                    .rotateX(-angle + Mth.HALF_PI)
                    .uncenter()
                    .setChanged();
        }
    }

    protected TransformedInstance transformed(TransformedInstance modelData, Direction facing, boolean roll90) {
        return modelData.setIdentityTransform()
                .translate(this.getVisualPosition())
                .center()
                .rotateYDegrees(AngleHelper.horizontalAngle(facing))
                .rotateXDegrees(AngleHelper.verticalAngle(facing) + 90.0F)
                .rotateYDegrees((float) (roll90 ? -90.0 : 0.0))
                .uncenter();
    }

    @Override
    public void updateLight(float partialTick) {
        this.relight(this.piston, this.linkage, this.connector);
    }

    @Override
    protected void _delete() {
        this.piston.delete();
        this.linkage.delete();
        this.connector.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        consumer.accept(this.piston);
        consumer.accept(this.linkage);
        consumer.accept(this.connector);
    }
}
