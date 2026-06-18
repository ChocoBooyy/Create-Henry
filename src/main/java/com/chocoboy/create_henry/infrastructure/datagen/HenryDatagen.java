package com.chocoboy.create_henry.infrastructure.datagen;

import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.infrastructure.ponder.HenryPonderPlugin;
import com.chocoboy.create_henry.registry.HenryLangPartial;
import com.tterrag.registrate.providers.ProviderType;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.createmod.ponder.foundation.PonderIndex;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataProvider;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class HenryDatagen implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		ExistingFileHelper helper = ExistingFileHelper.withResourcesFromArg();
		FabricDataGenerator.Pack pack = generator.createPack();

		addExtraRegistrateData();
		HenryCreate.registrate().setupDatagen(pack, helper);

		gatherData(pack);
	}

	private static void gatherData(FabricDataGenerator.Pack pack) {
		add(pack, AdvancedCraftingRecipeGen::new);
		add(pack, WashingRecipeGen::new);
		add(pack, SandingRecipeGen::new);
		add(pack, FreezingRecipeGen::new);
		add(pack, SeethingRecipeGen::new);
		add(pack, WitheringRecipeGen::new);
		add(pack, DragonBreathingRecipeGen::new);
		add(pack, ItemApplicationRecipeGen::new);
		add(pack, MixingRecipeGen::new);
		add(pack, EmptyingRecipeGen::new);
		add(pack, FillingRecipeGen::new);
		add(pack, com.chocoboy.create_henry.registry.HenrySoundEvents::provider);
		add(pack, HydraulicRecipeGen::new);
	}

	// Disambiguates Pack.addProvider's Factory / RegistryDependentFactory overloads: every Henry
	// provider is constructed from a PackOutput alone, so it always maps to the simple Factory form.
	private static <T extends DataProvider> void add(FabricDataGenerator.Pack pack,
													 Function<FabricDataOutput, T> factory) {
		pack.addProvider(factory::apply);
	}

	private static void addExtraRegistrateData() {
		HenryRegistrateTags.addGenerators();

		HenryCreate.registrate().addDataGenerator(ProviderType.LANG, provider -> {
			BiConsumer<String, String> langConsumer = provider::add;
			HenryLangPartial.provideLang(langConsumer);
			PonderIndex.addPlugin(new HenryPonderPlugin());
			PonderIndex.getLangAccess().provideLang(HenryCreate.MOD_ID, langConsumer);
		});
	}
}
