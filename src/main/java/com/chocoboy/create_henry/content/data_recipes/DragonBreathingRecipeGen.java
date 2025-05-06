package com.chocoboy.create_henry.content.data_recipes;

import com.chocoboy.create_henry.registry.HenryRecipeTypes;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

@SuppressWarnings({"unused"})
public class DragonBreathingRecipeGen extends HenryProcessingRecipeGen{

    GeneratedRecipe

            CHORUS_FRUIT = create(() -> Items.APPLE, b -> b.output(.50f, Items.CHORUS_FRUIT)),
            ENDER_PEARL = create(() -> Items.MAGMA_CREAM, b -> b.output(.15f, Items.ENDER_PEARL)),
            GHAST_TEAR = create(() -> Items.BLUE_ICE, b -> b.output(.10f, Items.GHAST_TEAR).output(0.05f, Items.GHAST_TEAR)),

            DRAGON_BREATH = convert(Items.POTION, Items.DRAGON_BREATH),
            END_STONE = convert(Items.DEEPSLATE, Items.END_STONE),
            CRYING_OBSIDIAN = convert(Items.OBSIDIAN, Items.CRYING_OBSIDIAN),
            END_ROD = convert(Items.BLAZE_ROD, Items.END_ROD);

    public CreateRecipeProvider.GeneratedRecipe convert(Item item, Item result) {
        return create(() -> item, b -> b.output(result));
    }

    public DragonBreathingRecipeGen(PackOutput dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected HenryRecipeTypes getRecipeType() {return HenryRecipeTypes.DRAGON_BREATHING;}
}