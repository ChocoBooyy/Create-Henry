package com.chocoboy.create_henry.infrastructure.datagen;

import com.chocoboy.create_henry.HenryCreate;
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
			HenryLangPartial.provideLang(langConsumer);
			PonderIndex.addPlugin(new HenryPonderPlugin());
		});
	}
}
