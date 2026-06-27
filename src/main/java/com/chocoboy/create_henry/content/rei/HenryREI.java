package com.chocoboy.create_henry.content.rei;

import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.content.fans.processing.SandingType;
import com.chocoboy.create_henry.content.recipes.DragonBreathingRecipe;
import com.chocoboy.create_henry.content.recipes.FreezingRecipe;
import com.chocoboy.create_henry.content.recipes.SandingRecipe;
import com.chocoboy.create_henry.content.recipes.SeethingRecipe;
import com.chocoboy.create_henry.content.recipes.WitheringRecipe;
import com.chocoboy.create_henry.registry.HenryBlocks;
import com.chocoboy.create_henry.registry.HenryRecipeTypes;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.rei.CreateREI;
import com.simibubi.create.compat.rei.DoubleItemIcon;
import com.simibubi.create.compat.rei.EmptyBackground;
import com.simibubi.create.compat.rei.ItemIcon;
import com.simibubi.create.compat.rei.category.CreateRecipeCategory;
import com.simibubi.create.compat.rei.display.BasinDisplay;
import com.simibubi.create.compat.rei.display.CreateDisplay;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;

import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * REI plugin for Create: Henry. Mirrors HenryJEI (fan processing + hydraulic compacting) so the
 * categories, recipes and catalysts show up in REI, not just JEI. Titles reuse the existing
 * create_henry.recipe.* lang keys.
 */
@SuppressWarnings("unused")
public class HenryREI implements REIClientPlugin {

    private final List<CreateRecipeCategory<?>> allCategories = new ArrayList<>();

    @Override
    public void registerCategories(CategoryRegistry registry) {
        loadCategories();
        allCategories.forEach(category -> {
            registry.add(category);
            category.registerCatalysts(registry);
        });
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        allCategories.forEach(c -> c.registerRecipes(registry));
    }

    private void loadCategories() {
        allCategories.clear();

        CreateRecipeCategory<?>

                sanding = this.<SandingRecipe>builder()
                        .addTypedRecipes(HenryRecipeTypes.SANDING)
                        .addRecipeListConsumer(recipes -> CreateREI.consumeAllRecipes(recipe -> {
                            if (SandingType.isPolishProcessingRecipe(recipe)) {
                                SandingRecipe r = SandingType.toSandingRecipe((ProcessingRecipe<?>) recipe);
                                if (r != null) recipes.add(r);
                            }
                        }))
                        .catalystStack(henryFan("fan_sanding"))
                        .doubleItemIcon(AllItems.PROPELLER.get(), Items.SAND)
                        .emptyBackground(178, 72)
                        .build("fan_sanding", info -> new FanSimpleBlockReiCategory<>(info, Blocks.SAND)),

                freezing = this.<FreezingRecipe>builder()
                        .addTypedRecipes(HenryRecipeTypes.FREEZING)
                        .catalystStack(henryFan("fan_freezing"))
                        .doubleItemIcon(AllItems.PROPELLER.get(), Items.POWDER_SNOW_BUCKET)
                        .emptyBackground(178, 72)
                        .build("fan_freezing", info -> new FanSimpleBlockReiCategory<>(info, Blocks.POWDER_SNOW)),

                seething = this.<SeethingRecipe>builder()
                        .addTypedRecipes(HenryRecipeTypes.SEETHING)
                        .catalystStack(henryFan("fan_seething"))
                        .doubleItemIcon(AllItems.PROPELLER.get(), AllItems.BLAZE_CAKE.get())
                        .emptyBackground(178, 72)
                        .build("fan_seething", FanSeethingReiCategory::new),

                withering = this.<WitheringRecipe>builder()
                        .addTypedRecipes(HenryRecipeTypes.WITHERING)
                        .catalystStack(henryFan("fan_withering"))
                        .doubleItemIcon(AllItems.PROPELLER.get(), Items.WITHER_ROSE)
                        .emptyBackground(178, 72)
                        .build("fan_withering", info -> new FanSimpleBlockReiCategory<>(info, Blocks.WITHER_ROSE)),

                dragonBreathing = this.<DragonBreathingRecipe>builder()
                        .addTypedRecipes(HenryRecipeTypes.DRAGON_BREATHING)
                        .catalystStack(henryFan("fan_dragon_breathing"))
                        .doubleItemIcon(AllItems.PROPELLER.get(), Items.DRAGON_HEAD)
                        .emptyBackground(178, 72)
                        .build("fan_dragon_breathing", info -> new FanSimpleBlockReiCategory<>(info, Blocks.DRAGON_HEAD)),

                hydraulic = this.<BasinRecipe>builder()
                        .addTypedRecipes(HenryRecipeTypes.HYDRAULIC_COMPACTING)
                        .catalyst(HenryBlocks.HYDRAULIC_PRESS::get)
                        .itemIcon(HenryBlocks.HYDRAULIC_PRESS.get())
                        .emptyBackground(185, 100)
                        .displayFactory(BasinDisplay::packing)
                        .build("hydraulic_compacting", HydraulicReiCategory::new);
    }

    private <T extends Recipe<?>> CategoryBuilder<T> builder() {
        return new CategoryBuilder<>();
    }

    private static Supplier<ItemStack> henryFan(String name) {
        return () -> HenryBlocks.INDUSTRIAL_FAN.asStack()
                .setHoverName(Component.translatable(HenryCreate.MOD_ID + ".recipe." + name + ".fan")
                        .withStyle(style -> style.withItalic(false)));
    }

    private class CategoryBuilder<T extends Recipe<?>> {
        private Renderer background;
        private Renderer icon;
        private int width;
        private int height;
        private Function<T, ? extends CreateDisplay<T>> displayFactory;

        private final List<Consumer<List<T>>> recipeListConsumers = new ArrayList<>();
        private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList<>();

        public CategoryBuilder<T> addRecipeListConsumer(Consumer<List<T>> consumer) {
            recipeListConsumers.add(consumer);
            return this;
        }

        public CategoryBuilder<T> addTypedRecipes(IRecipeTypeInfo recipeTypeEntry) {
            return addRecipeListConsumer(recipes ->
                    CreateREI.<T>consumeTypedRecipes(recipes::add, recipeTypeEntry.getType()));
        }

        public CategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
            catalysts.add(supplier);
            return this;
        }

        public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
            return catalystStack(() -> new ItemStack(supplier.get().asItem()));
        }

        public CategoryBuilder<T> itemIcon(ItemLike item) {
            this.icon = new ItemIcon(() -> new ItemStack(item));
            return this;
        }

        public CategoryBuilder<T> doubleItemIcon(ItemLike item1, ItemLike item2) {
            this.icon = new DoubleItemIcon(() -> new ItemStack(item1), () -> new ItemStack(item2));
            return this;
        }

        public CategoryBuilder<T> emptyBackground(int width, int height) {
            this.background = new EmptyBackground(width, height);
            this.width = width;
            this.height = height;
            return this;
        }

        public CategoryBuilder<T> displayFactory(Function<T, ? extends CreateDisplay<T>> factory) {
            this.displayFactory = factory;
            return this;
        }

        public CreateRecipeCategory<T> build(String name, CreateRecipeCategory.Factory<T> factory) {
            Supplier<List<T>> recipesSupplier = () -> {
                List<T> recipes = new ArrayList<>();
                for (Consumer<List<T>> consumer : recipeListConsumers)
                    consumer.accept(recipes);
                return recipes;
            };

            CategoryIdentifier<CreateDisplay<T>> identifier = CategoryIdentifier.of(HenryCreate.asResource(name));
            CreateRecipeCategory.Info<T> info = new CreateRecipeCategory.Info<>(
                    identifier,
                    Component.translatable(HenryCreate.MOD_ID + ".recipe." + name),
                    background, icon, recipesSupplier, catalysts, width, height,
                    displayFactory == null ? (recipe) -> new CreateDisplay<>(recipe, identifier) : displayFactory);
            CreateRecipeCategory<T> category = factory.create(info);
            allCategories.add(category);
            return category;
        }
    }
}
