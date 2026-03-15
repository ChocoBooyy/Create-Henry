package com.chocoboy.create_henry.registry;

import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.content.fluids.SolidRenderedPlaceableFluidType;
import com.chocoboy.create_henry.infrastructure.config.HenryConfigs;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
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
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class HenryFluids {

    private static final CreateRegistrate REGISTRATE = HenryCreate.registrate();

    static {
        REGISTRATE.setCreativeTab(HenryCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    private static final float FOG_DISTANCE_SCALE = 0.25f;

    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHOCOLATE_MILKSHAKE = newMilkshake(
            "Chocolate Milkshake",
            () -> HenryConfigs.client().chocolateFogColor.get(),
            () -> HenryConfigs.client().chocolateTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.CHOCOLATE.tag).register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> VANILLA_MILKSHAKE = newMilkshake(
            "Vanilla Milkshake",
            () -> HenryConfigs.client().vanillaFogColor.get(),
            () -> HenryConfigs.client().vanillaTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.VANILLA.tag).register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> STRAWBERRY_MILKSHAKE = newMilkshake(
            "Strawberry Milkshake",
            () -> HenryConfigs.client().strawberryFogColor.get(),
            () -> HenryConfigs.client().strawberryTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.STRAWBERRY.tag).register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOWBERRY_MILKSHAKE = newMilkshake(
            "Glowberry Milkshake",
            () -> HenryConfigs.client().glowberryFogColor.get(),
            () -> HenryConfigs.client().glowberryTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.GLOWBERRY.tag).register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> PUMPKIN_MILKSHAKE = newMilkshake(
            "Pumpkin Milkshake",
            () -> HenryConfigs.client().pumpkinFogColor.get(),
            () -> HenryConfigs.client().pumpkinTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.PUMPKIN.tag).register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> SAP = newFluid(
            "Sap",
            () -> HenryConfigs.client().sapFogColor.get(),
            () -> HenryConfigs.client().sapTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.SAP.tag).register();

    // Load this class
    public static void register() {}

    // Registration helpers

    @SafeVarargs
    private static FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> newMilkshake(
            String name, Supplier<Integer> fogColor, Supplier<Float> transparency, TagKey<Fluid>... tags) {
        String id = name.toLowerCase().replace(" ", "_");
        ResourceLocation stillTex = new ResourceLocation(HenryCreate.MOD_ID, "fluid/" + id + "_still");
        ResourceLocation flowTex  = new ResourceLocation(HenryCreate.MOD_ID, "fluid/" + id + "_flow");
        return REGISTRATE.standardFluid(id,
                        SolidRenderedPlaceableFluidType.create(fogColor, () -> FOG_DISTANCE_SCALE * transparency.get(), stillTex, flowTex))
                .lang(name)
                .properties(b -> b.viscosity(1000).density(1400))
                .fluidProperties(p -> p.levelDecreasePerBlock(2).tickRate(10).slopeFindDistance(3).explosionResistance(100f))
                .tag(tags)
                .source(ForgeFlowingFluid.Source::new)
                .block()
                .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models()
                        .getBuilder(ctx.getName())
                        .texture("particle", stillTex.toString())))
                .build()
                .bucket()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation("minecraft", "item/generated"))
                        .texture("layer0", new ResourceLocation(HenryCreate.MOD_ID, "item/" + ctx.getName())))
                .tag(HenryTags.forgeItemTag("buckets/" + id))
                .build();
    }

    @SafeVarargs
    private static FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> newFluid(
            String name, Supplier<Integer> fogColor, Supplier<Float> transparency, TagKey<Fluid>... tags) {
        String id = name.toLowerCase().replace(" ", "_");
        ResourceLocation stillTex = new ResourceLocation(HenryCreate.MOD_ID, "fluid/" + id + "_still");
        ResourceLocation flowTex  = new ResourceLocation(HenryCreate.MOD_ID, "fluid/" + id + "_flow");
        return REGISTRATE.standardFluid(id,
                        SolidRenderedPlaceableFluidType.create(fogColor, () -> FOG_DISTANCE_SCALE * transparency.get(), stillTex, flowTex))
                .lang(name)
                .properties(b -> b.viscosity(2000).density(1400))
                .fluidProperties(p -> p.levelDecreasePerBlock(2).tickRate(25).slopeFindDistance(3).explosionResistance(100f))
                .tag(tags)
                .source(ForgeFlowingFluid.Source::new)
                .block()
                .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models()
                        .getBuilder(ctx.getName())
                        .texture("particle", stillTex.toString())))
                .build()
                .bucket()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation("minecraft", "item/generated"))
                        .texture("layer0", new ResourceLocation(HenryCreate.MOD_ID, "item/" + ctx.getName())))
                .tag(HenryTags.forgeItemTag("buckets/" + id))
                .build();
    }

    // Lava interactions

    public static void registerFluidInteractions() {
        addMilkshakeLavaInteraction(CHOCOLATE_MILKSHAKE.get(),
                AllPaletteStoneTypes.VERIDIUM.getBaseBlock().get(), AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get());
        addMilkshakeLavaInteraction(STRAWBERRY_MILKSHAKE.get(),
                AllPaletteStoneTypes.CRIMSITE.getBaseBlock().get(), Blocks.COBBLED_DEEPSLATE);
    }

    @Nullable
    public static BlockState getLavaInteraction(FluidState fluidState, Level level, BlockPos pos) {
        if (isNearMilkshake(fluidState, CHOCOLATE_MILKSHAKE.get(), level, pos))
            return selectLavaStoneResult(AllPaletteStoneTypes.VERIDIUM.getBaseBlock().get(), AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get(), level, pos);
        if (isNearMilkshake(fluidState, STRAWBERRY_MILKSHAKE.get(), level, pos))
            return selectLavaStoneResult(AllPaletteStoneTypes.CRIMSITE.getBaseBlock().get(), Blocks.COBBLED_DEEPSLATE, level, pos);
        return null;
    }

    private static void addMilkshakeLavaInteraction(Fluid fluid, Block oreStone, Block defaultStone) {
        var lavaType = ForgeMod.LAVA_TYPE.get();
        FluidInteractionRegistry.addInteraction(lavaType, new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, fluidState) ->
                        level.getFluidState(relativePos).is(fluid) && fluidState.isSource(),
                Blocks.OBSIDIAN.defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(lavaType, new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, fluidState) ->
                        randomChance(HenryConfigs.server().chanceForOreStone.get(), level) &&
                        level.getBlockState(currentPos.below()).is(HenryTags.AllBlockTags.ORE_GENERATOR.tag) &&
                        level.getFluidState(relativePos).is(fluid) && !fluidState.isSource(),
                oreStone.defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(lavaType, new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, fluidState) ->
                        randomChance(HenryConfigs.server().chanceForArtificialOreStone.get(), level) &&
                        level.getBlockState(currentPos.below()).is(HenryTags.AllBlockTags.ARTIFICIAL_ORE_GENERATOR.tag) &&
                        level.getFluidState(relativePos).is(fluid) && !fluidState.isSource(),
                oreStone.defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(lavaType, new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, fluidState) ->
                        level.getFluidState(relativePos).is(fluid) && !fluidState.isSource(),
                defaultStone.defaultBlockState()
        ));
    }

    private static boolean isNearMilkshake(FluidState fluidState, Fluid milkshake, Level level, BlockPos pos) {
        boolean isMilkshake = fluidState.getType().isSame(milkshake);
        boolean adjacentMilkshake = level.getFluidState(pos.relative(Direction.Axis.X, 1)).is(milkshake)
                || level.getFluidState(pos.relative(Direction.Axis.Y, 1)).is(milkshake)
                || level.getFluidState(pos.relative(Direction.Axis.Z, 1)).is(milkshake);
        boolean replaceable = level.getBlockState(pos).isAir() || level.getBlockState(pos).canBeReplaced();
        return isMilkshake || (adjacentMilkshake && replaceable && level.getFluidState(pos).isEmpty());
    }

    private static BlockState selectLavaStoneResult(Block oreStone, Block defaultStone, Level level, BlockPos pos) {
        if (level.getBlockState(pos.below()).is(HenryTags.AllBlockTags.ORE_GENERATOR.tag)
                && randomChance(HenryConfigs.server().chanceForOreStone.get(), level))
            return oreStone.defaultBlockState();
        if (level.getBlockState(pos.below()).is(HenryTags.AllBlockTags.ARTIFICIAL_ORE_GENERATOR.tag)
                && randomChance(HenryConfigs.server().chanceForArtificialOreStone.get(), level))
            return oreStone.defaultBlockState();
        return defaultStone.defaultBlockState();
    }

    private static boolean randomChance(int chance, Level level) {
        return level.getRandom().nextInt(100) < chance;
    }
}
