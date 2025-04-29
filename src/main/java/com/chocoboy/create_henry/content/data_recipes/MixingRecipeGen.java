package com.chocoboy.create_henry.content.data_recipes;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import com.chocoboy.create_henry.registry.HenryFluids;
import com.chocoboy.create_henry.registry.HenryItems;

@SuppressWarnings({"unused", "all"})
public class MixingRecipeGen extends HenryProcessingRecipeGen {

    GeneratedRecipe

    RAW_RUBBER = create("raw_rubber", b -> b
            .require(HenryFluids.SAP.get(), 500)
            .output(HenryItems.RAW_RUBBER.get(), 1)),

    CALCITE = create("calcite", b -> b
            .require(Items.DIORITE)
            .require(Items.BONE_BLOCK)
            .output(Items.CALCITE, 2)
            .requiresHeat(HeatCondition.HEATED));

    public MixingRecipeGen(PackOutput generator) {
        super(generator);
    }

    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.MIXING;
    }
}