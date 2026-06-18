package com.chocoboy.create_henry.compat;

import com.chocoboy.create_henry.registry.HenryTags;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.fabricmc.loader.api.FabricLoader;
import com.chocoboy.create_henry.util.Lang;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings({"unused", "removal", "all"})
public enum HenryMods {
    CREATEADDITION("createaddition");

    private final String id;

    HenryMods(String id) {
        this.id = Lang.asId(name());
    }

    public String id() {
        return id;
    }

    public ResourceLocation rl(String path) {
        return new ResourceLocation(id, path);
    }

    public Block getBlock(String id) {
        return BuiltInRegistries.BLOCK.get(rl(id));
    }

    public boolean isLoaded() {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    public <T> Optional<T> runIfInstalled(Supplier<Supplier<T>> toRun) {
        if (isLoaded())
            return Optional.of(toRun.get().get());
        return Optional.empty();
    }

    public void executeIfInstalled(Supplier<Runnable> toExecute) {
        if (isLoaded()) {
            toExecute.get().run();
        }
    }

}