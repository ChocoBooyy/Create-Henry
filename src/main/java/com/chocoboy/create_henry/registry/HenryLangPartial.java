package com.chocoboy.create_henry.registry;

import java.util.function.BiConsumer;

@SuppressWarnings({"unused"})
public class HenryLangPartial {
    public static void provideLang(BiConsumer<String, String> consumer) {
        consume(consumer, "itemGroup.create_henry.base", "Create: Henry");
    }

    private static void consume(BiConsumer<String, String> consumer, String key, String enUS) {
        consumer.accept(key, enUS);
    }
}
