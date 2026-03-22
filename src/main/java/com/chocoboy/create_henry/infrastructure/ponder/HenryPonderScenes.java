package com.chocoboy.create_henry.infrastructure.ponder;

import com.chocoboy.create_henry.infrastructure.ponder.scenes.KineticsScenes;
import com.chocoboy.create_henry.infrastructure.ponder.scenes.ProcessingScenes;
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

        HELPER.forComponents(HenryBlocks.GOLDEN_MIXER)
                .addStoryBoard("golden_mixing", ProcessingScenes::goldenMixing, HenryPonderTags.Henry);

        HELPER.forComponents(HenryBlocks.KINETIC_MOTOR)
                .addStoryBoard("kinetic_motor", ProcessingScenes::kineticMotor, HenryPonderTags.Henry);

        HELPER.forComponents(HenryBlocks.INDUSTRIAL_BRAKE)
                .addStoryBoard("industrial_brake", KineticsScenes::industrialBrake, HenryPonderTags.Henry);

        HELPER.forComponents(HenryBlocks.FURNACE_ENGINE)
                .addStoryBoard("furnace_engine", ProcessingScenes::furnaceEngine, HenryPonderTags.Henry);

        HELPER.forComponents(HenryBlocks.POWERED_FLYWHEEL)
                .addStoryBoard("furnace_engine", ProcessingScenes::flywheel, HenryPonderTags.Henry);

        HELPER.forComponents(HenryBlocks.HYDRAULIC_PRESS)
                .addStoryBoard("hydraulic_press", ProcessingScenes::bulk_pressing, HenryPonderTags.Henry);
    }
}