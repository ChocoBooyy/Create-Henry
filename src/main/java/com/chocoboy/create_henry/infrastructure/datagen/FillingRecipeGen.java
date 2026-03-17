package com.chocoboy.create_henry.infrastructure.datagen;

import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.registry.HenryFluids;
import com.chocoboy.create_henry.registry.HenryItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

@SuppressWarnings("unused")
public final class FillingRecipeGen extends com.simibubi.create.api.data.recipe.FillingRecipeGen {

    GeneratedRecipe CHOCOLATE = create("chocolate", b -> b.require(HenryFluids.CHOCOLATE_MILKSHAKE.get(), 250)
            .require(Items.GLASS_BOTTLE)
            .output(HenryItems.CHOCOLATE_MILKSHAKE)
    );
    GeneratedRecipe VANILLA = create("vanilla", b -> b.require(HenryFluids.VANILLA_MILKSHAKE.get(), 250)
            .require(Items.GLASS_BOTTLE)
            .output(HenryItems.VANILLA_MILKSHAKE)
    );
    GeneratedRecipe STRAWBERRY = create("strawberry", b -> b.require(HenryFluids.STRAWBERRY_MILKSHAKE.get(), 250)
            .require(Items.GLASS_BOTTLE)
            .output(HenryItems.STRAWBERRY_MILKSHAKE)
    );
    GeneratedRecipe GLOWBERRY = create("glowberry", b -> b.require(HenryFluids.GLOWBERRY_MILKSHAKE.get(), 250)
            .require(Items.GLASS_BOTTLE)
            .output(HenryItems.GLOWBERRY_MILKSHAKE)
    );
    GeneratedRecipe PUMPKIN = create("pumpkin", b -> b.require(HenryFluids.PUMPKIN_MILKSHAKE.get(), 250)
            .require(Items.GLASS_BOTTLE)
            .output(HenryItems.PUMPKIN_MILKSHAKE)
    );

    public FillingRecipeGen(PackOutput output) {
        super(output, HenryCreate.MOD_ID);
    }

    public static Ingredient items(ItemLike... items) {
        return Ingredient.of(items);
    }
}
