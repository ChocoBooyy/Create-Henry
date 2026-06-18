package com.chocoboy.create_henry.content.recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.world.level.Level;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.RecipeWrapper;

public abstract class HenryFanProcessingRecipe extends ProcessingRecipe<HenryFanProcessingRecipe.Wrapper> {

    protected HenryFanProcessingRecipe(IRecipeTypeInfo recipeType, ProcessingRecipeParams params) {
        super(recipeType, params);
    }

    @Override
    public boolean matches(Wrapper inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return ingredients.get(0).test(inv.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 12;
    }

    public static class Wrapper extends RecipeWrapper {
        public Wrapper() {
            super(new ItemStackHandler(1));
        }
    }
}
