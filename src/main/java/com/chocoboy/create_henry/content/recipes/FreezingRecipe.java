package com.chocoboy.create_henry.content.recipes;

import com.chocoboy.create_henry.registry.HenryRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;

public class FreezingRecipe extends HenryFanProcessingRecipe {

    public FreezingRecipe(ProcessingRecipeParams params) {
        super(HenryRecipeTypes.FREEZING, params);
    }

}
