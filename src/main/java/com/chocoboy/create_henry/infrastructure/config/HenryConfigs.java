package com.chocoboy.create_henry.infrastructure.config;

import com.chocoboy.create_henry.HenryCreate;
import com.simibubi.create.api.stress.BlockStressValues;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.createmod.catnip.config.ConfigBase;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class HenryConfigs {

	private static final Map<ModConfig.Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

	private static HenryClientConfig client;
	private static HenryServerConfig server;

	public static HenryClientConfig client() {
		return client;
	}

	public static HenryServerConfig server() {
		return server;
	}

	public static ConfigBase byType(ModConfig.Type type) {
		return CONFIGS.get(type);
	}

	private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
		Pair<T, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(builder -> {
			T config = factory.get();
			config.registerAll(builder);
			return config;
		});

		T config = specPair.getLeft();
		config.specification = specPair.getRight();
		CONFIGS.put(side, config);
		return config;
	}

	public static void register() {
		client = register(HenryClientConfig::new, ModConfig.Type.CLIENT);
		server = register(HenryServerConfig::new, ModConfig.Type.SERVER);

		for (Entry<ModConfig.Type, ConfigBase> pair : CONFIGS.entrySet())
			ForgeConfigRegistry.INSTANCE.register(HenryCreate.MOD_ID, pair.getKey(), pair.getValue().specification);

		ModConfigEvents.loading(HenryCreate.MOD_ID).register(
				config -> handleConfigEvent(config, ConfigBase::onLoad));
		ModConfigEvents.reloading(HenryCreate.MOD_ID).register(
				config -> handleConfigEvent(config, ConfigBase::onReload));

		HenryStressConfig stress = server().kinetics.stressValues;
		BlockStressValues.IMPACTS.registerProvider(stress::getImpact);
		BlockStressValues.CAPACITIES.registerProvider(stress::getCapacity);
	}

	private static void handleConfigEvent(ModConfig config, Consumer<ConfigBase> action) {
		CONFIGS.values().stream()
				.filter(c -> c.specification == config.getSpec())
				.forEach(action);
	}
}
