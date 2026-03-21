package com.chocoboy.create_henry.infrastructure.ponder;

import com.chocoboy.create_henry.infrastructure.ponder.scenes.KineticsScenes;
import com.chocoboy.create_henry.registry.HenryBlocks;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings({"deprecation", "all"})
public class HenryPonderScenes {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(HenryBlocks.MULTIMETER)
                .addStoryBoard("multimeter", KineticsScenes::multimeter, HenryPonderTags.Henry);
    }
}