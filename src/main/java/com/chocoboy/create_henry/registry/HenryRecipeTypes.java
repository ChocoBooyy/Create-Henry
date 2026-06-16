package com.chocoboy.create_henry.registry;

import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.content.recipes.*;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeFactory;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import io.github.fabricators_of_create.porting_lib.util.ShapedRecipeUtil;
import io.github.fabricators_of_create.porting_lib.util.SimpleRecipeType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import net.createmod.catnip.lang.Lang;

import java.util.Optional;
import java.util.function.Supplier;

public enum HenryRecipeTypes implements IRecipeTypeInfo {

	SANDING(SandingRecipe::new),
	FREEZING(FreezingRecipe::new),
	SEETHING(SeethingRecipe::new),
	WITHERING(WitheringRecipe::new),
	DRAGON_BREATHING(DragonBreathingRecipe::new),
	HYDRAULIC_COMPACTING(HydraulicRecipe::new);

	private final ResourceLocation id;
	private final RecipeSerializer<?> serializer;
	private final RecipeType<?> type;

	HenryRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
		String name = Lang.asId(name());
		id = HenryCreate.asResource(name);
		serializer = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id, serializerSupplier.get());
		type = Registry.register(BuiltInRegistries.RECIPE_TYPE, id, new SimpleRecipeType<>(id));
	}

	HenryRecipeTypes(ProcessingRecipeFactory<?> processingFactory) {
		this(() -> new ProcessingRecipeSerializer<>(processingFactory));
	}

	public static void register() {
		ShapedRecipeUtil.setCraftingSize(9, 9);
		for (HenryRecipeTypes ignored : values()) {
			// Touching the enum forces eager registration through the constructors.
		}
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RecipeSerializer<?>> T getSerializer() {
		return (T) serializer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RecipeType<?>> T getType() {
		return (T) type;
	}

	public <C extends Container, T extends Recipe<C>> Optional<T> find(C inv, Level world) {
		return world.getRecipeManager()
				.getRecipeFor(getType(), inv, world);
	}

}
