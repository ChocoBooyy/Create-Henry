package com.chocoboy.create_henry.registry.helper;

import net.minecraft.network.chat.Component;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.network.chat.MutableComponent;
import com.chocoboy.create_henry.HenryCreate;

import static net.createmod.catnip.lang.LangBuilder.resolveBuilders;

public class Lang extends net.createmod.catnip.lang.Lang {

    public static MutableComponent translateDirect(String key, Object... args) {
        return Component.translatable(HenryCreate.MOD_ID + "." + key, resolveBuilders(args));
    }

    public static LangBuilder builder() {
        return new LangBuilder(HenryCreate.MOD_ID);
    }
}
