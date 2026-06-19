package com.chocoboy.create_henry.registry;

import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.content.fluids.SolidRenderedPlaceableFluidType;
import com.chocoboy.create_henry.infrastructure.config.HenryConfigs;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.fabric.SimpleFlowableFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import io.github.fabricators_of_create.porting_lib.fluids.FluidInteractionRegistry;
import io.github.fabricators_of_create.porting_lib.fluids.FluidInteractionRegistry.InteractionInformation;
import io.github.fabricators_of_create.porting_lib.fluids.PortingLibFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class HenryFluids {

    private static final CreateRegistrate REGISTRATE = HenryCreate.registrate();

    static {
        REGISTRATE.setCreativeTab(HenryCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    private static final float FOG_DISTANCE_SCALE = 0.25f;

    // Render configuration for each fluid, consumed by the client entrypoint.
    public record FluidRendering(FluidEntry<SimpleFlowableFluid.Flowing> fluid, SolidRenderedPlaceableFluidType config) {}

    private static final List<FluidRendering> RENDERED_FLUIDS = new ArrayList<>();

    // Fluid registrations

    public static final FluidEntry<SimpleFlowableFluid.Flowing> CHOCOLATE_MILKSHAKE = newMilkshake(
            "Chocolate Milkshake",
            () -> HenryConfigs.client().chocolateFogColor.get(),
            () -> HenryConfigs.client().chocolateTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.CHOCOLATE.tag);

    public static final FluidEntry<SimpleFlowableFluid.Flowing> VANILLA_MILKSHAKE = newMilkshake(
            "Vanilla Milkshake",
            () -> HenryConfigs.client().vanillaFogColor.get(),
            () -> HenryConfigs.client().vanillaTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.VANILLA.tag);

    public static final FluidEntry<SimpleFlowableFluid.Flowing> STRAWBERRY_MILKSHAKE = newMilkshake(
            "Strawberry Milkshake",
            () -> HenryConfigs.client().strawberryFogColor.get(),
            () -> HenryConfigs.client().strawberryTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.STRAWBERRY.tag);

    public static final FluidEntry<SimpleFlowableFluid.Flowing> GLOWBERRY_MILKSHAKE = newMilkshake(
            "Glowberry Milkshake",
            () -> HenryConfigs.client().glowberryFogColor.get(),
            () -> HenryConfigs.client().glowberryTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.GLOWBERRY.tag);

    public static final FluidEntry<SimpleFlowableFluid.Flowing> PUMPKIN_MILKSHAKE = newMilkshake(
            "Pumpkin Milkshake",
            () -> HenryConfigs.client().pumpkinFogColor.get(),
            () -> HenryConfigs.client().pumpkinTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.PUMPKIN.tag);

    public static final FluidEntry<SimpleFlowableFluid.Flowing> SAP = newFluid(
            "Sap", 25,
            () -> HenryConfigs.client().sapFogColor.get(),
            () -> HenryConfigs.client().sapTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.SAP.tag);

    // Load this class
    public static void register() {}

    // Client render configuration

    public static List<FluidRendering> renderedFluids() {
        return RENDERED_FLUIDS;
    }

    // Lava interactions

    private record LavaInteraction(Fluid fluid, Block oreStone, Block defaultStone) {}

    private static boolean fluidInteractionsRegistered = false;

    // The palette stone base blocks are registered by Create's own ModInitializer. Resolving them
    // eagerly during Henry's onInitialize races with Create's registration, so the lookup is deferred
    // until the interactions are actually registered (server start), by which point Create has run.
    private static List<LavaInteraction> lavaInteractions() {
        return List.of(
            new LavaInteraction(CHOCOLATE_MILKSHAKE.get(), AllPaletteStoneTypes.VERIDIUM.getBaseBlock().get(),  Blocks.GRANITE),
            new LavaInteraction(VANILLA_MILKSHAKE.get(),   AllPaletteStoneTypes.ASURINE.getBaseBlock().get(),   Blocks.SANDSTONE),
            new LavaInteraction(STRAWBERRY_MILKSHAKE.get(), AllPaletteStoneTypes.CRIMSITE.getBaseBlock().get(), Blocks.COBBLED_DEEPSLATE),
            new LavaInteraction(GLOWBERRY_MILKSHAKE.get(), AllPaletteStoneTypes.OCHRUM.getBaseBlock().get(),    Blocks.TERRACOTTA),
            new LavaInteraction(PUMPKIN_MILKSHAKE.get(),   AllPaletteStoneTypes.LIMESTONE.getBaseBlock().get(),   AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get())
        );
    }

    public static void registerFluidInteractions() {
        if (fluidInteractionsRegistered)
            return;
        fluidInteractionsRegistered = true;
        for (LavaInteraction interaction : lavaInteractions())
            registerLavaInteraction(interaction.fluid(), interaction.oreStone(), interaction.defaultStone());
    }

    @Nullable
    public static BlockState getLavaInteraction(FluidState fluidState, Level level, BlockPos pos) {
        for (LavaInteraction interaction : lavaInteractions()) {
            if (isAdjacentToFluid(fluidState, interaction.fluid(), level, pos))
                return selectLavaResult(interaction.oreStone(), interaction.defaultStone(), level, pos);
        }
        return null;
    }

    // Registration helpers

    @SafeVarargs
    private static FluidEntry<SimpleFlowableFluid.Flowing> newMilkshake(String name, Supplier<Integer> fogColor, Supplier<Float> transparency, TagKey<Fluid>... tags) {
        return newFluid(name, 10, fogColor, transparency, tags);
    }

    @SafeVarargs
    private static FluidEntry<SimpleFlowableFluid.Flowing> newFluid(String name, int tickRate, Supplier<Integer> fogColor, Supplier<Float> transparency, TagKey<Fluid>... tags) {
        String id = name.toLowerCase().replace(" ", "_");
        ResourceLocation stillTex = new ResourceLocation(HenryCreate.MOD_ID, "fluid/" + id + "_still");
        ResourceLocation flowTex  = new ResourceLocation(HenryCreate.MOD_ID, "fluid/" + id + "_flow");
        SolidRenderedPlaceableFluidType renderConfig =
                SolidRenderedPlaceableFluidType.create(fogColor, () -> FOG_DISTANCE_SCALE * transparency.get(), stillTex, flowTex);

        FluidBuilder<SimpleFlowableFluid.Flowing, CreateRegistrate> builder = REGISTRATE.standardFluid(id);
        FluidEntry<SimpleFlowableFluid.Flowing> entry = builder
                .lang(name)
                .fluidProperties(p -> p.levelDecreasePerBlock(2).tickRate(tickRate).blastResistance(100f))
                .tag(tags)
                .source(SimpleFlowableFluid.Source::new)
                .block()
                .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models()
                        .getBuilder(ctx.getName())
                        .texture("particle", stillTex.toString())))
                .build()
                .bucket()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation("minecraft", "item/generated"))
                        .texture("layer0", new ResourceLocation(HenryCreate.MOD_ID, "item/" + ctx.getName())))
                .tag(HenryTags.forgeItemTag("buckets/" + id))
                .build()
                .register();

        RENDERED_FLUIDS.add(new FluidRendering(entry, renderConfig));
        return entry;
    }

    // Lava interaction helpers

    private static void registerLavaInteraction(Fluid fluid, Block oreStone, Block defaultStone) {
        var lavaType = PortingLibFluids.LAVA_TYPE;

        // Source lava touching this fluid -> obsidian
        FluidInteractionRegistry.addInteraction(lavaType, new InteractionInformation(
                (level, currentPos, relativePos, lavaState) ->
                        lavaState.isSource() && level.getFluidState(relativePos).is(fluid),
                Blocks.OBSIDIAN.defaultBlockState()
        ));

        // Flowing lava on an ore generator block -> ore stone (random chance)
        FluidInteractionRegistry.addInteraction(lavaType, new InteractionInformation(
                (level, currentPos, relativePos, lavaState) ->
                        !lavaState.isSource() &&
                        level.getFluidState(relativePos).is(fluid) &&
                        level.getBlockState(currentPos.below()).is(HenryTags.AllBlockTags.ORE_GENERATOR.tag) &&
                        randomChance(HenryConfigs.server().chanceForOreStone.get(), level),
                oreStone.defaultBlockState()
        ));

        // Flowing lava on an artificial ore generator block -> ore stone (random chance)
        FluidInteractionRegistry.addInteraction(lavaType, new InteractionInformation(
                (level, currentPos, relativePos, lavaState) ->
                        !lavaState.isSource() &&
                        level.getFluidState(relativePos).is(fluid) &&
                        level.getBlockState(currentPos.below()).is(HenryTags.AllBlockTags.ARTIFICIAL_ORE_GENERATOR.tag) &&
                        randomChance(HenryConfigs.server().chanceForArtificialOreStone.get(), level),
                oreStone.defaultBlockState()
        ));

        // Flowing lava touching this fluid anywhere else -> default stone
        FluidInteractionRegistry.addInteraction(lavaType, new InteractionInformation(
                (level, currentPos, relativePos, lavaState) ->
                        !lavaState.isSource() && level.getFluidState(relativePos).is(fluid),
                defaultStone.defaultBlockState()
        ));
    }

    private static boolean isAdjacentToFluid(FluidState fluidState, Fluid fluid, Level level, BlockPos pos) {
        if (fluidState.getType().isSame(fluid)) return true;
        boolean adjacentFluid = level.getFluidState(pos.relative(Direction.Axis.X, 1)).is(fluid)
                || level.getFluidState(pos.relative(Direction.Axis.Y, 1)).is(fluid)
                || level.getFluidState(pos.relative(Direction.Axis.Z, 1)).is(fluid);
        boolean replaceable = level.getBlockState(pos).isAir() || level.getBlockState(pos).canBeReplaced();
        return adjacentFluid && replaceable && level.getFluidState(pos).isEmpty();
    }

    private static BlockState selectLavaResult(Block oreStone, Block defaultStone, Level level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        if (below.is(HenryTags.AllBlockTags.ORE_GENERATOR.tag) && randomChance(HenryConfigs.server().chanceForOreStone.get(), level))
            return oreStone.defaultBlockState();
        if (below.is(HenryTags.AllBlockTags.ARTIFICIAL_ORE_GENERATOR.tag) && randomChance(HenryConfigs.server().chanceForArtificialOreStone.get(), level))
            return oreStone.defaultBlockState();
        return defaultStone.defaultBlockState();
    }

    private static boolean randomChance(int chance, Level level) {
        return level.getRandom().nextInt(100) < chance;
    }
}
