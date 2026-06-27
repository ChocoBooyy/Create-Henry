package com.chocoboy.create_henry.content.emi;

import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.registry.HenryBlocks;
import com.chocoboy.create_henry.registry.HenryRecipeTypes;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.emi.DoubleItemIcon;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiRenderable;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.function.BiFunction;

/**
 * EMI plugin for Create: Henry. Mirrors the categories registered by HenryJEI (fan processing +
 * hydraulic compacting) so the recipes and their catalysts show up in EMI, not just JEI.
 */
public class HenryEmiPlugin implements EmiPlugin {

    public static final EmiRecipeCategory
            FAN_SANDING = register("fan_sanding", DoubleItemIcon.of(AllItems.PROPELLER.get(), Items.SAND)),
            FAN_FREEZING = register("fan_freezing", DoubleItemIcon.of(AllItems.PROPELLER.get(), Items.POWDER_SNOW_BUCKET)),
            FAN_SEETHING = register("fan_seething", DoubleItemIcon.of(AllItems.PROPELLER.get(), AllItems.BLAZE_CAKE.get())),
            FAN_WITHERING = register("fan_withering", DoubleItemIcon.of(AllItems.PROPELLER.get(), Items.WITHER_ROSE)),
            FAN_DRAGON_BREATHING = register("fan_dragon_breathing", DoubleItemIcon.of(AllItems.PROPELLER.get(), Items.DRAGON_HEAD)),
            HYDRAULIC_COMPACTING = register("hydraulic_compacting", EmiStack.of(HenryBlocks.HYDRAULIC_PRESS.get()));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(FAN_SANDING);
        registry.addCategory(FAN_FREEZING);
        registry.addCategory(FAN_SEETHING);
        registry.addCategory(FAN_WITHERING);
        registry.addCategory(FAN_DRAGON_BREATHING);
        registry.addCategory(HYDRAULIC_COMPACTING);

        EmiStack industrialFan = EmiStack.of(HenryBlocks.INDUSTRIAL_FAN.get());
        EmiStack encasedFan = EmiStack.of(AllBlocks.ENCASED_FAN.get());
        for (EmiRecipeCategory fan : List.of(FAN_SANDING, FAN_FREEZING, FAN_SEETHING, FAN_WITHERING, FAN_DRAGON_BREATHING)) {
            registry.addWorkstation(fan, industrialFan);
            registry.addWorkstation(fan, encasedFan);
        }
        registry.addWorkstation(HYDRAULIC_COMPACTING, EmiStack.of(HenryBlocks.HYDRAULIC_PRESS.get()));

        addAll(registry, HenryRecipeTypes.SANDING, FAN_SANDING,
                (c, r) -> new HenryFanEmiRecipe(c, (ProcessingRecipe<?>) r, Blocks.SAND));
        addAll(registry, HenryRecipeTypes.FREEZING, FAN_FREEZING,
                (c, r) -> new HenryFanEmiRecipe(c, (ProcessingRecipe<?>) r, Blocks.POWDER_SNOW));
        addAll(registry, HenryRecipeTypes.SEETHING, FAN_SEETHING,
                (c, r) -> new HenrySeethingEmiRecipe(c, (ProcessingRecipe<?>) r));
        addAll(registry, HenryRecipeTypes.WITHERING, FAN_WITHERING,
                (c, r) -> new HenryFanEmiRecipe(c, (ProcessingRecipe<?>) r, Blocks.WITHER_ROSE));
        addAll(registry, HenryRecipeTypes.DRAGON_BREATHING, FAN_DRAGON_BREATHING,
                (c, r) -> new HenryFanEmiRecipe(c, (ProcessingRecipe<?>) r, Blocks.DRAGON_HEAD));
        addAll(registry, HenryRecipeTypes.HYDRAULIC_COMPACTING, HYDRAULIC_COMPACTING,
                (c, r) -> new HenryHydraulicEmiRecipe(c, (BasinRecipe) r));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void addAll(EmiRegistry registry, IRecipeTypeInfo type, EmiRecipeCategory category,
                        BiFunction<EmiRecipeCategory, Recipe<?>, EmiRecipe> constructor) {
        List<Recipe<?>> recipes = (List) registry.getRecipeManager().getAllRecipesFor((RecipeType) type.getType());
        for (Recipe<?> recipe : recipes) {
            registry.addRecipe(constructor.apply(category, recipe));
        }
    }

    private static EmiRecipeCategory register(String name, EmiRenderable icon) {
        return new EmiRecipeCategory(HenryCreate.asResource(name), icon);
    }
}
