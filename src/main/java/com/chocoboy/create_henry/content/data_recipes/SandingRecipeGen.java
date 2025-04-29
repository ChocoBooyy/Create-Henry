package com.chocoboy.create_henry.content.data_recipes;

import com.chocoboy.create_henry.registry.HenryRecipeTypes;
import com.simibubi.create.AllItems;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;


@SuppressWarnings({"unused"})
public class SandingRecipeGen extends HenryProcessingRecipeGen {

	GeneratedRecipe

			COPPER_BLOCK = convert(Blocks.EXPOSED_COPPER, Blocks.COPPER_BLOCK),
			EXPOSED_COPPER = convert(Blocks.WEATHERED_COPPER, Blocks.EXPOSED_COPPER),
			WEATHERED_COPPER = convert(Blocks.OXIDIZED_COPPER, Blocks.WEATHERED_COPPER),
			CUT_COPPER = convert(Blocks.EXPOSED_CUT_COPPER, Blocks.CUT_COPPER),
			EXPOSED_CUT_COPPER = convert(Blocks.WEATHERED_CUT_COPPER, Blocks.EXPOSED_CUT_COPPER),
			WEATHERED_CUT_COPPER = convert(Blocks.OXIDIZED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER),
			CUT_COPPER_SLAB = convert(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.CUT_COPPER_SLAB),
			EXPOSED_CUT_COPPER_SLAB = convert(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB),
			WEATHERED_CUT_COPPER_SLAB = convert(Blocks.OXIDIZED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB),
			CUT_COPPER_STAIRS = convert(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.CUT_COPPER_STAIRS),
			EXPOSED_CUT_COPPER_STAIRS = convert(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS),
			WEATHERED_CUT_COPPER_STAIRS = convert(Blocks.OXIDIZED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS),

			ANDESITE = convert(Items.POLISHED_ANDESITE, Items.ANDESITE),
			ANDESITE_SLAB = convert(Items.POLISHED_ANDESITE_SLAB, Items.ANDESITE_SLAB),
			ANDESITE_STAIRS = convert(Items.POLISHED_ANDESITE_STAIRS, Items.ANDESITE_STAIRS),

			GRANITE = convert(Items.POLISHED_GRANITE, Items.GRANITE),
			GRANITE_SLAB = convert(Items.POLISHED_GRANITE_SLAB, Items.GRANITE_SLAB),
			GRANITE_STAIRS = convert(Items.POLISHED_GRANITE_STAIRS, Items.GRANITE_STAIRS),

			DIORITE = convert(Items.POLISHED_DIORITE, Items.DIORITE),
			DIORITE_SLAB = convert(Items.POLISHED_DIORITE_SLAB, Items.DIORITE_SLAB),
			DIORITE_STAIRS = convert(Items.POLISHED_DIORITE_STAIRS, Items.DIORITE_STAIRS),

			COBBLED_DEEPSLATE = convert(Items.POLISHED_DEEPSLATE, Items.COBBLED_DEEPSLATE),
			COBBLED_DEEPSLATE_SLAB = convert(Items.POLISHED_DEEPSLATE_SLAB, Items.COBBLED_DEEPSLATE_SLAB),
			COBBLED_DEEPSLATE_STAIRS = convert(Items.POLISHED_DEEPSLATE_STAIRS, Items.COBBLED_DEEPSLATE_STAIRS),
			COBBLED_DEEPSLATE_WALL = convert(Items.POLISHED_DEEPSLATE_WALL, Items.COBBLED_DEEPSLATE_WALL),

			BASALT = convert(Items.POLISHED_BASALT, Items.BASALT),
			PACKED_MUD = convert(Items.MUD, Items.PACKED_MUD),
			WARPED_NYLIUM = convert(Items.WARPED_NYLIUM, Items.NETHERRACK),
			CRIMSON_NYLIUM = convert(Items.CRIMSON_NYLIUM, Items.NETHERRACK),
			NETHERRACK = convert(Items.MAGMA_BLOCK, Items.NETHERRACK),

			POLISHED_ROSE_QUARTZ = convert(AllItems.ROSE_QUARTZ, AllItems.POLISHED_ROSE_QUARTZ);

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

	public SandingRecipeGen(PackOutput dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected HenryRecipeTypes getRecipeType() {
		return HenryRecipeTypes.SANDING;
	}

}
