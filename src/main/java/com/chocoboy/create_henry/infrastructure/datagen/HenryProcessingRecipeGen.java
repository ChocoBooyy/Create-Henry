package com.chocoboy.create_henry.infrastructure.datagen;

import com.chocoboy.create_henry.HenryCreate;
import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

/**
 * Base class for Henry's processing recipe generators.
 * Wraps Create's ProcessingRecipeGen to fix the namespace to this mod id.
 */
public abstract class HenryProcessingRecipeGen extends ProcessingRecipeGen {

    public HenryProcessingRecipeGen(PackOutput output) {
        super(output, HenryCreate.MOD_ID);
    }

    @Override
    protected abstract IRecipeTypeInfo getRecipeType();

    // Quantity helpers

    protected static void require(ProcessingRecipeBuilder<?> b, ItemLike item, int count) {
        for (int i = 0; i < count; i++)
            b.require(item);
    }

    protected static void require(ProcessingRecipeBuilder<?> b, Ingredient ingredient, int count) {
        for (int i = 0; i < count; i++)
            b.require(ingredient);
    }

    // Conversion helpers

    protected GeneratedRecipe convert(ItemLike item, ItemLike result) {
        return create(() -> item, b -> b.output(result));
    }

    protected GeneratedRecipe convert(Supplier<? extends ItemLike> item, Supplier<? extends ItemLike> result) {
        return create(() -> item.get(), b -> b.output(result.get()));
    }

    // Utilities

    public static String getItemName(ItemLike itemLike) {
        return CatnipServices.REGISTRIES.getKeyOrThrow(itemLike.asItem()).getPath();
    }
}
