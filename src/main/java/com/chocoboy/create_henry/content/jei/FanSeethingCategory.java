package com.chocoboy.create_henry.content.jei;

import com.chocoboy.create_henry.content.recipes.SeethingRecipe;
import com.chocoboy.create_henry.content.recipeviewer.HenryRecipeRenders;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import net.minecraft.client.gui.GuiGraphics;

public class FanSeethingCategory extends HenryFanProcessingCategory.MultiOutput<SeethingRecipe> {

    public FanSeethingCategory(Info<SeethingRecipe> info) {
        super(info);
    }

    @Override
    protected AllGuiTextures getBlockShadow() {
        return AllGuiTextures.JEI_LIGHT;
    }

    @Override
    protected void renderAttachedBlock(GuiGraphics graphics) {
        HenryRecipeRenders.drawSuperheatedBlazeBurner(graphics, AnimatedKinetics.DEFAULT_LIGHTING);
    }
}
