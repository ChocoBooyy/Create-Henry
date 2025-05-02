package com.chocoboy.create_henry;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import com.chocoboy.create_henry.infrastructure.ponder.HenryPonderTags;
import com.chocoboy.create_henry.infrastructure.ponder.HenryPonderIndex;
import com.chocoboy.create_henry.registry.HenryParticleTypes;
import com.chocoboy.create_henry.registry.HenryPartialModels;

public class HenryClient {

    public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
        modEventBus.addListener(HenryClient::clientInit);;
        modEventBus.addListener(HenryParticleTypes::registerFactories);
    }


    public static void clientInit(final FMLClientSetupEvent event) {
        HenryPonderTags.register();
        HenryPonderIndex.register();
    }
}