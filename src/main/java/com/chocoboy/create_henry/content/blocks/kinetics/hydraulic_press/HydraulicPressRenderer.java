package com.chocoboy.create_henry.content.blocks.kinetics.hydraulic_press;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import com.chocoboy.create_henry.registry.HenryPartialModels;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class HydraulicPressRenderer extends KineticBlockEntityRenderer<HydraulicPressBlockEntity> {
    public HydraulicPressRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRenderOffScreen(HydraulicPressBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    protected void renderSafe(HydraulicPressBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        if (VisualizationManager.supportsVisualization(be.getLevel()))
            return;

        BlockState blockState = be.getBlockState();
        PressingBehaviour pressingBehaviour = be.getPressingBehaviour();
        float renderedHeadOffset =
                pressingBehaviour.getRenderedHeadOffset(partialTicks) * pressingBehaviour.mode.headOffset;

        SuperByteBuffer headRender = CachedBuffers.partialFacing(HenryPartialModels.HYDRAULIC_PRESS_HEAD, blockState,
                blockState.getValue(HORIZONTAL_FACING));
        headRender.translate(0, (-renderedHeadOffset / 1.2f) - 0.1f, 0)
                .light(light)
                .renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }

    @Override
    protected BlockState getRenderedBlockState(HydraulicPressBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }
}
