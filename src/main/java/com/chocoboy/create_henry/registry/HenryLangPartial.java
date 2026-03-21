package com.chocoboy.create_henry.registry;

import java.util.function.BiConsumer;

@SuppressWarnings({"unused"})
public class HenryLangPartial {
    public static void provideLang(BiConsumer<String, String> consumer) {
        consume(consumer, "create_henry.recipe.fan_sanding.fan", "Fan behind Sand");
        consume(consumer, "create_henry.recipe.fan_freezing.fan", "Fan behind Powdered Snow");
        consume(consumer, "create_henry.recipe.fan_seething.fan", "Fan behind Super Heated Blaze Burner");
        consume(consumer, "create_henry.recipe.fan_withering.fan", "Fan behind Wither Rose");
        consume(consumer, "create_henry.recipe.fan_dragon_breathing.fan", "Fan behind Dragon Head");
        consume(consumer, "create_henry.recipe.fan_sanding", "Bulk Sanding");
        consume(consumer, "create_henry.recipe.fan_freezing", "Bulk Freezing");
        consume(consumer, "create_henry.recipe.fan_seething", "Bulk Seething");
        consume(consumer, "create_henry.recipe.fan_withering", "Bulk Withering");
        consume(consumer, "create_henry.recipe.fan_dragon_breathing", "Bulk Dragon Breathing");
        consume(consumer, "itemGroup.create_henry.base", "Create: Henry");
        // Multimeter display link: speed source
        consume(consumer, "create_henry.display_source.multimeter_speed", "Multimeter Speed (RPM)");
        consume(consumer, "create_henry.display_source.multimeter_speed.absolute", "Ignore Direction");
        consume(consumer, "create_henry.display_source.multimeter_speed.directional", "Include Direction");
        // Multimeter display link: stress source
        consume(consumer, "create_henry.display_source.multimeter_stress", "Multimeter Network Stress");
        consume(consumer, "create_henry.display_source.multimeter_stress.display", "Displayed Info");
        consume(consumer, "create_henry.display_source.multimeter_stress.percent", "Percentage of Capacity");
        consume(consumer, "create_henry.display_source.multimeter_stress.progress_bar", "Progress Bar");
        consume(consumer, "create_henry.display_source.multimeter_stress.current", "Current Stress (SU Used)");
        consume(consumer, "create_henry.display_source.multimeter_stress.remaining", "Remaining SU");
        consume(consumer, "create_henry.display_source.multimeter_stress.max", "Total SU Capacity");
    }

    private static void consume(BiConsumer<String, String> consumer, String key, String enUS) {
        consumer.accept(key, enUS);
    }
}
