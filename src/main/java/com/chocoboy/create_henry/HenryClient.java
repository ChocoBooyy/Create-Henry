package com.chocoboy.create_henry;

import net.createmod.ponder.foundation.PonderIndex;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;

import com.chocoboy.create_henry.content.fluids.SolidRenderedPlaceableFluidType;
import com.chocoboy.create_henry.infrastructure.ponder.HenryPonderPlugin;
import com.chocoboy.create_henry.registry.HenryFluids;
import com.chocoboy.create_henry.registry.HenryParticleTypes;
import com.chocoboy.create_henry.registry.HenryPartialModels;

import com.tterrag.registrate.fabric.SimpleFlowableFluid;

public class HenryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HenryPartialModels.init();
        PonderIndex.addPlugin(new HenryPonderPlugin());
        HenryParticleTypes.registerFactories();
        initFluidRendering();
    }

    private void initFluidRendering() {
        for (HenryFluids.FluidRendering rendering : HenryFluids.renderedFluids()) {
            SolidRenderedPlaceableFluidType config = rendering.config();
            SimpleFluidRenderHandler handler = new SimpleFluidRenderHandler(
                    config.getStillTexture(),
                    config.getFlowingTexture(),
                    config.getTintColor());

            SimpleFlowableFluid.Flowing flowing = rendering.fluid().get();
            FluidRenderHandlerRegistry.INSTANCE.register(flowing.getSource(), flowing, handler);
        }
    }
}
