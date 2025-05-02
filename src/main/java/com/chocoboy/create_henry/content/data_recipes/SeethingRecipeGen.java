package com.chocoboy.create_henry.content.data_recipes;

import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.registry.HenryItems;
import com.chocoboy.create_henry.registry.HenryRecipeTypes;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.palettes.AllPaletteBlocks;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.data.recipe.CompatMetals;
import com.simibubi.create.foundation.data.recipe.Mods;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.recipe.CompatMetals.*;

@SuppressWarnings({"unused", "deprecation", "all"})
public class SeethingRecipeGen extends HenryProcessingRecipeGen {

	GeneratedRecipe

		ENDER_EYE = convert(Items.ENDER_PEARL, Items.ENDER_EYE),
		MAGMA_BLOCK = convert(Blocks.NETHERRACK, Blocks.MAGMA_BLOCK),
		MAGMA_CREAM = convert(Items.SLIME_BALL, Items.MAGMA_CREAM),
		CRYING_OBSIDIAN = convert(Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN),
		COBBLED_DEEPSLATE = convert(Blocks.COBBLESTONE, Blocks.COBBLED_DEEPSLATE),

		LAPIS_LAZULI_SHARD = convertChanceRecipe(() -> Items.CALCITE, HenryItems.LAPIS_LAZULI_SHARD::get, .75f),


		NETHERITE_SCRAP = secondaryRecipe(() -> Items.ANCIENT_DEBRIS, () -> Items.NETHERITE_SCRAP, () -> Items.NETHERITE_SCRAP, .35f),

		CRUSHED_COPPER = crushedOre(AllItems.CRUSHED_COPPER, () -> Items.COPPER_INGOT, () -> Items.COPPER_INGOT, .5f),
		CRUSHED_ZINC = crushedOre(AllItems.CRUSHED_ZINC, AllItems.ZINC_INGOT::get, () -> AllItems.ZINC_INGOT::get, .25f),
		CRUSHED_GOLD = crushedOre(AllItems.CRUSHED_GOLD, () -> Items.GOLD_INGOT, () -> Items.GOLD_INGOT, .5f),
		CRUSHED_IRON = crushedOre(AllItems.CRUSHED_IRON, () -> Items.IRON_INGOT, () -> Items.IRON_INGOT, .75f),

		CRUSHED_OSMIUM = moddedCrushedOre(AllItems.CRUSHED_OSMIUM, OSMIUM),
		CRUSHED_PLATINUM = moddedCrushedOre(AllItems.CRUSHED_PLATINUM, PLATINUM),
		CRUSHED_SILVER = moddedCrushedOre(AllItems.CRUSHED_SILVER, SILVER),
		CRUSHED_TIN = moddedCrushedOre(AllItems.CRUSHED_TIN, TIN),
		CRUSHED_LEAD = moddedCrushedOre(AllItems.CRUSHED_LEAD, LEAD),
		CRUSHED_QUICKSILVER = moddedCrushedOre(AllItems.CRUSHED_QUICKSILVER, QUICKSILVER),
		CRUSHED_BAUXITE = moddedCrushedOre(AllItems.CRUSHED_BAUXITE, ALUMINUM),
		CRUSHED_URANIUM = moddedCrushedOre(AllItems.CRUSHED_URANIUM, URANIUM),
		CRUSHED_NICKEL = moddedCrushedOre(AllItems.CRUSHED_NICKEL, NICKEL)

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
		return create(HenryCreate.asResource(getItemName(first.get()) + "_from_" + getItemName(item.get())), b -> b.withItemIngredients(Ingredient.of(item.get())).output(first.get(), 1)
				.output(secondaryChance, secondary.get(), 1));
	}
	public GeneratedRecipe convertChanceRecipe(Supplier<ItemLike> item, Supplier<ItemLike> result, float chance) {
		return create(HenryCreate.asResource(getItemName(result.get()) + "_from_" + getItemName(item.get())), b -> b.withItemIngredients(Ingredient.of(item.get())).output(chance, result.get(), 1));
	}

	public GeneratedRecipe crushedOre(ItemEntry<Item> crushed, Supplier<ItemLike> ingot, Supplier<ItemLike> secondary,
		float secondaryChance) {
		return create(crushed::get, b -> b.output(ingot.get(), 1)
			.output(secondaryChance, secondary.get(), 1));
	}

	public GeneratedRecipe moddedCrushedOre(ItemEntry<? extends Item> crushed, CompatMetals metal) {
		String metalName = metal.getName();
		for (Mods mod : metal.getMods()) {
			ResourceLocation ingot = mod.ingotOf(metalName);
			create(mod.getId() + "/" + crushed.getId()
				.getPath(),
				b -> b.withItemIngredients(Ingredient.of(crushed::get))
					.output(1, ingot, 1)
					.output(0.5f, ingot, 1)
					.whenModLoaded(mod.getId()));
		}
		return null;
	}

	public SeethingRecipeGen(PackOutput dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected HenryRecipeTypes getRecipeType() {
		return HenryRecipeTypes.SEETHING;
	}

}
