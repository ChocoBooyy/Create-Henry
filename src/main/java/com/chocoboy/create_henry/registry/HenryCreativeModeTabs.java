package com.chocoboy.create_henry.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.mutable.MutableObject;
import com.chocoboy.create_henry.HenryCreate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class HenryCreativeModeTabs {
	private static final DeferredRegister<CreativeModeTab> REGISTER =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HenryCreate.MOD_ID);

	public static final RegistryObject<CreativeModeTab> BASE_CREATIVE_TAB = REGISTER.register("base",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.create_henry.base"))
					.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
					.icon(HenryBlocks.INDUSTRIAL_FAN::asStack)
					.displayItems(new RegistrateDisplayItemsGenerator(true, HenryCreativeModeTabs.BASE_CREATIVE_TAB))
					.build());

	public static void register(IEventBus modEventBus) {
		REGISTER.register(modEventBus);
	}

	private static class RegistrateDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {
		private static final Predicate<Item> IS_ITEM_3D_PREDICATE;

		static {
			MutableObject<Predicate<Item>> isItem3d = new MutableObject<>(item -> false);
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> isItem3d.setValue(item -> {
				ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
				BakedModel model = renderer.getModel(new ItemStack(item), null, null, 0);
				return model.isGui3d();
			}));
			IS_ITEM_3D_PREDICATE = isItem3d.getValue();
		}

		private final boolean addItems;
		private final RegistryObject<CreativeModeTab> tabFilter;

		public RegistrateDisplayItemsGenerator(boolean addItems, RegistryObject<CreativeModeTab> tabFilter) {
			this.addItems = addItems;
			this.tabFilter = tabFilter;
		}

		@SuppressWarnings("removal")
		private static Predicate<Item> makeExclusionPredicate() {
			return Set.of(HenryItems.INCOMPLETE_KINETIC_MECHANISM.asItem())::contains;
		}

		private static List<ItemOrdering> makeOrderings() {
			List<ItemOrdering> orderings = new ReferenceArrayList<>();

			// Items
			// Drinks are in natural Registrate order (chocolate -> vanilla -> strawberry -> glowberry -> pumpkin).
			// All buckets follow in the same flavor order, starting after the last drink.
			Item[] milkshakeBuckets = {
				bucket("chocolate_milkshake"), bucket("vanilla_milkshake"),
				bucket("strawberry_milkshake"), bucket("glowberry_milkshake"),
				bucket("pumpkin_milkshake")
			};
			Item sapBucket = bucket("sap");

			Item bucketAnchor = HenryItems.PUMPKIN_MILKSHAKE.asItem();
			for (Item b : milkshakeBuckets) {
				if (b != null) {
					orderings.add(ItemOrdering.after(b, bucketAnchor));
					bucketAnchor = b;
				}
			}
			if (sapBucket != null) {
				orderings.add(ItemOrdering.after(sapBucket, bucketAnchor));
				bucketAnchor = sapBucket;
			}

			// Small materials: coal piece -> lapis shard
			orderings.add(ItemOrdering.after(HenryItems.COAL_PIECE.asItem(), bucketAnchor));
			orderings.add(ItemOrdering.after(HenryItems.LAPIS_LAZULI_SHARD.asItem(), HenryItems.COAL_PIECE.asItem()));

			// Rubber materials -> kinetic mechanism
			orderings.add(ItemOrdering.after(HenryItems.RAW_RUBBER.asItem(), HenryItems.LAPIS_LAZULI_SHARD.asItem()));
			orderings.add(ItemOrdering.after(HenryItems.RUBBER.asItem(), HenryItems.RAW_RUBBER.asItem()));
			orderings.add(ItemOrdering.after(HenryItems.KINETIC_MECHANISM.asItem(), HenryItems.RUBBER.asItem()));

			// Blocks: casings (hydraulic, industrial, rubber) -> processing machines -> kinetics -> redstone/utility -> rubber blocks -> decorative
			orderings.add(ItemOrdering.after(HenryBlocks.KINETIC_MOTOR.asItem(), HenryBlocks.BORE_BLOCK.asItem()));
			orderings.add(ItemOrdering.after(HenryBlocks.INDUSTRIAL_BRAKE.asItem(), HenryBlocks.KINETIC_MOTOR.asItem()));
			orderings.add(ItemOrdering.after(HenryBlocks.FURNACE_ENGINE.asItem(), HenryBlocks.INDUSTRIAL_BRAKE.asItem()));
			orderings.add(ItemOrdering.after(HenryBlocks.POWERED_FLYWHEEL.asItem(), HenryBlocks.FURNACE_ENGINE.asItem()));
			orderings.add(ItemOrdering.after(HenryBlocks.ROLL_TABLE.asItem(), HenryBlocks.FURNACE_ENGINE.asItem()));
			orderings.add(ItemOrdering.after(HenryBlocks.RAW_RUBBER_BLOCK.asItem(), HenryBlocks.INVERSE_BOX.asItem()));
			orderings.add(ItemOrdering.after(HenryBlocks.RUBBER_BLOCK.asItem(), HenryBlocks.RAW_RUBBER_BLOCK.asItem()));

			return orderings;
		}

		// Returns the registered bucket item for the given fluid id, or null if missing/AIR
		private static Item bucket(String fluidId) {
			Item item = ForgeRegistries.ITEMS.getValue(HenryCreate.asResource(fluidId + "_bucket"));
			return (item == null || item == Items.AIR) ? null : item;
		}

		private static Function<Item, ItemStack> makeStackFunc() {
			Map<Item, Function<Item, ItemStack>> factories = new Reference2ReferenceOpenHashMap<>();
			return item -> {
				Function<Item, ItemStack> factory = factories.get(item);
				return factory != null ? factory.apply(item) : new ItemStack(item);
			};
		}

		private static Function<Item, CreativeModeTab.TabVisibility> makeVisibilityFunc() {
			Map<Item, CreativeModeTab.TabVisibility> visibilities = new Reference2ObjectOpenHashMap<>();
			return item -> visibilities.getOrDefault(item, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		}

		@Override
		public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
			Predicate<Item> exclusionPredicate = makeExclusionPredicate();
			List<ItemOrdering> orderings = makeOrderings();
			Function<Item, ItemStack> stackFunc = makeStackFunc();
			Function<Item, CreativeModeTab.TabVisibility> visibilityFunc = makeVisibilityFunc();

			List<Item> items = new LinkedList<>();
			if (addItems) items.addAll(collectItems(exclusionPredicate.or(IS_ITEM_3D_PREDICATE.negate())));
			items.addAll(collectBlocks(exclusionPredicate));
			if (addItems) items.addAll(collectItems(exclusionPredicate.or(IS_ITEM_3D_PREDICATE)));

			applyOrderings(items, orderings);
			outputAll(output, items, stackFunc, visibilityFunc);
		}

		private List<Item> collectBlocks(Predicate<Item> exclusionPredicate) {
			List<Item> items = new ReferenceArrayList<>();
			for (RegistryEntry<Block> entry : HenryCreate.REGISTRATE.getAll(Registries.BLOCK)) {
				if (!CreateRegistrate.isInCreativeTab(entry, tabFilter)) continue;
				Item item = entry.get().asItem();
				if (item == Items.AIR) continue;
				if (!exclusionPredicate.test(item)) items.add(item);
			}
			return new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
		}

		private List<Item> collectItems(Predicate<Item> exclusionPredicate) {
			List<Item> items = new ReferenceArrayList<>();
			for (RegistryEntry<Item> entry : HenryCreate.REGISTRATE.getAll(Registries.ITEM)) {
				if (!CreateRegistrate.isInCreativeTab(entry, tabFilter)) continue;
				Item item = entry.get();
				if (item == Items.AIR || item instanceof BlockItem) continue;
				if (!exclusionPredicate.test(item)) items.add(item);
			}
			return items;
		}

		private static void applyOrderings(List<Item> items, List<ItemOrdering> orderings) {
			for (ItemOrdering ordering : orderings) {
				int anchorIndex = items.indexOf(ordering.anchor());
				if (anchorIndex == -1) continue;
				Item item = ordering.item();
				if (item == null || item == Items.AIR) continue;
				int itemIndex = items.indexOf(item);
				if (itemIndex != -1) {
					items.remove(itemIndex);
					if (itemIndex < anchorIndex) anchorIndex--;
				}
				items.add(ordering.type() == ItemOrdering.Type.AFTER ? anchorIndex + 1 : anchorIndex, item);
			}
		}

		private static void outputAll(CreativeModeTab.Output output, List<Item> items,
				Function<Item, ItemStack> stackFunc, Function<Item, CreativeModeTab.TabVisibility> visibilityFunc) {
			for (Item item : items) {
				if (item == null || item == Items.AIR) continue;
				output.accept(stackFunc.apply(item), visibilityFunc.apply(item));
			}
		}

		private record ItemOrdering(Item item, Item anchor, ItemOrdering.Type type) {
			public static ItemOrdering before(Item item, Item anchor) {
				return new ItemOrdering(item, anchor, Type.BEFORE);
			}

			public static ItemOrdering after(Item item, Item anchor) {
				return new ItemOrdering(item, anchor, Type.AFTER);
			}

			public enum Type {
                BEFORE,
                AFTER
            }
		}
	}
}
