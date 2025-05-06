package com.chocoboy.create_henry.registry;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.*;
import com.simibubi.create.content.kinetics.transmission.SplitShaftVisual;
import com.simibubi.create.content.kinetics.transmission.SplitShaftRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.chocoboy.create_henry.content.blocks.kinetics.hydraulic_press.HydraulicPressBlockEntity;
import com.chocoboy.create_henry.content.blocks.kinetics.hydraulic_press.HydraulicPressVisual;
import com.chocoboy.create_henry.content.blocks.kinetics.hydraulic_press.HydraulicPressRenderer;
import com.chocoboy.create_henry.content.blocks.kinetics.industrial_fan_block.IndustrialFanBlockEntity;
import com.chocoboy.create_henry.content.blocks.kinetics.industrial_fan_block.IndustrialFanVisual;
import com.chocoboy.create_henry.content.blocks.kinetics.industrial_fan_block.IndustrialFanRenderer;
import com.chocoboy.create_henry.content.blocks.kinetics.kinetic_motor.KineticMotorBlockEntity;
import com.chocoboy.create_henry.content.blocks.kinetics.kinetic_motor.KineticMotorRenderer;
import com.chocoboy.create_henry.content.blocks.kinetics.transmission.redstone_divider.RedstoneDividerBlockEntity;
import com.chocoboy.create_henry.content.blocks.kinetics.transmission.InverseBoxBlockEntity;
import com.chocoboy.create_henry.content.blocks.kinetics.furnace_engine.*;
import com.chocoboy.create_henry.content.blocks.kinetics.industrial_brake.IndustrialBrakeBlockEntity;
import com.chocoboy.create_henry.content.blocks.kinetics.multimeter.MultiMeterBlockEntity;
import com.chocoboy.create_henry.content.blocks.kinetics.industrial_brake.IndustrialBrakeRenderer;

import static com.chocoboy.create_henry.HenryCreate.REGISTRATE;

public class HenryBlockEntityTypes {

	public static final BlockEntityEntry<IndustrialFanBlockEntity> INDUSTRIAL_FAN = REGISTRATE
			.blockEntity("industrial_fan", IndustrialFanBlockEntity::new)
			.visual(() -> IndustrialFanVisual::new, false)
			.validBlocks(HenryBlocks.INDUSTRIAL_FAN)
			.renderer(() -> IndustrialFanRenderer::new)
			.register();

	public static final BlockEntityEntry<HydraulicPressBlockEntity> HYDRAULIC_PRESS = REGISTRATE
			.blockEntity("hydraulic_press", HydraulicPressBlockEntity::new)
			.visual(() -> HydraulicPressVisual::new)
			.validBlocks(HenryBlocks.HYDRAULIC_PRESS)
			.renderer(() -> HydraulicPressRenderer::new)
			.register();

	public static final BlockEntityEntry<FurnaceEngineBlockEntity> FURNACE_ENGINE = REGISTRATE
			.blockEntity("furnace_engine", FurnaceEngineBlockEntity::new)
			.visual(()->FurnaceEngineVisual::new)
			.validBlocks(HenryBlocks.FURNACE_ENGINE)
			.renderer(()-> FurnaceEngineRenderer::new)
			.register();

	public static final BlockEntityEntry<PoweredFlywheelBlockEntity> POWERED_FLYWHEEL = REGISTRATE
			.blockEntity("powered_flywheel", PoweredFlywheelBlockEntity::new)
			.visual(() -> PoweredFlywheelVisual::new, false)
			.validBlocks(HenryBlocks.POWERED_FLYWHEEL)
			.renderer(() -> PoweredFlywheelRenderer::new)
			.register();

	public static final BlockEntityEntry<InverseBoxBlockEntity> INVERSE_BOX = REGISTRATE
			.blockEntity("inverse_box", InverseBoxBlockEntity::new)
			.visual(() -> SplitShaftVisual::new, false)
			.validBlocks(HenryBlocks.INVERSE_BOX)
			.renderer(() -> SplitShaftRenderer::new)
			.register();

	public static final BlockEntityEntry<KineticMotorBlockEntity> KINETIC_MOTOR = REGISTRATE
			.blockEntity("motor", KineticMotorBlockEntity::new)
			.visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF), false)
			.validBlocks(HenryBlocks.KINETIC_MOTOR)
			.renderer(() -> KineticMotorRenderer::new).register();

	public static final BlockEntityEntry<IndustrialBrakeBlockEntity> INDUSTRIAL_BRAKE = REGISTRATE
			.blockEntity("industrial_brake", IndustrialBrakeBlockEntity::new)
			.visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF), false)
			.validBlocks(HenryBlocks.INDUSTRIAL_BRAKE)
			.renderer(() -> IndustrialBrakeRenderer::new).register();

	public static final BlockEntityEntry<MultiMeterBlockEntity> MULTIMETER = REGISTRATE
			.blockEntity("multimeter", MultiMeterBlockEntity::new)
			.visual(() -> ShaftVisual::new)
			.validBlocks(HenryBlocks.MULTIMETER)
			.renderer(() -> ShaftRenderer::new)
			.register();

	public static final BlockEntityEntry<RedstoneDividerBlockEntity> REDSTONE_DIVIDER = REGISTRATE
			.blockEntity("redstone_divider", RedstoneDividerBlockEntity::new)
			.visual(() -> SplitShaftVisual::new, false)
			.validBlocks(HenryBlocks.REDSTONE_DIVIDER)
			.renderer(() -> SplitShaftRenderer::new)
			.register();

	public static void register() {}
}
