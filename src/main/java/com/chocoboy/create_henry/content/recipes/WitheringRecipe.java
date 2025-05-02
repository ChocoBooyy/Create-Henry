package com.chocoboy.create_henry.content.recipes;

import com.chocoboy.create_henry.registry.HenryRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class WitheringRecipe extends ProcessingRecipe<WitheringRecipe.WitheringWrapper> {

    public WitheringRecipe(ProcessingRecipeParams params) {
        super(HenryRecipeTypes.WITHERING, params);
    }

    @Override
    public boolean matches(WitheringWrapper inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return ingredients.get(0)
                .test(inv.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 12;
    }

    public static class WitheringWrapper extends RecipeWrapper {
        public WitheringWrapper() {
            super(new ItemStackHandler(1));
        }
    }

}
