package com.chocoboy.create_henry.content.data_recipes;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

@SuppressWarnings({"unused"})
public class WashingRecipeGen extends HenryProcessingRecipeGen {

	GeneratedRecipe

			EXPOSED_COPPER = convert(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER),
			WEATHERED_COPPER = convert(Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER),
			OXIDIZED_COPPER = convert(Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER),

			EXPOSED_CUT_COPPER = convert(Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER),
			WEATHERED_CUT_COPPER = convert(Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER),
			OXIDIZED_CUT_COPPER = convert(Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER),

			EXPOSED_CUT_COPPER_SLAB = convert(Blocks.CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB),
			WEATHERED_CUT_COPPER_SLAB = convert(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB),
			OXIDIZED_CUT_COPPER_SLAB = convert(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB),

			EXPOSED_CUT_COPPER_STAIRS = convert(Blocks.CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS),
			WEATHERED_CUT_COPPER_STAIRS = convert(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS),
			OXIDIZED_CUT_COPPER_STAIRS = convert(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS);

	public GeneratedRecipe convert(Block block, Block result) {
		return create(() -> block, b -> b.output(result));
	}

	public WashingRecipeGen(PackOutput dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected AllRecipeTypes getRecipeType() {
		return AllRecipeTypes.SPLASHING;
	}

}
