package com.chocoboy.create_henry.registry.helper;

import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.LangBuilder;
import net.minecraft.network.chat.MutableComponent;
import com.chocoboy.create_henry.HenryCreate;

public class Lang extends com.simibubi.create.foundation.utility.Lang {

    public static MutableComponent translateDirect(String key, Object... args) {
        return Components.translatable(HenryCreate.MOD_ID + "." + key, resolveBuilders(args));
    }

    public static LangBuilder builder() {
        return new LangBuilder(HenryCreate.MOD_ID);
    }
}