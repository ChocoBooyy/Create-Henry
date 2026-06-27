package com.chocoboy.create_henry.content.recipeviewer;

import com.chocoboy.create_henry.registry.HenryBlocks;
import com.chocoboy.create_henry.registry.HenryPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.AllSpriteShifts;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.gui.ILightingSettings;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * Block renders shared by Henry's JEI, EMI and REI categories. Each viewer ships its own lighting
 * constant, so it is passed in rather than reached for here, keeping this free of any viewer API.
 */
public final class HenryRecipeRenders {

    private static final int SCALE = 24;

    private HenryRecipeRenders() {}

    // Drawn behind the fan in seething recipes: the blaze burner mid-flare with the scrolling super flame.
    public static void drawSuperheatedBlazeBurner(GuiGraphics graphics, ILightingSettings lighting) {
        PoseStack poseStack = graphics.pose();

        float offsetMain = (Mth.sin(AnimationTickHolder.getRenderTime() / 16f) + 0.5f) / 16f;
        float offset1 = offsetMain * 0.5f;
        float offset2 = offsetMain * -0.5f;
        float offsetHead = offsetMain * 0.25f;

        GuiGameElement.of(AllBlocks.BLAZE_BURNER.getDefaultState())
                .scale(SCALE)
                .atLocal(0, 0, 2)
                .lighting(lighting)
                .render(graphics);

        GuiGameElement.of(AllPartialModels.BLAZE_SUPER)
                .rotate(0, 180, 0)
                .scale(SCALE * 1.1)
                .atLocal(1, 0.1 + offsetHead, 2.65)
                .lighting(lighting)
                .render(graphics);

        GuiGameElement.of(AllPartialModels.BLAZE_BURNER_SUPER_RODS)
                .rotate(0, 180, 0)
                .scale(SCALE)
                .atLocal(1, 0 + offset1, 3)
                .render(graphics);

        GuiGameElement.of(AllPartialModels.BLAZE_BURNER_SUPER_RODS_2)
                .rotate(0, 180, 0)
                .scale(SCALE)
                .atLocal(1, 0.2 + offset2, 3)
                .render(graphics);

        SpriteShiftEntry spriteShift = AllSpriteShifts.SUPER_BURNER_FLAME;

        float spriteWidth = spriteShift.getTarget().getU1() - spriteShift.getTarget().getU0();
        float spriteHeight = spriteShift.getTarget().getV1() - spriteShift.getTarget().getV0();

        float time = AnimationTickHolder.getRenderTime(Minecraft.getInstance().level);
        float speed = 1 / 32f + 1 / 128f;

        double vScroll = speed * time;
        vScroll = vScroll - Math.floor(vScroll);
        vScroll = vScroll * spriteHeight / 2;

        double uScroll = speed * time / 2;
        uScroll = uScroll - Math.floor(uScroll);
        uScroll = uScroll * spriteWidth / 2;

        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());
        CachedBuffers.partial(AllPartialModels.BLAZE_BURNER_FLAME, Blocks.AIR.defaultBlockState())
                .shiftUVScrolling(spriteShift, (float) uScroll, (float) vScroll)
                .light(LightTexture.FULL_BRIGHT)
                .renderInto(poseStack, vb);
    }

    // The hydraulic press driving the basin in hydraulic compacting recipes. EMI positions the
    // drawable itself, so it passes (0, 0); JEI and REI translate to the anchor here.
    public static void drawHydraulicPress(GuiGraphics graphics, ILightingSettings lighting, boolean basin, int xOffset, int yOffset) {
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        matrices.translate(xOffset, yOffset, 200);
        matrices.mulPose(Axis.XP.rotationDegrees(-15.5f));
        matrices.mulPose(Axis.YP.rotationDegrees(22.5f));

        int scale = basin ? 23 : 24;

        block(shaft(), lighting)
                .rotateBlock(0, 0, currentAngle())
                .scale(scale)
                .render(graphics);

        block(HenryBlocks.HYDRAULIC_PRESS.getDefaultState(), lighting)
                .scale(scale)
                .render(graphics);

        block(HenryPartialModels.HYDRAULIC_PRESS_HEAD, lighting)
                .atLocal(0, -animatedHeadOffset(), 0)
                .scale(scale)
                .render(graphics);

        if (basin)
            block(AllBlocks.BASIN.getDefaultState(), lighting)
                    .atLocal(0, 1.65, 0)
                    .scale(scale)
                    .render(graphics);

        matrices.popPose();
    }

    private static GuiGameElement.GuiRenderBuilder block(BlockState state, ILightingSettings lighting) {
        return GuiGameElement.of(state).lighting(lighting);
    }

    private static GuiGameElement.GuiRenderBuilder block(PartialModel model, ILightingSettings lighting) {
        return GuiGameElement.of(model).lighting(lighting);
    }

    private static BlockState shaft() {
        return AllBlocks.SHAFT.getDefaultState().setValue(BlockStateProperties.AXIS, Direction.Axis.Z);
    }

    private static float currentAngle() {
        return (AnimationTickHolder.getRenderTime() * 4f) % 360;
    }

    private static float animatedHeadOffset() {
        float cycle = AnimationTickHolder.getRenderTime() % 30;
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
