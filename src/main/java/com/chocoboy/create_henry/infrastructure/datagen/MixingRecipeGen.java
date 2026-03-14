package com.chocoboy.create_henry.infrastructure.datagen;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import com.chocoboy.create_henry.registry.HenryFluids;
import com.chocoboy.create_henry.registry.HenryItems;
import net.minecraftforge.common.Tags;

import static com.tterrag.registrate.util.DataIngredient.items;

@SuppressWarnings({"unused", "all"})
public class MixingRecipeGen extends HenryProcessingRecipeGen {

    GeneratedRecipe

    // Liquids
    RAW_RUBBER = create("raw_rubber", b -> b
            .require(HenryFluids.SAP.get(), 500)
            .output(HenryItems.RAW_RUBBER.get(), 1)
    ),

    CHOCOLATE_MILKSHAKE = create("chocolate_milkshake", b -> {
        b.require(AllFluids.CHOCOLATE.get(), 250);
        require(b, Items.SUGAR, 2);
        require(b, Items.SNOWBALL, 4);
        require(b, AllItems.BAR_OF_CHOCOLATE, 2);
        return b.output(HenryFluids.CHOCOLATE_MILKSHAKE.get(), 500);
    }),

    VANILLA_MILKSHAKE = create("vanilla_milkshake", b -> {
        b.require(Tags.Fluids.MILK, 250);
        require(b, Items.SUGAR, 2);
        require(b, Items.SNOWBALL, 4);
        require(b, items(Items.DANDELION, Items.BLUE_ORCHID), 2);
        return b.output(HenryFluids.VANILLA_MILKSHAKE.get(), 500);
    }),

    STRAWBERRY_MILKSHAKE = create("strawberry_milkshake", b -> {
        b.require(Tags.Fluids.MILK, 250);
        require(b, Items.SUGAR, 2);
        require(b, Items.SNOWBALL, 4);
        require(b, Items.SWEET_BERRIES, 2);
        return b.output(HenryFluids.STRAWBERRY_MILKSHAKE.get(), 500);
    }),

    GLOWBERRY_MILKSHAKE = create("glowberry_milkshake", b -> {
        b.require(Tags.Fluids.MILK, 250);
        require(b, Items.SUGAR, 2);
        require(b, Items.SNOWBALL, 4);
        require(b, Items.GLOW_BERRIES, 2);
        return b.output(HenryFluids.GLOWBERRY_MILKSHAKE.get(), 500);
    }),

    PUMPKIN_MILKSHAKE = create("pumpkin_milkshake", b -> {
        b.require(Tags.Fluids.MILK, 250);
        require(b, Items.SUGAR, 2);
        require(b, Items.SNOWBALL, 4);
        require(b, Items.PUMPKIN, 2);
        return b.output(HenryFluids.PUMPKIN_MILKSHAKE.get(), 500);
    }),

    // Blocks

    CALCITE = create("calcite", b -> b
            .require(Items.DIORITE)
            .require(Items.BONE_BLOCK)
            .output(Items.CALCITE, 2)
            .requiresHeat(HeatCondition.HEATED)
    );

    public MixingRecipeGen(PackOutput generator) {
        super(generator);
    }

    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.MIXING;
    }
}
