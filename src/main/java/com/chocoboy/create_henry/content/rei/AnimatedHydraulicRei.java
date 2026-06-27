package com.chocoboy.create_henry.content.rei;

import com.chocoboy.create_henry.registry.HenryBlocks;
import com.chocoboy.create_henry.registry.HenryPartialModels;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.rei.category.animations.AnimatedKinetics;

import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;

/**
 * REI version of Henry's animated Hydraulic Press, mirroring Create's REI AnimatedPress.
 */
public class AnimatedHydraulicRei extends AnimatedKinetics {

    private final boolean basin;

    public AnimatedHydraulicRei(boolean basin) {
        this.basin = basin;
    }

    @Override
    public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
        var matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 200);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));

        int scale = basin ? 23 : 24;

        blockElement(shaft(Direction.Axis.Z))
                .rotateBlock(0, 0, getCurrentAngle())
                .scale(scale)
                .render(graphics);

        blockElement(HenryBlocks.HYDRAULIC_PRESS.getDefaultState())
                .scale(scale)
                .render(graphics);

        blockElement(HenryPartialModels.HYDRAULIC_PRESS_HEAD)
                .atLocal(0, -getAnimatedHeadOffset(), 0)
                .scale(scale)
                .render(graphics);

        if (basin)
            blockElement(AllBlocks.BASIN.getDefaultState())
                    .atLocal(0, 1.65, 0)
                    .scale(scale)
                    .render(graphics);

        matrixStack.popPose();
    }

    private float getAnimatedHeadOffset() {
        float cycle = (AnimationTickHolder.getRenderTime() - offset * 8) % 30;
        if (cycle < 10) {
            float progress = cycle / 10f;
            return -(progress * progress * progress);
        }
        if (cycle < 15)
            return -1;
        if (cycle < 20)
            return -1 + (1 - ((20 - cycle) / 5f));
        return 0;
    }
}
