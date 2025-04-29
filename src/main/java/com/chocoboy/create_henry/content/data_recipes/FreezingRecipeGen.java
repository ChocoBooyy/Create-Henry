package com.chocoboy.create_henry.content.data_recipes;

import com.chocoboy.create_henry.registry.HenryRecipeTypes;
import com.simibubi.create.AllItems;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;


@SuppressWarnings({"unused"})
public class FreezingRecipeGen extends HenryProcessingRecipeGen {

	GeneratedRecipe

			BLAZE_CAKE = secondaryRecipe(AllItems.BLAZE_CAKE::get, AllItems.POWDERED_OBSIDIAN::get, AllItems.CINDER_FLOUR::get, .90f),
			PACKED_ICE = convert(Items.ICE, Items.PACKED_ICE),
			BLUE_ICE = convert(Items.PACKED_ICE, Items.BLUE_ICE),
			POWDER_SNOW_BUCKET = convert(Items.WATER_BUCKET, Items.POWDER_SNOW_BUCKET),
			SLIME_BALL = convert(Items.MAGMA_CREAM, Items.SLIME_BALL),
			SNOW = convert(Items.SNOWBALL, Items.SNOW),
			SNOW_BLOCK = convert(Items.SNOW, Items.SNOW_BLOCK),
			OBSIDIAN = convert(Items.CRYING_OBSIDIAN, Items.OBSIDIAN);

	;

	public GeneratedRecipe convert(Block block, Block result) {
		return create(() -> block, b -> b.output(result));
	}

	public GeneratedRecipe convert(Item item, Item result) {
		return create(() -> item, b -> b.output(result));
	}

	public GeneratedRecipe convert(Supplier<ItemLike> item, Supplier<ItemLike> result) {
		return create(item, b -> b.output((ItemLike) result));
	}

	public GeneratedRecipe convert(ItemEntry<Item> item, ItemEntry<Item> result) {
		return create(item::get, b -> b.output(result::get));
	}

	public GeneratedRecipe secondaryRecipe(Supplier<ItemLike> item, Supplier<ItemLike> first, Supplier<ItemLike> secondary,
									  float secondaryChance) {
		return create(item, b -> b.output(first.get(), 1)
				.output(secondaryChance, secondary.get(), 1));
	}

	public FreezingRecipeGen(PackOutput dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected HenryRecipeTypes getRecipeType() {
		return HenryRecipeTypes.FREEZING;
	}

}
