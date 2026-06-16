package com.chocoboy.create_henry;

import net.createmod.ponder.foundation.PonderIndex;
import net.fabricmc.api.ClientModInitializer;
import com.chocoboy.create_henry.infrastructure.ponder.HenryPonderPlugin;
import com.chocoboy.create_henry.registry.HenryParticleTypes;
import com.chocoboy.create_henry.registry.HenryPartialModels;

public class HenryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HenryPartialModels.init();
        PonderIndex.addPlugin(new HenryPonderPlugin());
        HenryParticleTypes.registerFactories();
    }
}
