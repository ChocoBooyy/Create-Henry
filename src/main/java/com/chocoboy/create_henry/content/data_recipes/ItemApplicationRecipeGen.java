package com.chocoboy.create_henry.content.data_recipes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import com.chocoboy.create_henry.registry.HenryBlocks;
import com.chocoboy.create_henry.registry.HenryItems;

import static com.chocoboy.create_henry.registry.HenryTags.forgeItemTag;

@SuppressWarnings({"unused"})
public class ItemApplicationRecipeGen extends HenryProcessingRecipeGen {
    public ItemApplicationRecipeGen(PackOutput generator) {
        super(generator);
    }

    GeneratedRecipe INDUSTRIAL = create("industrial_casing", b -> b
            .require(AllBlocks.INDUSTRIAL_IRON_BLOCK.get()) //Block
            .require(AllItems.ZINC_INGOT.get()) //Item to Apply
            .output(HenryBlocks.INDUSTRIAL_CASING.get())); //Output

    GeneratedRecipe HYDRAULIC = create("hydraulic_casing", b -> b
            .require(AllBlocks.COPPER_CASING.get()) //Block
            .require(AllItems.COPPER_SHEET.get()) //Item to Apply
            .output(HenryBlocks.HYDRAULIC_CASING.get())); //Output

    GeneratedRecipe COGWHEEL = create("cogwheel", b -> b
            .require(AllBlocks.SHAFT.get()) //Block
            .require(ItemTags.PLANKS) //Item to Apply
            .output(AllBlocks.COGWHEEL.get())); //Output

    GeneratedRecipe LARGE_COGWHEEL = create("large_cogwheel", b -> b
            .require(AllBlocks.COGWHEEL.get()) //Block
            .require(ItemTags.PLANKS) //Item to Apply
            .output(AllBlocks.LARGE_COGWHEEL.get())); //Output

    GeneratedRecipe DOWNGRADE_COGWHEEL = create("downgrade_cogwheel", b -> b
            .require(AllBlocks.COGWHEEL.get()) //Block
            .require(forgeItemTag("nuggets/coal")) //Item to Apply
            .output(AllBlocks.SHAFT.get())); //Output

    GeneratedRecipe DOWNGRADE_LARGE_COGWHEEL = create("downgrade_large_cogwheel", b -> b
            .require(AllBlocks.LARGE_COGWHEEL.get()) //Block
            .require(forgeItemTag("nuggets/coal")) //Item to Apply
            .output(AllBlocks.COGWHEEL.get())); //Output


    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.ITEM_APPLICATION;
    }
}