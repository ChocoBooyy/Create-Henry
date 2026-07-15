package com.chocoboy.create_henry.content.blocks.kinetics.multimeter;

import com.chocoboy.create_henry.registry.HenryPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class MultiMeterRenderer extends ShaftRenderer<MultiMeterBlockEntity> {

    public MultiMeterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(MultiMeterBlockEntity be, float partialTick, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (VisualizationManager.supportsVisualization(be.getLevel())) return;
        super.renderSafe(be, partialTick, ms, buffer, light, overlay);

        BlockState state = be.getBlockState();
        MultiMeterBlock block = (MultiMeterBlock) state.getBlock();

        SuperByteBuffer head = CachedBuffers.partial(HenryPartialModels.GAUGE_HEAD, state);
        SuperByteBuffer speedDial = CachedBuffers.partial(HenryPartialModels.GAUGE_SPEED_DIAL, state);
        SuperByteBuffer stressDial = CachedBuffers.partial(HenryPartialModels.GAUGE_STRESS_DIAL, state);

        float pivot = 5.75f / 16f;
        float speedProgress = Mth.lerp(partialTick, be.prevDialStateSpeed, be.dialStateSpeed);
        float stressProgress = Mth.lerp(partialTick, be.prevDialStateStress, be.dialStateStress);

        for (Direction face : Iterate.directions) {
            if (!block.shouldRenderHeadOnFace(be.getLevel(), be.getBlockPos(), state, face)) continue;

            VertexConsumer vb = buffer.getBuffer(RenderType.solid());
            head.rotateCentered((-face.toYRot() - 90) / 180f * (float) Math.PI, Direction.UP)
                    .light(light)
                    .renderInto(ms, vb);
            renderDial(speedDial, face, pivot, speedProgress, light, ms, vb);
            renderDial(stressDial, face, pivot, stressProgress, light, ms, vb);
        }
    }

    private static void renderDial(SuperByteBuffer buf, Direction face, float pivot, float progress,  int light, PoseStack ms, VertexConsumer vb) {
        float rotAngle = (-face.toYRot() - 90) / 180f * (float) Math.PI;
        float dialAngle = (float) (Math.PI / 2 * -progress);
        buf.rotateCentered(rotAngle, Direction.UP)
                .translate(0, pivot, pivot)
                .rotate(dialAngle, Direction.EAST)
                .translate(0, -pivot, -pivot)
                .light(light)
                .renderInto(ms, vb);
    }
}
