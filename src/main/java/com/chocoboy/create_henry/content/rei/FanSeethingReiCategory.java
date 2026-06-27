package com.chocoboy.create_henry.content.rei;

import com.chocoboy.create_henry.content.recipes.SeethingRecipe;
import com.chocoboy.create_henry.content.recipeviewer.HenryRecipeRenders;
import com.simibubi.create.compat.rei.category.ProcessingViaFanCategory;
import com.simibubi.create.compat.rei.category.animations.AnimatedKinetics;

import net.minecraft.client.gui.GuiGraphics;

/**
 * REI display for Henry's seething recipes. Draws the superheated Blaze Burner behind the fan.
 */
public class FanSeethingReiCategory extends ProcessingViaFanCategory.MultiOutput<SeethingRecipe> {

    public FanSeethingReiCategory(Info<SeethingRecipe> info) {
        super(info);
    }

    @Override
    protected void renderAttachedBlock(GuiGraphics graphics) {
        HenryRecipeRenders.drawSuperheatedBlazeBurner(graphics, AnimatedKinetics.DEFAULT_LIGHTING);
    }
}
