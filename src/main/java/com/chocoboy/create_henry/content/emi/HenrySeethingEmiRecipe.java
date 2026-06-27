package com.chocoboy.create_henry.content.emi;

import com.chocoboy.create_henry.content.recipeviewer.HenryRecipeRenders;
import com.simibubi.create.compat.emi.CreateEmiAnimations;
import com.simibubi.create.compat.emi.recipes.fan.FanEmiRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;

/**
 * EMI display for Henry's seething recipes. Draws the superheated Blaze Burner behind the fan.
 */
public class HenrySeethingEmiRecipe extends FanEmiRecipe.MultiOutput<ProcessingRecipe<?>> {

    public HenrySeethingEmiRecipe(EmiRecipeCategory category, ProcessingRecipe<?> recipe) {
        super(category, recipe);
    }

    @Override
    protected void renderAttachedBlock(GuiGraphics graphics) {
        HenryRecipeRenders.drawSuperheatedBlazeBurner(graphics, CreateEmiAnimations.DEFAULT_LIGHTING);
    }
}
