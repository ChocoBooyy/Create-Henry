package com.chocoboy.create_henry;

import com.mojang.logging.LogUtils;
import com.simibubi.create.*;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import com.chocoboy.create_henry.content.blocks.kinetics.furnace_engine.FurnaceEngineBlock;
import com.chocoboy.create_henry.infrastructure.config.HenryConfigs;
import com.chocoboy.create_henry.infrastructure.data.HenryDatagen;
import com.chocoboy.create_henry.registry.*;

import java.util.Random;


@SuppressWarnings({"removal","all"})
@Mod(HenryCreate.MOD_ID)
public class HenryCreate
{
    public static final String NAME = "Create: Henry";
    public static final String MOD_ID = "create_henry";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Random RANDOM = Create.RANDOM;

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

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

    static {
        REGISTRATE.setTooltipModifierFactory(item -> {
            return new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
                    .andThen(TooltipModifier.mapNull(HenryCreate.create(item)));
        });
    }

    public HenryCreate()
    {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        REGISTRATE.registerEventListeners(modEventBus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> HenryPartialModels::init);

        HenryTags.init();
        HenryCreativeModeTabs.register(modEventBus);
        HenrySpriteShifts.register();
        HenryBlocks.register();
        HenryItems.register();
        HenryFluids.register();
        HenryBlockEntityTypes.register();
        HenryFanProcessingTypes.register();
        HenryRecipeTypes.register(modEventBus);
        HenryParticleTypes.register(modEventBus);
        HenryPackets.registerPackets();

        //if (HenryMods.CREATECASING.isLoaded()) {
        //    EncasedCompat.register();
        //}


        HenryConfigs.register(modLoadingContext);

        modEventBus.addListener(HenryCreate::init);
        modEventBus.addListener(EventPriority.LOWEST, HenryDatagen::gatherData);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> HenryClient.onCtorClient(modEventBus, forgeEventBus));

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    public static void init(final FMLCommonSetupEvent event) {
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}