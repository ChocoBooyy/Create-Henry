package com.chocoboy.create_henry.content.blocks.kinetics.multimeter;

import com.chocoboy.create_henry.registry.HenryPartialModels;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MultiMeterVisual extends ShaftVisual<MultiMeterBlockEntity> implements SimpleDynamicVisual {

    // Two dials per exposed face: index 0/1 = face A speed/stress, index 2/3 = face B speed/stress
    private final List<TransformedInstance> speedDials = new ArrayList<>();
    private final List<TransformedInstance> stressDials = new ArrayList<>();
    private final List<Direction> dialFacings = new ArrayList<>();


    public MultiMeterVisual(VisualizationContext context, MultiMeterBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        Level realLevel = blockEntity.getLevel();
        if (realLevel == null) return;

        MultiMeterBlock block = (MultiMeterBlock) blockState.getBlock();

        for (Direction facing : Iterate.directions) {
            if (!block.shouldRenderHeadOnFace(realLevel, pos, blockState, facing)) continue;

            TransformedInstance speed = instancerProvider()
                    .instancer(InstanceTypes.TRANSFORMED, Models.partial(HenryPartialModels.GAUGE_SPEED_DIAL))
                    .createInstance();
            TransformedInstance stress = instancerProvider()
                    .instancer(InstanceTypes.TRANSFORMED, Models.partial(HenryPartialModels.GAUGE_STRESS_DIAL))
                    .createInstance();

            speedDials.add(speed);
            stressDials.add(stress);
            dialFacings.add(facing);
        }

        applyDialTransforms(partialTick);
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        applyDialTransforms(ctx.partialTick());
    }

    private void applyDialTransforms(float pt) {
        float speedProgress  = Mth.lerp(pt, blockEntity.prevDialStateSpeed,  blockEntity.dialStateSpeed);
        float stressProgress = Mth.lerp(pt, blockEntity.prevDialStateStress, blockEntity.dialStateStress);

        for (int i = 0; i < dialFacings.size(); i++) {
            Direction facing = dialFacings.get(i);
            applyDialTransform(speedDials.get(i),  facing, speedProgress,  0f);
            applyDialTransform(stressDials.get(i), facing, stressProgress, 0.002f);
        }
    }

    private void applyDialTransform(TransformedInstance dial, Direction facing, float progress, float outwardBias) {
        float dialPivot = 5.75f / 16;
        float dialAngle = (float) (Math.PI / 2 * -progress);
        net.minecraft.core.Vec3i n = facing.getNormal();

        // bias applied in world space before any rotation, so it correctly follows the face normal
        dial.setIdentityTransform()
                .translate(getVisualPosition())
                .translate(n.getX() * outwardBias, n.getY() * outwardBias, n.getZ() * outwardBias)
                .center()
                .rotateYDegrees(-facing.toYRot() - 90)
                .uncenter()
                .translate(0, dialPivot, dialPivot)
                .rotateX(dialAngle)
                .translate(0, -dialPivot, -dialPivot)
                .setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);
        for (TransformedInstance d : speedDials)  relight(d);
        for (TransformedInstance d : stressDials) relight(d);
    }

    @Override
    protected void _delete() {
        super._delete();
        for (TransformedInstance d : speedDials)  d.delete();
        for (TransformedInstance d : stressDials) d.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);
        speedDials.forEach(consumer);
        stressDials.forEach(consumer);
    }
}
