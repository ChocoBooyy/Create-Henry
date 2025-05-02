package com.chocoboy.create_henry.registry;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.item.CombustibleItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import com.chocoboy.create_henry.HenryCreate;

import static com.tterrag.registrate.providers.RegistrateRecipeProvider.getItemName;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static com.chocoboy.create_henry.HenryCreate.REGISTRATE;
import static com.chocoboy.create_henry.registry.HenryTags.forgeItemTag;

@SuppressWarnings({"unused", "deprecation", "all"})
public class HenryItems {

	static {
		REGISTRATE.setCreativeTab(HenryCreativeModeTabs.BASE_CREATIVE_TAB);
	}

	public static final ItemEntry<SequencedAssemblyItem>
			INCOMPLETE_KINETIC_MECHANISM = sequencedItem("incomplete_kinetic_mechanism");

	public static final ItemEntry<Item>
			KINETIC_MECHANISM = item("kinetic_mechanism");

	public static final ItemEntry<Item> RAW_RUBBER = REGISTRATE.item("raw_rubber", Item::new)
			.model((c, p) -> p.withExistingParent(c.getId().getPath(),
					new ResourceLocation("item/generated")).texture("layer0",
					new ResourceLocation(HenryCreate.MOD_ID,"item/" + c.getId().getPath())))
			.tag(forgeItemTag("raw_rubbers"))
			.recipe((c, p) -> {
				Item output = HenryBlocks.RAW_RUBBER_BLOCK.get().asItem();
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 1)
						.pattern("CCC")
						.pattern("CCC")
						.pattern("CCC")
						.define('C', c.get())
						.unlockedBy("has_" + getItemName(output), has(output))
						.save(p, HenryCreate.asResource("crafting/" + getItemName(output) + "_from_" + c.getName()));
				ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 9)
						.requires(output)
						.unlockedBy("has_" + c.getName(), has(c.get()))
						.save(p, HenryCreate.asResource("crafting/" + c.getName() + "_from_" + getItemName(output)));
			})
			.lang("Raw Rubber")
			.register();

	public static final ItemEntry<Item> RUBBER = REGISTRATE.item("rubber", Item::new)
			.model((c, p) -> p.withExistingParent(c.getId().getPath(),
					new ResourceLocation("item/generated")).texture("layer0",
					new ResourceLocation(HenryCreate.MOD_ID,"item/" + c.getId().getPath())))
			.tag(forgeItemTag("rubbers"), forgeItemTag("crude_rubbers"))
			.recipe((c, p) -> {
				Item output = HenryBlocks.RUBBER_BLOCK.get().asItem();
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 1)
						.pattern("CCC")
						.pattern("CCC")
						.pattern("CCC")
						.define('C', c.get())
						.unlockedBy("has_" + getItemName(output), has(output))
						.save(p, HenryCreate.asResource("crafting/" + getItemName(output) + "_from_" + c.getName()));
				ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 9)
						.requires(output)
						.unlockedBy("has_" + c.getName(), has(c.get()))
						.save(p, HenryCreate.asResource("crafting/" + c.getName() + "_from_" + getItemName(output)));

				SimpleCookingRecipeBuilder.smoking(Ingredient.of(RAW_RUBBER),RecipeCategory.BUILDING_BLOCKS , c.get(), 2, 600)
						.unlockedBy("has_" + getItemName(RAW_RUBBER.get()), has(HenryItems.RAW_RUBBER.get()))
						.save(p, HenryCreate.asResource("smoking/" + c.getId().getPath()));
			})
			.lang("Rubber")
			.register();

	public static final ItemEntry<Item> LAPIS_LAZULI_SHARD = REGISTRATE.item("lapis_lazuli_shard", Item::new)
			.model((c, p) -> p.withExistingParent(c.getId().getPath(),
					new ResourceLocation("item/generated")).texture("layer0",
					new ResourceLocation(HenryCreate.MOD_ID,"item/" + c.getId().getPath())))
			.tag(forgeItemTag("nuggets/lapis"), forgeItemTag("nuggets"))
			.recipe((c, p) -> {
				Item output = Items.LAPIS_LAZULI;
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 1)
						.pattern("CC")
						.pattern("CC")
						.define('C', c.get())
						.unlockedBy("has_" + getItemName(output), has(output))
						.save(p, HenryCreate.asResource("crafting/" + getItemName(output) + "_from_" + c.getName()));
				ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
						.requires(output)
						.unlockedBy("has_" + c.getName(), has(c.get()))
						.save(p, HenryCreate.asResource("crafting/" + c.getName() + "_from_" + getItemName(output)));
			})
			.register();

	public static final ItemEntry<CombustibleItem> COAL_PIECE = REGISTRATE.item("coal_piece", CombustibleItem::new)
			.onRegister(i -> i.setBurnTime(200))
			.model((c, p) -> p.withExistingParent(c.getId().getPath(),
					new ResourceLocation("item/generated")).texture("layer0",
					new ResourceLocation(HenryCreate.MOD_ID,"item/" + c.getId().getPath())))
			.tag(forgeItemTag("nuggets/coal"), forgeItemTag("nuggets"))
			.recipe((c, p) -> {
				Item output = Items.COAL;
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 1)
						.pattern("CCC")
						.pattern("CCC")
						.pattern("CCC")
						.define('C', c.get())
						.unlockedBy("has_" + getItemName(output), has(output))
						.save(p, HenryCreate.asResource("crafting/" + getItemName(output) + "_from_" + c.getName()));
				ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 9)
						.requires(output)
						.unlockedBy("has_" + c.getName(), has(c.get()))
						.save(p, HenryCreate.asResource("crafting/" + c.getName() + "_from_" + getItemName(output)));
			})
			.register();

	private static ItemEntry<Item> item(String name) {
		ResourceKey<CreativeModeTab> tab = HenryCreativeModeTabs.BASE_CREATIVE_TAB.getKey();
		assert tab != null;
		return REGISTRATE.item(name, Item::new)
				.tab(tab)
				.register();
	}

	private static ItemEntry<SequencedAssemblyItem> sequencedItem(String name) {
		return REGISTRATE.item(name, SequencedAssemblyItem::new)
				.register();
	}
	// Load this class

	public static void register() {}

}
