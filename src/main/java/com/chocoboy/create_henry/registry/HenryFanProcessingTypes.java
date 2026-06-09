package com.chocoboy.create_henry.registry;

import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.content.fans.processing.*;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.minecraft.core.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class HenryFanProcessingTypes {

    public static final SandingType SANDING = register("sanding", new SandingType());
    public static final FreezingType FREEZING = register("freezing", new FreezingType());
    public static final SeethingType SEETHING = register("seething", new SeethingType());
    public static final WitheringType WITHERING = register("withering", new WitheringType());
    public static final DragonBreathingType DRAGON_BREATHING = register("dragon_breathing", new DragonBreathingType());

    private static final Map<String, FanProcessingType> LEGACY_NAME_MAP = buildLegacyMap();

    private static Map<String, FanProcessingType> buildLegacyMap() {
        Object2ReferenceOpenHashMap<String, FanProcessingType> map = new Object2ReferenceOpenHashMap<>();
        map.put("SANDING", SANDING);
        map.put("FREEZING", FREEZING);
        map.put("SEETHING", SEETHING);
        map.put("WITHERING", WITHERING);
        map.put("DRAGON_BREATHING", DRAGON_BREATHING);
        map.trim();
        return map;
    }

    private static <T extends FanProcessingType> T register(String name, T type) {
        return Registry.register(CreateBuiltInRegistries.FAN_PROCESSING_TYPE, HenryCreate.asResource(name), type);
    }

    @Nullable
    public static FanProcessingType ofLegacyName(String name) {
        return LEGACY_NAME_MAP.get(name);
    }

    @Nullable
    public static FanProcessingType parseLegacy(String str) {
        FanProcessingType type = ofLegacyName(str);
        return type != null ? type : FanProcessingType.parse(str);
    }

    public static void init() {}
}
