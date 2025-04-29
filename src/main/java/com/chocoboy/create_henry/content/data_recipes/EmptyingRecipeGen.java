package com.chocoboy.create_henry.content.data_recipes;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import com.chocoboy.create_henry.registry.HenryFluids;

import static  com.chocoboy.create_henry.registry.HenryTags.forgeItemTag;

@SuppressWarnings({"unused", "all"})
public class EmptyingRecipeGen extends HenryProcessingRecipeGen {


    GeneratedRecipe

    SAP_LOGS = create("sap_from_logs", b -> b.require(forgeItemTag("stripped_logs"))
            .output(HenryFluids.SAP.get(), 100)
    ),

    SAP_WOOD = create("sap_from_wood", b -> b.require(forgeItemTag("stripped_wood"))
            .output(HenryFluids.SAP.get(), 100)
    );


    public EmptyingRecipeGen(PackOutput generator) {
        super(generator);
    }

    @Override
    protected @NotNull IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.EMPTYING;
    }
}