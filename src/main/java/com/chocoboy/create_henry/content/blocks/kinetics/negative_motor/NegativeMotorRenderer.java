package com.chocoboy.create_henry.content.blocks.kinetics.negative_motor;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class NegativeMotorRenderer extends KineticBlockEntityRenderer<NegativeMotorBlockEntity> {
    public NegativeMotorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    protected SuperByteBuffer getRotatedModel(NegativeMotorBlockEntity be, BlockState state) {
        return CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, state);
    }
}