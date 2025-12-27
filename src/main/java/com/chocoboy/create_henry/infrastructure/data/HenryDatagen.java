package com.chocoboy.create_henry.infrastructure.data;

import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.content.data_recipes.AdvancedCraftingRecipeGen;
import com.chocoboy.create_henry.content.data_recipes.DragonBreathingRecipeGen;
import com.chocoboy.create_henry.content.data_recipes.EmptyingRecipeGen;
import com.chocoboy.create_henry.content.data_recipes.FreezingRecipeGen;
import com.chocoboy.create_henry.content.data_recipes.ItemApplicationRecipeGen;
import com.chocoboy.create_henry.content.data_recipes.MixingRecipeGen;
import com.chocoboy.create_henry.content.data_recipes.SandingRecipeGen;
import com.chocoboy.create_henry.content.data_recipes.SeethingRecipeGen;
import com.chocoboy.create_henry.content.data_recipes.WashingRecipeGen;
import com.chocoboy.create_henry.content.data_recipes.WitheringRecipeGen;
import com.chocoboy.create_henry.infrastructure.ponder.HenryPonderPlugin;
import com.chocoboy.create_henry.registry.HenryLangPartial;
import com.tterrag.registrate.providers.ProviderType;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.function.BiConsumer;

public class HenryDatagen {

	public static void gatherData(GatherDataEvent event) {
		addExtraRegistrateData();

		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();

		if (event.includeServer()) {
			generator.addProvider(true, new AdvancedCraftingRecipeGen(output));

			generator.addProvider(true, new WashingRecipeGen(output));
			generator.addProvider(true, new SandingRecipeGen(output));
			generator.addProvider(true, new FreezingRecipeGen(output));
			generator.addProvider(true, new SeethingRecipeGen(output));
			generator.addProvider(true, new WitheringRecipeGen(output));
			generator.addProvider(true, new DragonBreathingRecipeGen(output));
			generator.addProvider(true, new ItemApplicationRecipeGen(output));
			generator.addProvider(true, new MixingRecipeGen(output));
			generator.addProvider(true, new EmptyingRecipeGen(output));
		}
	}

	private static void addExtraRegistrateData() {
		HenryRegistrateTags.addGenerators();

		HenryCreate.registrate().addDataGenerator(ProviderType.LANG, provider -> {
			BiConsumer<String, String> langConsumer = provider::add;

			providePartialLang(langConsumer);
			providePonderLang();
		});
	}

	private static void providePartialLang(BiConsumer<String, String> consumer) {
		HenryLangPartial.provideLang(consumer);
	}

	private static void providePonderLang() {
		PonderIndex.addPlugin(new HenryPonderPlugin());
	}
}
