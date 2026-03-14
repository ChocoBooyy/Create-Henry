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

    private static final ResourceLocation MILKSHAKE_STILL = new ResourceLocation(HenryCreate.MOD_ID, "fluid/milkshake_still");
    private static final ResourceLocation MILKSHAKE_FLOW  = new ResourceLocation(HenryCreate.MOD_ID, "fluid/milkshake_flow");

    // Tinted milkshakes — share a single grayscale texture, tinted per-flavor at render time
    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHOCOLATE_MILKSHAKE = newMilkshake(
            "Chocolate Milkshake", 0xB57847,
            () -> HenryConfigs.client().chocolateTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.CHOCOLATE.tag).register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> VANILLA_MILKSHAKE = newMilkshake(
            "Vanilla Milkshake", 0xFFDF8C,
            () -> HenryConfigs.client().vanillaTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.VANILLA.tag).register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> STRAWBERRY_MILKSHAKE = newMilkshake(
            "Strawberry Milkshake", 0xFF7BAA,
            () -> HenryConfigs.client().strawberryTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.STRAWBERRY.tag).register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> GLOWBERRY_MILKSHAKE = newMilkshake(
            "Glowberry Milkshake", 0xFFD22A,
            () -> HenryConfigs.client().glowberryTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.GLOWBERRY.tag).register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> PUMPKIN_MILKSHAKE = newMilkshake(
            "Pumpkin Milkshake", 0xFFAB4D,
            () -> HenryConfigs.client().pumpkinTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.PUMPKIN.tag).register();

    // Non-tinted fluid — uses its own dedicated colored texture
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SAP = newFluid(
            "Sap", 0xEAAE2F,
            () -> HenryConfigs.client().sapTransparencyMultiplier.getF(),
            HenryTags.AllFluidTags.SAP.tag).register();

    // Load this class
    public static void register() {}

    // Registration helpers

    @SafeVarargs
    private static FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> newMilkshake(String name, int tintColor, Supplier<Float> transparency, TagKey<Fluid>... tags) {
        String id = name.toLowerCase().replace(" ", "_");
        return REGISTRATE.standardFluid(id,
                        SolidRenderedPlaceableFluidType.createTinted(tintColor, () -> 1f / 4f * transparency.get(), MILKSHAKE_STILL, MILKSHAKE_FLOW))
                .lang(name)
                .properties(b -> b.viscosity(1000).density(1400))
                .fluidProperties(p -> p.levelDecreasePerBlock(2).tickRate(10).slopeFindDistance(3).explosionResistance(100f))
                .tag(tags)
                .source(ForgeFlowingFluid.Source::new)
                .block()
                .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models()
                        .getBuilder(ctx.getName())
                        .texture("particle", MILKSHAKE_STILL.toString())))
                .build()
                .bucket()
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation("minecraft", "item/generated"))
                        .texture("layer0", new ResourceLocation(HenryCreate.MOD_ID, "item/" + ctx.getName())))
                .tag(HenryTags.forgeItemTag("buckets/" + id))
                .build();
    }

    @SafeVarargs
    private static FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> newFluid(String name, int fogColor, Supplier<Float> transparency, TagKey<Fluid>... tags) {
        String id = name.toLowerCase().replace(" ", "_");
        ResourceLocation stillTex = new ResourceLocation(HenryCreate.MOD_ID, "fluid/" + id + "_still");
        return REGISTRATE.standardFluid(id,
                        SolidRenderedPlaceableFluidType.create(fogColor, () -> 1f / 4f * transparency.get()))
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
        addMilkshakeInteraction(CHOCOLATE_MILKSHAKE.get(),
                AllPaletteStoneTypes.VERIDIUM.getBaseBlock().get(), AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get());
        addMilkshakeInteraction(STRAWBERRY_MILKSHAKE.get(),
                AllPaletteStoneTypes.CRIMSITE.getBaseBlock().get(), Blocks.COBBLED_DEEPSLATE);
    }

    @Nullable
    public static BlockState getLavaInteraction(FluidState fluidState, Level level, BlockPos pos) {
        if (addMilkshakeFlag(fluidState, CHOCOLATE_MILKSHAKE.get(), level, pos))
            return addMilkshakeStones(AllPaletteStoneTypes.VERIDIUM.getBaseBlock().get(), AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get(), level, pos);
        if (addMilkshakeFlag(fluidState, STRAWBERRY_MILKSHAKE.get(), level, pos))
            return addMilkshakeStones(AllPaletteStoneTypes.CRIMSITE.getBaseBlock().get(), Blocks.COBBLED_DEEPSLATE, level, pos);
        return null;
    }

    public static void addMilkshakeInteraction(Fluid fluid, Block oreStone, Block defaultStone) {
        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, fluidState) ->
                        level.getFluidState(relativePos).is(fluid) && fluidState.isSource(),
                Blocks.OBSIDIAN.defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, fluidState) ->
                        randomChance(HenryConfigs.server().chanceForOreStone.get(), level) &&
                        level.getBlockState(currentPos.below()).is(HenryTags.AllBlockTags.ORE_GENERATOR.tag) &&
                        level.getFluidState(relativePos).is(fluid) && !fluidState.isSource(),
                oreStone.defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, fluidState) ->
                        randomChance(HenryConfigs.server().chanceForArtificialOreStone.get(), level) &&
                        level.getBlockState(currentPos.below()).is(HenryTags.AllBlockTags.ARTIFICIAL_ORE_GENERATOR.tag) &&
                        level.getFluidState(relativePos).is(fluid) && !fluidState.isSource(),
                oreStone.defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, fluidState) ->
                        level.getFluidState(relativePos).is(fluid) && !fluidState.isSource(),
                defaultStone.defaultBlockState()
        ));
    }

    public static boolean addMilkshakeFlag(FluidState fluidState, Fluid milkshake, Level level, BlockPos pos) {
        boolean isMilkshake = fluidState.getType().isSame(milkshake);
        boolean adjacentMilkshake = level.getFluidState(pos.relative(Direction.Axis.X, 1)).is(milkshake)
                || level.getFluidState(pos.relative(Direction.Axis.Y, 1)).is(milkshake)
                || level.getFluidState(pos.relative(Direction.Axis.Z, 1)).is(milkshake);
        boolean replaceable = level.getBlockState(pos).isAir() || level.getBlockState(pos).canBeReplaced();
        return isMilkshake || (adjacentMilkshake && replaceable && level.getFluidState(pos).isEmpty());
    }

    public static BlockState addMilkshakeStones(Block oreStone, Block defaultStone, Level level, BlockPos pos) {
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
