package com.chocoboy.create_henry.content.jei;

import com.chocoboy.create_henry.content.recipes.SeethingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

public class FanSeethingCategory extends HProcessingViaFanCategory.MultiOutput<SeethingRecipe> {

    public FanSeethingCategory(Info<SeethingRecipe> info) {
        super(info);
    }

    @Override
    protected AllGuiTextures getBlockShadow() {
        return AllGuiTextures.JEI_LIGHT;
    }

    @Override
    protected void renderAttachedBlock(GuiGraphics graphics) {
        PoseStack poseStack = graphics.pose();

        float offsetMain = (Mth.sin(AnimationTickHolder.getRenderTime() / 16f) + 0.5f) / 16f;
        float offset1 = offsetMain * 0.5f;
        float offset2 = offsetMain * -0.5f;
        float offsetHead = offsetMain * 0.25f;

        GuiGameElement.of(AllBlocks.BLAZE_BURNER.getDefaultState())
                .scale(SCALE)
                .atLocal(0, 0, 2)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
                .render(graphics);

        GuiGameElement.of(AllPartialModels.BLAZE_SUPER)
                .rotate(0, 180, 0)
                .scale(SCALE * 1.1)
                .atLocal(1, 0.1 + offsetHead, 2.65)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
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

        float spriteWidth = spriteShift.getTarget()
                .getU1()
                - spriteShift.getTarget()
                .getU0();

        float spriteHeight = spriteShift.getTarget()
                .getV1()
                - spriteShift.getTarget()
                .getV0();

        float time = AnimationTickHolder.getRenderTime(Minecraft.getInstance().level);
        float speed = 1 / 32f + 1 / 128f;

        double vScroll = speed * time;
        vScroll = vScroll - Math.floor(vScroll);
        vScroll = vScroll * spriteHeight / 2;

        double uScroll = speed * time / 2;
        uScroll = uScroll - Math.floor(uScroll);
        uScroll = uScroll * spriteWidth / 2;

        Minecraft mc = Minecraft.getInstance();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers()
                .bufferSource();
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());
        CachedBuffers.partial(AllPartialModels.BLAZE_BURNER_FLAME, Blocks.AIR.defaultBlockState())
                .shiftUVScrolling(spriteShift, (float) uScroll, (float) vScroll)
                .light(LightTexture.FULL_BRIGHT)
                .renderInto(poseStack, vb);
    }

}
