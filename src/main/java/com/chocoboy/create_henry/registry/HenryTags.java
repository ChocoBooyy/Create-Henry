package com.chocoboy.create_henry.registry;

import com.chocoboy.create_henry.HenryCreate;
import com.simibubi.create.Create;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collections;

import static com.chocoboy.create_henry.registry.HenryTags.NameSpace.FORGE;

@SuppressWarnings({"all"})
public class HenryTags {
	public static <T> TagKey<T> optionalTag(IForgeRegistry<T> registry,
											ResourceLocation id) {
		return registry.tags()
				.createOptionalTagKey(id, Collections.emptySet());
	}

	public static <T> TagKey<T> forgeTag(IForgeRegistry<T> registry, String path) {
		return optionalTag(registry, new ResourceLocation("forge", path));
	}

	public static TagKey<Block> forgeBlockTag(String path) {
		return forgeTag(ForgeRegistries.BLOCKS, path);
	}

	public static TagKey<Item> forgeItemTag(String path) {
		return forgeTag(ForgeRegistries.ITEMS, path);
	}

	public static TagKey<Fluid> forgeFluidTag(String path) {
		return forgeTag(ForgeRegistries.FLUIDS, path);
	}

	public static TagKey<Item> minecraftItemTag(String path) {
		return ItemTags.create(new ResourceLocation("minecraft", path));
	}

	public enum NameSpace {

		MOD(HenryCreate.MOD_ID, false, true),
		CREATE("create"),
		FORGE("forge"),

		;

		public final String id;
		public final boolean optionalDefault;
		public final boolean alwaysDatagenDefault;

		NameSpace(String id) {
			this(id, true, false);
		}

		NameSpace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
			this.id = id;
			this.optionalDefault = optionalDefault;
			this.alwaysDatagenDefault = alwaysDatagenDefault;
		}
	}

	public enum AllBlockTags {

		FAN_PROCESSING_CATALYSTS_SANDING(NameSpace.MOD, "fan_processing_catalysts/sanding"),
		FAN_PROCESSING_CATALYSTS_FREEZING(NameSpace.MOD, "fan_processing_catalysts/freezing"),
		FAN_PROCESSING_CATALYSTS_SEETHING(NameSpace.MOD, "fan_processing_catalysts/seething"),
		FAN_PROCESSING_CATALYSTS_WITHERING(NameSpace.MOD, "fan_processing_catalysts/withering"),
		FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING(NameSpace.MOD, "fan_processing_catalysts/dragon_breathing"),
		INDUSTRIAL_FAN_TRANSPARENT

		;

		public final TagKey<Block> tag;
		public final boolean alwaysDatagen;

		AllBlockTags() {
			this(NameSpace.MOD);
		}

		AllBlockTags(NameSpace namespace) {
			this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		AllBlockTags(NameSpace namespace, String path) {
			this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		AllBlockTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
			this(namespace, null, optional, alwaysDatagen);
		}

		AllBlockTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
			ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
			if (optional) {
				tag = optionalTag(ForgeRegistries.BLOCKS, id);
			} else {
				tag = BlockTags.create(id);
			}
			this.alwaysDatagen = alwaysDatagen;
		}

		@SuppressWarnings("deprecation")
		public boolean matches(Block block) {
			return block.builtInRegistryHolder()
					.is(tag);
		}

		public boolean matches(ItemStack stack) {
			return stack != null && stack.getItem() instanceof BlockItem blockItem && matches(blockItem.getBlock());
		}

		public boolean matches(BlockState state) {
			return state.is(tag);
		}

		private static void init() {}

	}

	public enum AllItemTags {

		SEETHABLE,
		SANDABLE,
		FREEZABLE,
		WITHERABLE,
		DRAGON_BREATHABLE,

		MEAT(NameSpace.MOD, "meat")
		;

		public final TagKey<Item> tag;
		public final boolean alwaysDatagen;

		AllItemTags() {
			this(NameSpace.MOD);
		}

		AllItemTags(NameSpace namespace) {
			this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		AllItemTags(NameSpace namespace, String path) {
			this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		AllItemTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
			this(namespace, null, optional, alwaysDatagen);
		}

		AllItemTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
			ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
			if (optional) {
				tag = optionalTag(ForgeRegistries.ITEMS, id);
			} else {
				tag = ItemTags.create(id);
			}
			this.alwaysDatagen = alwaysDatagen;
		}

		@SuppressWarnings("deprecation")
		public boolean matches(Item item) {
			return item.builtInRegistryHolder()
					.is(tag);
		}

		public boolean matches(ItemStack stack) {
			return stack.is(tag);
		}

		private static void init() {}

	}

	public enum AllFluidTags {

		FAN_PROCESSING_CATALYSTS_SANDING(NameSpace.MOD, "fan_processing_catalysts/sanding"),
		FAN_PROCESSING_CATALYSTS_FREEZING(NameSpace.MOD, "fan_processing_catalysts/freezing"),
		FAN_PROCESSING_CATALYSTS_SEETHING(NameSpace.MOD, "fan_processing_catalysts/seething"),
		FAN_PROCESSING_CATALYSTS_WITHERING(NameSpace.MOD, "fan_processing_catalysts/withering"),
		FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING(NameSpace.MOD, "fan_processing_catalysts/dragon_breathing"),

		SAP(FORGE)

		;

		public final TagKey<Fluid> tag;
		public final boolean alwaysDatagen;

		AllFluidTags() {
			this(NameSpace.MOD);
		}

		AllFluidTags(NameSpace namespace) {
			this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		AllFluidTags(NameSpace namespace, String path) {
			this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		AllFluidTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
			this(namespace, null, optional, alwaysDatagen);
		}

		AllFluidTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
			ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
			if (optional) {
				tag = optionalTag(ForgeRegistries.FLUIDS, id);
			} else {
				tag = FluidTags.create(id);
			}
			this.alwaysDatagen = alwaysDatagen;
		}

		@SuppressWarnings("deprecation")
		public boolean matches(Fluid fluid) {
			return fluid.is(tag);
		}

		public boolean matches(FluidState state) {
			return state.is(tag);
		}

		private static void init() {}

	}

	public enum HenryRecipeSerializerTags {

		;

		public final TagKey<RecipeSerializer<?>> tag;
		public final boolean alwaysDatagen;

		HenryRecipeSerializerTags() {
			this(NameSpace.MOD);
		}

		HenryRecipeSerializerTags(NameSpace namespace) {
			this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		HenryRecipeSerializerTags(NameSpace namespace, String path) {
			this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
		}

		HenryRecipeSerializerTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
			this(namespace, null, optional, alwaysDatagen);
		}

		HenryRecipeSerializerTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
			ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
			if (optional) {
				tag = optionalTag(ForgeRegistries.RECIPE_SERIALIZERS, id);
			} else {
				tag = TagKey.create(Registries.RECIPE_SERIALIZER, id);
			}
			this.alwaysDatagen = alwaysDatagen;
		}

		public boolean matches(RecipeSerializer<?> recipeSerializer) {
			return ForgeRegistries.RECIPE_SERIALIZERS.getHolder(recipeSerializer).orElseThrow().is(tag);
		}

		private static void init() {}
	}

	public static void init() {
		AllBlockTags.init();
		AllItemTags.init();
		AllFluidTags.init();
		HenryRecipeSerializerTags.init();
	}
}
