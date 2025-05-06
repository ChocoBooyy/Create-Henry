package com.chocoboy.create_henry.content.recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import com.chocoboy.create_henry.registry.HenryRecipeTypes;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DragonBreathingRecipe extends ProcessingRecipe<DragonBreathingRecipe.dragonBreathingWrapper> {

    public DragonBreathingRecipe(ProcessingRecipeParams params) {
        super(HenryRecipeTypes.DRAGON_BREATHING, params);
    }

    @Override
    public boolean matches(dragonBreathingWrapper inv, Level worldIn) {
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

    public static class dragonBreathingWrapper extends RecipeWrapper {
        public dragonBreathingWrapper() {
            super(new ItemStackHandler(1));
        }
    }

}