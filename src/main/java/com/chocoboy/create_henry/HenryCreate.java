package com.chocoboy.create_henry;

import com.chocoboy.create_henry.registry.*;
import com.chocoboy.create_henry.infrastructure.network.HenryPackets;
import com.mojang.logging.LogUtils;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import com.chocoboy.create_henry.content.blocks.kinetics.furnace_engine.FurnaceEngineBlock;
import com.chocoboy.create_henry.content.blocks.logistics.smart_hopper.SmartHopperBlockEntity;
import com.chocoboy.create_henry.content.blocks.kinetics.hydraulic_press.HydraulicPressBlockEntity;
import com.chocoboy.create_henry.content.fans.processing.SandingType;
import com.chocoboy.create_henry.infrastructure.config.HenryConfigs;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


public class HenryCreate implements ModInitializer {

    public static final String NAME = "Create: Henry";
    public static final String MOD_ID = "create_henry";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Random RANDOM = new Random();

    @Nullable
    private static MinecraftServer server;

    @Nullable
    public static KineticStats create(Item item) {
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof IRotate || block instanceof FurnaceEngineBlock) {
                return new KineticStats(block);
            }
        }
        return null;
    }

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(HenryCreate.create(item)))
            );

    @Override
    public void onInitialize() {
        HenryTags.init();
        HenryCreativeModeTabs.register();
        HenryDisplaySources.register();
        HenryBlocks.register();
        HenryItems.register();
        HenryFluids.register();
        HenryBlockEntityTypes.register();
        HenryRecipeTypes.register();
        HenryParticleTypes.register();
        HenrySoundEvents.register();

        REGISTRATE.register();

        HenryPackets.registerPackets();
        HenryConfigs.register();

        HenryFanProcessingTypes.init();
        HenryFluids.registerFluidInteractions();

        SmartHopperBlockEntity.registerCapabilities();
        HydraulicPressBlockEntity.registerCapabilities();

        FurnaceEngineBlock.registerInteractionHandler();

        ServerLifecycleEvents.SERVER_STARTING.register(startingServer -> server = startingServer);
        ServerLifecycleEvents.SERVER_STOPPED.register(stoppedServer -> server = null);

        ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener(new SandingPolishCacheReloadListener());
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    private static final class SandingPolishCacheReloadListener implements IdentifiableResourceReloadListener {

        private static final ResourceLocation ID = HenryCreate.asResource("sanding_polish_cache");

        @Override
        public ResourceLocation getFabricId() {
            return ID;
        }

        @Override
        public List<ResourceLocation> getFabricDependencies() {
            return List.of(ResourceReloadListenerKeys.RECIPES);
        }

        @Override
        public CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier barrier,
                                              ResourceManager manager, ProfilerFiller prepareProfiler,
                                              ProfilerFiller applyProfiler, Executor prepareExecutor,
                                              Executor applyExecutor) {
            return barrier.wait(null).thenRunAsync(() -> {
                if (server != null) {
                    SandingType.buildPolishCache(server.getRecipeManager());
                }
            }, applyExecutor);
        }
    }
}
