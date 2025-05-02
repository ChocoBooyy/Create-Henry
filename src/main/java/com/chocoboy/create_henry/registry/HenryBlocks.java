package com.chocoboy.create_henry.registry;

import com.chocoboy.create_henry.content.blocks.kinetics.negative_motor.HenryBlockStressDefaults;
import com.chocoboy.create_henry.content.blocks.kinetics.negative_motor.NegativeMotorBlock;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.gauge.GaugeGenerator;
import com.simibubi.create.content.kinetics.motor.CreativeMotorGenerator;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.content.redstone.displayLink.source.KineticSpeedDisplaySource;
import com.simibubi.create.content.redstone.displayLink.source.KineticStressDisplaySource;
import com.simibubi.create.foundation.data.*;
import com.simibubi.create.foundation.utility.Couple;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.util.ForgeSoundType;
import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.content.blocks.contraptions.bore_block.BoreBlock;
import com.chocoboy.create_henry.content.blocks.contraptions.bore_block.BoreBlockMovementBehaviour;
import com.chocoboy.create_henry.content.blocks.kinetics.multimeter.MultiMeterBlock;
import com.chocoboy.create_henry.content.blocks.kinetics.hydraulic_press.HydraulicPressBlock;
import com.chocoboy.create_henry.content.blocks.kinetics.industrial_fan_block.IndustrialFanBlock;
import com.chocoboy.create_henry.content.blocks.kinetics.furnace_engine.FurnaceEngineBlock;
import com.chocoboy.create_henry.content.blocks.kinetics.furnace_engine.FurnaceEngineGenerator;
import com.chocoboy.create_henry.content.blocks.kinetics.furnace_engine.PoweredFlywheelBlock;
import com.chocoboy.create_henry.content.blocks.kinetics.kinetic_motor.KineticMotorBlock;
import com.chocoboy.create_henry.content.blocks.kinetics.transmission.redstone_divider.RedstoneDividerBlock;
import com.chocoboy.create_henry.content.blocks.kinetics.transmission.InverseBoxBlock;
import static com.chocoboy.create_henry.HenryCreate.REGISTRATE;

import java.util.function.Consumer;

import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.*;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;

@SuppressWarnings({"unused", "removal", "all"})
public class HenryBlocks {
	static {
		REGISTRATE.setCreativeTab(HenryCreativeModeTabs.BASE_CREATIVE_TAB);
	}

	public static final BlockEntry<Block> RAW_RUBBER_BLOCK = REGISTRATE.block("raw_rubber_block", Block::new)
			.properties(p -> p.mapColor(MapColor.TERRACOTTA_WHITE))
			.properties(p -> p.strength(0.5f,1.5f))
			.lang("Block of Raw Rubber")
			.item()
			.build()
			.register();

	public static final BlockEntry<Block> RUBBER_BLOCK = REGISTRATE.block("rubber_block", Block::new)
			.properties(p -> p.mapColor(MapColor.TERRACOTTA_GRAY))
			.properties(p -> p.strength(0.5f,1.5f))
			.lang("Block of Rubber")
			.item()
			.build()
			.register();

	public static final BlockEntry<CasingBlock> HYDRAULIC_CASING = REGISTRATE.block("hydraulic_casing", CasingBlock::new)
			.transform(BuilderTransformers.casing(() -> HenrySpriteShifts.HYDRAULIC_CASING))
			.properties(p -> p.mapColor(MapColor.COLOR_ORANGE)
					.requiresCorrectToolForDrops()
					.sound(SoundType.COPPER))
			.transform(pickaxeOnly())
			.lang("Hydraulic Casing")
			.item()
			.build()
			.register();

	public static final BlockEntry<CasingBlock> INDUSTRIAL_CASING = REGISTRATE.block("industrial_casing", CasingBlock::new)
			.transform(BuilderTransformers.casing(() -> HenrySpriteShifts.INDUSTRIAL_CASING))
			.properties(p -> p.mapColor(MapColor.TERRACOTTA_CYAN)
					.requiresCorrectToolForDrops()
					.sound(SoundType.NETHERITE_BLOCK))
			.transform(pickaxeOnly())
			.lang("Industrial Casing")
			.item()
			.build()
			.register();

	public static final BlockEntry<IndustrialFanBlock> INDUSTRIAL_FAN = REGISTRATE.block("industrial_fan", IndustrialFanBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.noOcclusion()
					.mapColor(MapColor.TERRACOTTA_CYAN)
					.requiresCorrectToolForDrops()
					.sound(SoundType.NETHERITE_BLOCK))
			.blockstate(BlockStateGen.directionalBlockProvider(true))
			.addLayer(() -> RenderType::cutoutMipped)
			.transform(pickaxeOnly())
			.transform(BlockStressDefaults.setImpact(4.0))
			.recipe((c, p) -> {
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
						.pattern("CIP")
						.define('P', AllItems.PROPELLER.get())
						.define('C', AllBlocks.COGWHEEL.get())
						.define('I', INDUSTRIAL_CASING.get())
						.unlockedBy("has_casing", has(INDUSTRIAL_CASING.get()))
						.save(p, HenryCreate.asResource("crafting/" + c.getName()));
			})
			.lang("Industrial Fan")
			.item()
			.transform(customItemModel())
			.register();


	public static final BlockEntry<HydraulicPressBlock> HYDRAULIC_PRESS = REGISTRATE.block("hydraulic_press", HydraulicPressBlock::new)
			.initialProperties(SharedProperties::copperMetal)
			.properties(BlockBehaviour.Properties::noOcclusion)
			.properties(p -> p.noOcclusion().mapColor(MapColor.TERRACOTTA_ORANGE))
			.transform(pickaxeOnly())
			.blockstate(BlockStateGen.horizontalBlockProvider(true))
			.transform(BlockStressDefaults.setImpact(64.0))
			.item(AssemblyOperatorBlockItem::new)
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BoreBlock> BORE_BLOCK = REGISTRATE.block("bore_block", BoreBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.mapColor(MapColor.STONE))
			.properties(p -> p.sound(new ForgeSoundType(0.9f, 1.25f, () -> SoundEvents.NETHERITE_BLOCK_BREAK,
					() -> SoundEvents.NETHERITE_BLOCK_STEP, () -> SoundEvents.NETHERITE_BLOCK_PLACE,
					() -> SoundEvents.NETHERITE_BLOCK_HIT, () -> SoundEvents.NETHERITE_BLOCK_FALL)))
			.onRegister(movementBehaviour(new BoreBlockMovementBehaviour()))
			.transform(pickaxeOnly())
			.recipe((c, p) -> {
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 4)
						.pattern("AIA")
						.pattern("ICI")
						.pattern("AIA")
						.define('A', AllItems.ANDESITE_ALLOY.get())
						.define('C', AllBlocks.ANDESITE_ALLOY_BLOCK.get())
						.define('I', Items.IRON_INGOT)
						.unlockedBy("has_" + c.getName(), has(c.get()))
						.save(p, HenryCreate.asResource("crafting/" + c.getName()));
			})
			.item()
			.build()
			.register();

	public static final BlockEntry<MultiMeterBlock> MULTIMETER = REGISTRATE.block("multimeter", MultiMeterBlock::new)
			.initialProperties(SharedProperties::wooden)
			.properties(p -> p.mapColor(MapColor.PODZOL))
			.transform(axeOrPickaxe())
			.transform(BlockStressDefaults.setNoImpact())
			.blockstate(new GaugeGenerator()::generate)
			.onRegister(assignDataBehaviour(new KineticSpeedDisplaySource(), "kinetic_speed"))
			.onRegister(assignDataBehaviour(new KineticStressDisplaySource(), "kinetic_stress"))
			.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
					.requires(AllBlocks.STRESSOMETER.get())
					.requires(AllBlocks.SPEEDOMETER.get())
					.unlockedBy("has_compass", has(Items.COMPASS))
					.save(p, HenryCreate.asResource("crafting/multimeter")))
			.item()
			.transform(ModelGen.customItemModel("gauge", "_", "item"))
			.register();

	public static final BlockEntry<RedstoneDividerBlock> REDSTONE_DIVIDER = REGISTRATE.block("redstone_divider", RedstoneDividerBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
			.addLayer(() -> RenderType::cutoutMipped)
			.transform(BlockStressDefaults.setNoImpact())
			.transform(axeOrPickaxe())
			.blockstate((c, p) -> BlockStateGen.axisBlock(c, p, s -> {
				int power = s.getValue(BlockStateProperties.POWER);
				return AssetLookup.partialBaseModel(c, p, "power_" + (
						power == 0 || power == 1 || power == 2 ? 0 :
								power == 3 || power == 4 || power == 5 ? 1 :
										power == 6 || power == 7 || power == 8 ? 2 :
												power == 9 || power == 10 || power == 11 ? 3 : 4));
			}))
			.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
					.requires(AllBlocks.ANDESITE_CASING.get())
					.requires(AllBlocks.LARGE_COGWHEEL.get())
					.requires(Items.REDSTONE)
					.unlockedBy("has_cogwheel", has(AllBlocks.COGWHEEL.get()))
					.save(p, HenryCreate.asResource("crafting/kinetics/redstone_divider")))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<InverseBoxBlock> INVERSE_BOX = REGISTRATE.block("inverse_box", InverseBoxBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
			.addLayer(() -> RenderType::cutoutMipped)
			.transform(BlockStressDefaults.setNoImpact())
			.transform(axeOrPickaxe())
			.blockstate(BlockStateGen.axisBlockProvider(true))
			.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
					.requires(AllBlocks.ANDESITE_CASING.get())
					.requires(AllBlocks.COGWHEEL.get())
					.unlockedBy("has_cogwheel", has(AllBlocks.COGWHEEL.get()))
					.save(p, HenryCreate.asResource("crafting/kinetics/inverse_box")))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<KineticMotorBlock> KINETIC_MOTOR = REGISTRATE
			.block("kinetic_motor", KineticMotorBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.mapColor(MapColor.COLOR_GRAY))
			.tag(AllTags.AllBlockTags.SAFE_NBT.tag)
			.transform(axeOrPickaxe())
			.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
					.requires(AllBlocks.ANDESITE_CASING.get())
					.requires(HenryItems.KINETIC_MECHANISM.get())
					.unlockedBy("has_kinetic_mechanism", has(HenryItems.KINETIC_MECHANISM.get()))
					.save(p, HenryCreate.asResource("crafting/kinetics/kinetic_motor")))
			.blockstate(new CreativeMotorGenerator()::generate)
			.transform(BlockStressDefaults.setCapacity(48))
			.transform(BlockStressDefaults.setGeneratorSpeed(() -> Couple.create(0, 32)))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<NegativeMotorBlock> NEGATIVE_MOTOR = REGISTRATE
			.block("negative_motor", NegativeMotorBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.mapColor(MapColor.COLOR_GRAY))
			.tag(AllTags.AllBlockTags.SAFE_NBT.tag)
			.transform(axeOrPickaxe())
			.blockstate(new CreativeMotorGenerator()::generate)
			.transform(HenryBlockStressDefaults.setImpact(() ->
					Couple.create(1.0, 1.0)
			))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<FurnaceEngineBlock> FURNACE_ENGINE =
			REGISTRATE.block("furnace_engine", FurnaceEngineBlock::new)
					.initialProperties(SharedProperties::softMetal)
					.properties(p -> p.mapColor(MapColor.TERRACOTTA_CYAN)
							.sound(SoundType.NETHERITE_BLOCK))
					.properties(BlockBehaviour.Properties::noOcclusion)
					.transform(pickaxeOnly())
					.tag(AllTags.AllBlockTags.BRITTLE.tag)
					.blockstate(new FurnaceEngineGenerator()::generate)
					.transform(BlockStressDefaults.setCapacity(256.0))
					.transform(BlockStressDefaults.setGeneratorSpeed(FurnaceEngineBlock::getSpeedRange))
					.item()
					.transform(ModelGen.customItemModel())
					.register();

	public static final BlockEntry<PoweredFlywheelBlock> POWERED_FLYWHEEL =
			REGISTRATE.block("powered_flywheel", PoweredFlywheelBlock::new)
					.initialProperties(SharedProperties::softMetal)
					.properties(p -> p.mapColor(MapColor.METAL))
					.transform(pickaxeOnly())
					.blockstate(BlockStateGen.axisBlockProvider(false))
					.loot((lt, block) -> lt.dropOther(block, AllBlocks.FLYWHEEL.get()))
					.register();

	// Load this class

	public static void register() {}

}