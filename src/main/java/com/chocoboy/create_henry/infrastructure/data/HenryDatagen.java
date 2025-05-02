package com.chocoboy.create_henry.infrastructure.data;

import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.content.data_recipes.HenryProcessingRecipeGen;
import com.chocoboy.create_henry.content.data_recipes.AdvancedCraftingRecipeGen;
import com.chocoboy.create_henry.infrastructure.ponder.HenryPonderTags;
import com.chocoboy.create_henry.infrastructure.ponder.HenryPonderIndex;
import com.chocoboy.create_henry.registry.HenryLangPartial;

import java.util.function.BiConsumer;

public class HenryDatagen {
	public static void gatherData(GatherDataEvent event) {
		addExtraRegistrateData();

		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();

		if (event.includeServer()) {

			generator.addProvider(true, new AdvancedCraftingRecipeGen(output));
			HenryProcessingRecipeGen.registerAll(generator, output);
		}
	}

	private static void addExtraRegistrateData() {
		HenryRegistrateTags.addGenerators();

		HenryCreate.REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
			BiConsumer<String, String> langConsumer = provider::add;

			providePartialLang(langConsumer);
			providePonderLang();
		});
	}

	private static void providePartialLang(BiConsumer<String, String> consumer) {
		HenryLangPartial.provideLang(consumer);
	}

	private static void providePonderLang() {
		HenryPonderTags.register();
		HenryPonderIndex.register();
	}
}