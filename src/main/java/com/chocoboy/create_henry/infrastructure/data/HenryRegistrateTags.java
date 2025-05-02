package com.chocoboy.create_henry.infrastructure.data;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.registry.HenryTags;
import net.minecraft.world.level.material.Fluid;

import static com.chocoboy.create_henry.registry.HenryTags.forgeItemTag;

@SuppressWarnings({"deprecation"})
public class HenryRegistrateTags {
    private static final CreateRegistrate REGISTRATE = HenryCreate.REGISTRATE;

    public static void addGenerators() {
        HenryCreate.REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, HenryRegistrateTags::genBlockTags);
        HenryCreate.REGISTRATE.addDataGenerator(ProviderType.FLUID_TAGS, HenryRegistrateTags::genFluidTags);
        HenryCreate.REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, HenryRegistrateTags::genItemTags);
    }
    private static void genItemTags(RegistrateTagsProvider<Item> provIn) {
        TagGen.CreateTagsProvider<Item> prov = new TagGen.CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);

        prov.tag(forgeItemTag("dusts/obsidian"))
                .add(AllItems.POWDERED_OBSIDIAN.get())
        ;
        prov.tag(forgeItemTag("dusts"))
                .add(AllItems.POWDERED_OBSIDIAN.get())
        ;
        prov.tag(HenryTags.AllItemTags.SEETHABLE.tag)
                .add(Items.ENDER_PEARL)
                .add(Items.NETHERRACK)
                .add(Items.SLIME_BALL)
                .add(Items.OBSIDIAN)
                .add(Items.COBBLESTONE)
                .add(Items.CALCITE)
                .add(Items.COAL_BLOCK)
                .add(Items.DEEPSLATE_COAL_ORE)
                .add(Items.ANCIENT_DEBRIS)
                .add(AllItems.CRUSHED_COPPER.get())
                .add(AllItems.CRUSHED_ZINC.get())
                .add(AllItems.CRUSHED_GOLD.get())
                .add(AllItems.CRUSHED_IRON.get())
                .add(AllItems.CRUSHED_OSMIUM.get())
                .add(AllItems.CRUSHED_PLATINUM.get())
                .add(AllItems.CRUSHED_SILVER.get())
                .add(AllItems.CRUSHED_TIN.get())
                .add(AllItems.CRUSHED_LEAD.get())
                .add(AllItems.CRUSHED_QUICKSILVER.get())
                .add(AllItems.CRUSHED_BAUXITE.get())
                .add(AllItems.CRUSHED_URANIUM.get())
                .add(AllItems.CRUSHED_NICKEL.get())
        ;
        prov.tag(HenryTags.AllItemTags.FREEZABLE.tag)
                .add(AllItems.BLAZE_CAKE.get())
                .add(Items.ICE)
                .add(Items.PACKED_ICE)
                .add(Items.WATER_BUCKET)
                .add(Items.MAGMA_CREAM)
                .add(Items.SNOWBALL)
                .add(Items.SNOW)
                .add(Items.CRYING_OBSIDIAN)
        ;
        prov.tag(HenryTags.AllItemTags.SANDABLE.tag)
                .add(Items.EXPOSED_COPPER)
                .add(Items.WEATHERED_COPPER)
                .add(Items.OXIDIZED_COPPER)
                .add(Items.EXPOSED_CUT_COPPER)
                .add(Items.WEATHERED_CUT_COPPER)
                .add(Items.OXIDIZED_CUT_COPPER)
                .add(Items.EXPOSED_CUT_COPPER_SLAB)
                .add(Items.WEATHERED_CUT_COPPER_SLAB)
                .add(Items.OXIDIZED_CUT_COPPER_SLAB)
                .add(Items.EXPOSED_CUT_COPPER_STAIRS)
                .add(Items.WEATHERED_CUT_COPPER_STAIRS)
                .add(Items.OXIDIZED_CUT_COPPER_STAIRS)
                .add(Items.POLISHED_ANDESITE)
                .add(Items.POLISHED_ANDESITE_SLAB)
                .add(Items.POLISHED_ANDESITE_STAIRS)
                .add(Items.POLISHED_GRANITE)
                .add(Items.POLISHED_GRANITE_SLAB)
                .add(Items.POLISHED_GRANITE_STAIRS)
                .add(Items.POLISHED_DIORITE)
                .add(Items.POLISHED_DIORITE_SLAB)
                .add(Items.POLISHED_DIORITE_STAIRS)
                .add(Items.POLISHED_DEEPSLATE)
                .add(Items.POLISHED_DEEPSLATE_SLAB)
                .add(Items.POLISHED_DEEPSLATE_STAIRS)
                .add(Items.POLISHED_DEEPSLATE_WALL)
                .add(Items.POLISHED_BASALT)
                .add(Items.MUD)
                .add(Items.WARPED_NYLIUM)
                .add(Items.CRIMSON_NYLIUM)
                .add(Items.MAGMA_BLOCK)
                .add(AllItems.ROSE_QUARTZ.get())
        ;

        prov.tag(HenryTags.AllItemTags.WITHERABLE.tag)
                .add(Items.CHARCOAL)
                .add(Items.COBBLESTONE)
                .add(Items.STONE)
                .add(Items.SLIME_BALL)
                .add(Items.GRASS_BLOCK)
        ;

        prov.tag(HenryTags.AllItemTags.MEAT.tag)
                .add(Items.BEEF, Items.PORKCHOP, Items.CHICKEN, Items.MUTTON, Items.COD, Items.SALMON, Items.RABBIT)
        ;

    }

    private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
        TagGen.CreateTagsProvider<Block> prov = new TagGen.CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);

        prov.tag(HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SANDING.tag)
                .add(Blocks.SAND)
                .add(Blocks.RED_SAND);

        prov.tag(HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_FREEZING.tag)
                .add(Blocks.POWDER_SNOW);

        prov.tag(HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SEETHING.tag)
                .add(AllBlocks.BLAZE_BURNER.get());

        prov.tag(HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_WITHERING.tag)
                .add(Blocks.WITHER_ROSE);

        prov.tag(HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING.tag)
                .add(Blocks.DRAGON_HEAD)
                .add(Blocks.DRAGON_WALL_HEAD);


        prov.tag(HenryTags.AllBlockTags.INDUSTRIAL_FAN_HEATER.tag)
                .add(Blocks.LAVA)
                .add(AllBlocks.BLAZE_BURNER.get());

        prov.tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
                .addTag(HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SANDING.tag)
                .addTag(HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_FREEZING.tag)
                .addTag(HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SEETHING.tag)
                .addTag(HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_WITHERING.tag)
                .addTag(HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING.tag);

        prov.tag(HenryTags.AllBlockTags.INDUSTRIAL_FAN_TRANSPARENT.tag)
                .addTag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag);

    }

    private static void genFluidTags(RegistrateTagsProvider<Fluid> provIn) {
    }
}
