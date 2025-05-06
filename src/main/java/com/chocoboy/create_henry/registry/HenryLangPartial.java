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
    }

    private static void consume(BiConsumer<String, String> consumer, String key, String enUS) {
        consumer.accept(key, enUS);
    }
}
