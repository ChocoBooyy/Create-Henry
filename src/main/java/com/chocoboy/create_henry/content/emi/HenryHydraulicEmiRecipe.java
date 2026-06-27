package com.chocoboy.create_henry.content.emi;

import com.chocoboy.create_henry.registry.HenryBlocks;
import com.chocoboy.create_henry.registry.HenryPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.emi.CreateEmiAnimations;
import com.simibubi.create.compat.emi.recipes.basin.BasinEmiRecipe;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.widget.WidgetHolder;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;

/**
 * EMI display for Henry's hydraulic compacting (a basin recipe). Reuses Create's basin layout and
 * draws Henry's animated Hydraulic Press on top, mirroring the JEI HydraulicCategory. The press is
 * rendered inline (not via the JEI AnimatedHydraulic) so this stays free of any JEI API references,
 * which are absent when EMI is the active recipe viewer.
 */
public class HenryHydraulicEmiRecipe extends BasinEmiRecipe {

    public HenryHydraulicEmiRecipe(EmiRecipeCategory category, BasinRecipe recipe) {
        super(category, recipe, true);
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        super.addWidgets(widgets);

        HeatCondition requiredHeat = recipe.getRequiredHeat();
        if (requiredHeat != HeatCondition.NONE) {
            CreateEmiAnimations.addBlazeBurner(widgets, widgets.getWidth() / 2 + 3, 55, requiredHeat.visualizeAsBlazeBurner());
        }
        widgets.addDrawable(widgets.getWidth() / 2 + 3, 40, 0, 0,
                (graphics, mouseX, mouseY, delta) -> renderPress(graphics));
    }

    private static void renderPress(GuiGraphics graphics) {
        PoseStack matrices = graphics.pose();
        matrices.translate(0, 0, 200);
        matrices.mulPose(Axis.XP.rotationDegrees(-15.5f));
        matrices.mulPose(Axis.YP.rotationDegrees(22.5f));
        int scale = 23;

        CreateEmiAnimations.blockElement(CreateEmiAnimations.shaft(Direction.Axis.Z))
                .rotateBlock(0, 0, CreateEmiAnimations.getCurrentAngle())
                .scale(scale)
                .render(graphics);

        CreateEmiAnimations.blockElement(HenryBlocks.HYDRAULIC_PRESS.getDefaultState())
                .scale(scale)
                .render(graphics);

        CreateEmiAnimations.blockElement(HenryPartialModels.HYDRAULIC_PRESS_HEAD)
                .atLocal(0, -getAnimatedHeadOffset(), 0)
                .scale(scale)
                .render(graphics);

        CreateEmiAnimations.blockElement(AllBlocks.BASIN.getDefaultState())
                .atLocal(0, 1.65, 0)
                .scale(scale)
                .render(graphics);
    }

    private static float getAnimatedHeadOffset() {
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
