package com.chocoboy.create_henry.infrastructure.ponder.scenes;

import com.chocoboy.create_henry.registry.HenryBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.gauge.GaugeBlock;
import com.chocoboy.create_henry.content.blocks.kinetics.multimeter.MultiMeterBlockEntity;
import com.simibubi.create.content.redstone.analogLever.AnalogLeverBlockEntity;
import com.chocoboy.create_henry.content.blocks.kinetics.transmission.redstone_divider.RedstoneDividerBlock;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.createmod.ponder.api.ParticleEmitter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class KineticsScenes {
    public static void multimeter(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("multimeter", "Reading Speed and Stress with the Multimeter");
        scene.configureBasePlate(1, 0, 5);

        BlockPos gaugePos = util.grid().at(2, 1, 3);

        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);

        for (int x = 6; x >= 0; x--) {
            scene.idle(2);
            scene.world().showSection(util.select().position(x, 1, 3), Direction.DOWN);
        }
        scene.idle(10);

        scene.world().setBlock(gaugePos, HenryBlocks.MULTIMETER.getDefaultState()
                .setValue(GaugeBlock.FACING, Direction.UP), true);
        scene.world().setKineticSpeed(util.select().position(gaugePos), 32);
        scene.world().modifyBlockEntityNBT(util.select().position(gaugePos), MultiMeterBlockEntity.class,
                nbt -> nbt.putFloat("SpeedValue", MultiMeterBlockEntity.getDialTarget(32)));
        scene.idle(10);

        scene.overlay().showText(80)
                .text("The Multimeter combines a Speedometer and Stressometer into one block, tracking both shaft Speed and network Stress load simultaneously")
                .attachKeyFrame()
                .pointAt(util.vector().topOf(gaugePos))
                .placeNearTarget();
        scene.idle(90);

        scene.world().multiplyKineticSpeed(util.select().everywhere(), 4);
        scene.world().modifyBlockEntityNBT(util.select().position(gaugePos), MultiMeterBlockEntity.class,
                nbt -> nbt.putFloat("SpeedValue", MultiMeterBlockEntity.getDialTarget(128)));
        scene.effects().rotationSpeedIndicator(util.grid().at(6, 1, 3));
        scene.idle(5);
        scene.effects().indicateSuccess(gaugePos);


        BlockState state = AllBlocks.CRUSHING_WHEEL.getDefaultState()
                .setValue(CrushingWheelBlock.AXIS, Direction.Axis.X);
        scene.world().setBlock(util.grid().at(5, 1, 3), state, true);
        scene.world().setKineticSpeed(util.select().position(5, 1, 3), 32);
        scene.world().modifyBlockEntityNBT(util.select().position(gaugePos), MultiMeterBlockEntity.class,
                nbt -> nbt.putFloat("StressValue", .5f));
        scene.effects().indicateRedstone(gaugePos);
        scene.idle(20);
        scene.world().setBlock(util.grid().at(4, 1, 3), state, true);
        scene.world().setKineticSpeed(util.select().position(4, 1, 3), 32);
        scene.world().modifyBlockEntityNBT(util.select().position(gaugePos), MultiMeterBlockEntity.class,
                nbt -> nbt.putFloat("StressValue", .9f));
        scene.effects().indicateRedstone(gaugePos);
        scene.idle(10);

        scene.idle(30);

        Vec3 blockSurface = util.vector().blockSurface(gaugePos, Direction.NORTH);
        scene.overlay().showControls(blockSurface, Pointing.RIGHT, 80).withItem(AllItems.GOGGLES.asStack());
        scene.idle(7);
        scene.overlay().showText(80)
                .text("Wearing Engineers' Goggles provides exact RPM and SU readouts from the gauge")
                .attachKeyFrame()
                .colored(PonderPalette.MEDIUM)
                .pointAt(blockSurface)
                .placeNearTarget();
        scene.idle(100);

        scene.markAsFinished();
    }

    public static void industrialBrake(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("industrial_brake", "Applying Stress Load with the Industrial Brake");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);

        BlockPos brakePos = util.grid().at(3, 1, 2);
        BlockPos meterPos = util.grid().at(1, 1, 2);

        for (int i = 0; i < 4; i++) {
            scene.idle(5);
            scene.world().showSection(util.select().position(i, 1, 2), Direction.DOWN);
        }

        scene.world().setKineticSpeed(util.select().fromTo(0, 1, 2, 3, 1, 2), 32);
        scene.world().modifyBlockEntityNBT(util.select().position(meterPos), MultiMeterBlockEntity.class, nbt -> {
            nbt.putFloat("SpeedValue", MultiMeterBlockEntity.getDialTarget(32));
            nbt.putFloat("StressValue", 0.35f);
        });
        scene.idle(10);

        Vec3 brakeAbove = util.vector().of(3, 2.5f, 2);
        scene.overlay().showText(70)
                .text("The Industrial Brake draws a flat amount of Stress from the network, regardless of RPM")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(brakeAbove);
        scene.idle(80);

        Vec3 brakePanel = util.vector().blockSurface(brakePos, Direction.NORTH).add(1 / 16f, 0, 3 / 16f);
        scene.overlay().showFilterSlotInput(brakePanel, Direction.NORTH, 80);
        scene.overlay().showControls(brakePanel, Pointing.DOWN, 60).rightClick();
        scene.idle(20);

        scene.overlay().showText(60)
                .text("Right-click the panel to set how many SU it draws (0 to 256)")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(brakeAbove);
        scene.idle(70);

        scene.world().modifyBlockEntityNBT(util.select().position(meterPos), MultiMeterBlockEntity.class,
                nbt -> nbt.putFloat("StressValue", 0.85f));
        scene.overlay().showText(60)
                .text("Raising the draw increases the load on the network")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().of(1, 2.5f, 2));
        scene.idle(70);

        scene.markAsFinished();
    }

    public static void kineticMotor(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("kinetic_motor", "Generating Rotational Force using Kinetic Motors");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);

        BlockPos motor = util.grid().at(3, 1, 2);

        for (int i = 0; i < 3; i++) {
            scene.idle(5);
            scene.world().showSection(util.select().position(1 + i, 1, 2), Direction.DOWN);
        }

        scene.world().setKineticSpeed(util.select().fromTo(1, 1, 2, 3, 1, 2), 32);
        scene.world().modifyBlockEntityNBT(util.select().position(util.grid().at(1, 1, 2)), MultiMeterBlockEntity.class,
                nbt -> nbt.putFloat("SpeedValue", MultiMeterBlockEntity.getDialTarget(32)));
        scene.idle(10);
        scene.effects().rotationSpeedIndicator(motor);
        scene.overlay().showText(50)
                .text("Kinetic motors are a compact and configurable source of Rotational Force")
                .placeNearTarget()
                .pointAt(util.vector().topOf(motor));
        scene.idle(70);

        Vec3 blockSurface = util.vector().blockSurface(motor, Direction.NORTH)
                .add(1 / 16f, 0, 3 / 16f);
        scene.overlay().showFilterSlotInput(blockSurface, Direction.NORTH, 80);
        scene.overlay().showControls(blockSurface, Pointing.DOWN, 60).rightClick();
        scene.idle(20);

        scene.overlay().showText(60)
                .text("The generated speed can be configured on its input panels")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(blockSurface);
        scene.idle(10);
        scene.idle(50);
        scene.world().modifyKineticSpeed(util.select().fromTo(1, 1, 2, 3, 1, 2), f -> 4 * f);
        scene.world().modifyBlockEntityNBT(util.select().position(util.grid().at(1, 1, 2)), MultiMeterBlockEntity.class,
                nbt -> nbt.putFloat("SpeedValue", MultiMeterBlockEntity.getDialTarget(128)));
        scene.idle(10);

        scene.effects().rotationSpeedIndicator(motor);
    }

    public static void furnaceEngine(SceneBuilder default_scene, SceneBuildingUtil util) {
        furnaceEngine(new CreateSceneBuilder(default_scene), util, false);
    }

    public static void flywheel(SceneBuilder default_scene, SceneBuildingUtil util) {
        furnaceEngine(new CreateSceneBuilder(default_scene), util, true);
    }

    private static void furnaceEngine(CreateSceneBuilder scene, SceneBuildingUtil util, boolean showFlywheel) {
        scene.title(showFlywheel ? "flywheel" : "furnace_engine",
                showFlywheel ? "The Powered Flywheel" : "Generating Rotational Force with the Flywheel Engine");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);

        BlockPos shaftPos = util.grid().at(1, 1, 0);
        BlockPos meterPos = util.grid().at(1, 1, 1);
        BlockPos cogPos = util.grid().at(1, 1, 2);
        BlockPos flywheelPos = util.grid().at(1, 1, 3);
        BlockPos enginePos = util.grid().at(3, 1, 3);
        BlockPos furnacePos = util.grid().at(4, 1, 3);

        scene.idle(5);
        scene.world().showSection(util.select().position(furnacePos), Direction.DOWN);
        scene.idle(3);
        scene.world().showSection(util.select().position(enginePos), Direction.DOWN);
        scene.idle(3);
        scene.world().showSection(util.select().position(flywheelPos), Direction.EAST);
        scene.idle(3);
        scene.world().showSection(util.select().position(cogPos), Direction.EAST);
        scene.idle(3);
        scene.world().showSection(util.select().position(meterPos), Direction.EAST);
        scene.idle(3);
        scene.world().showSection(util.select().position(shaftPos), Direction.EAST);
        scene.idle(10);

        String introText = showFlywheel
                ? "The Powered Flywheel connects the Flywheel Engine to the kinetic network"
                : "The Flywheel Engine generates Rotational Force while its Blast Furnace is running";
        scene.overlay().showText(60)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(showFlywheel ? flywheelPos : enginePos))
                .text(introText);
        scene.idle(70);

        scene.addKeyframe();
        scene.overlay().showControls(util.vector().topOf(furnacePos), Pointing.DOWN, 30)
                .withItem(new ItemStack(Items.OAK_LOG));
        scene.idle(5);
        scene.overlay().showControls(util.vector().blockSurface(furnacePos, Direction.NORTH), Pointing.RIGHT, 30)
                .withItem(new ItemStack(Items.COAL));
        scene.idle(7);
        scene.world().cycleBlockProperty(furnacePos, FurnaceBlock.LIT);
        ParticleEmitter lava = scene.effects().simpleParticleEmitter(ParticleTypes.LAVA, Vec3.ZERO);
        scene.effects().emitParticles(util.vector().of(4.5f, 1.5f, 2.9f), lava, 4, 1);
        scene.world().setKineticSpeed(util.select().fromTo(1, 1, 0, 1, 1, 3), 24);
        scene.world().modifyBlockEntityNBT(util.select().position(meterPos), MultiMeterBlockEntity.class,
                nbt -> nbt.putFloat("SpeedValue", MultiMeterBlockEntity.getDialTarget(24)));
        scene.idle(40);

        scene.effects().rotationSpeedIndicator(shaftPos);
        scene.overlay().showText(50)
                .attachKeyFrame()
                .placeNearTarget()
                .colored(PonderPalette.GREEN)
                .pointAt(util.vector().blockSurface(meterPos, Direction.WEST))
                .text("It provides a moderate but reliable source of Rotational Force");
        scene.idle(60);

        scene.markAsFinished();
    }

    public static void inverseBox(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("inverse_box", "Inverting rotation with the Inverse Box");
        scene.configureBasePlate(0, 0, 5);

        BlockPos inverseBoxPos = util.grid().at(3, 1, 2);

        //region Setup
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(1, 1, 2, 5, 1, 2), Direction.DOWN);
        scene.world().showSection(util.select().position(5, 0, 1), Direction.UP);
        scene.idle(10);
        //endregion

        //region Relay
        // Left (input) side: positive speed; right (output) side: inverted
        scene.world().setKineticSpeed(util.select().fromTo(1, 1, 2, 3, 1, 2), 32);
        scene.world().setKineticSpeed(util.select().fromTo(3, 1, 2, 5, 1, 2), -32);
        scene.world().setKineticSpeed(util.select().position(5, 0, 1), 16);
        scene.effects().rotationDirectionIndicator(util.grid().at(1, 1, 2));
        scene.effects().rotationDirectionIndicator(util.grid().at(5, 1, 2));
        scene.overlay().showText(60)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(inverseBoxPos))
                .text("The Inverse Box relays rotation along a shaft");
        scene.idle(70);
        //endregion

        //region Inversion
        scene.effects().rotationDirectionIndicator(util.grid().at(1, 1, 2));
        scene.effects().rotationDirectionIndicator(util.grid().at(5, 1, 2));
        scene.overlay().showText(70)
                .colored(PonderPalette.RED)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(inverseBoxPos))
                .text("The rotation on the other side is always inverted");
        scene.idle(80);
        scene.markAsFinished();
        //endregion
    }

    public static void redstoneDivider(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("redstone_divider", "Dividing rotational speed with the Redstone Divider");
        scene.configureBasePlate(0, 0, 5);

        BlockPos dividerPos = util.grid().at(3, 1, 3);
        BlockPos leverPos   = util.grid().at(1, 1, 2);

        //region Setup
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(1, 1, 3, 5, 1, 3), Direction.DOWN);
        scene.world().showSection(util.select().position(5, 0, 2), Direction.UP);
        scene.idle(10);
        //endregion

        //region Relay
        scene.world().setKineticSpeed(util.select().fromTo(1, 1, 3, 5, 1, 3), 32);
        scene.world().setKineticSpeed(util.select().position(5, 0, 2), 16);
        scene.overlay().showText(60)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(dividerPos))
                .text("The Redstone Divider relays rotation like a normal shaft when unpowered");
        scene.idle(70);
        //endregion

        //region Speed control
        // Reveal lever, then trace the wire from lever toward the divider
        scene.world().showSection(util.select().position(leverPos), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(1, 1, 1, 3, 1, 2), Direction.DOWN);
        scene.idle(10);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().centerOf(leverPos))
                .text("The Analog Lever controls the signal strength sent to the divider");
        scene.idle(70);

        // Click 1: lever state 6 -> wire powers 6,5,4,3 -> divider power 3 (model power_1, x0.75)
        scene.overlay().showControls(util.vector().centerOf(leverPos), Pointing.DOWN, 25).rightClick();
        scene.idle(5);
        scene.world().modifyBlockEntityNBT(util.select().position(leverPos), AnalogLeverBlockEntity.class, nbt -> nbt.putInt("State", 6));
        scene.world().modifyBlock(util.grid().at(1, 1, 1), s -> s.setValue(RedstoneDividerBlock.POWER, 6), false);
        scene.world().modifyBlock(util.grid().at(2, 1, 1), s -> s.setValue(RedstoneDividerBlock.POWER, 5), false);
        scene.world().modifyBlock(util.grid().at(3, 1, 1), s -> s.setValue(RedstoneDividerBlock.POWER, 4), false);
        scene.world().modifyBlock(util.grid().at(3, 1, 2), s -> s.setValue(RedstoneDividerBlock.POWER, 3), false);
        scene.world().modifyBlock(dividerPos, s -> s.setValue(RedstoneDividerBlock.POWER, 3), false);
        scene.effects().indicateRedstone(leverPos);
        scene.world().modifyKineticSpeed(util.select().fromTo(1, 1, 3, 2, 1, 3), f -> 24f);
        scene.overlay().showText(60)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(dividerPos))
                .text("A Redstone signal reduces the output speed");
        scene.idle(80);

        // Click 2: lever state 9 -> wire powers 9,8,7,6 -> divider power 6 (model power_2, x0.50)
        scene.overlay().showControls(util.vector().centerOf(leverPos), Pointing.DOWN, 25).rightClick();
        scene.idle(5);
        scene.world().modifyBlockEntityNBT(util.select().position(leverPos), AnalogLeverBlockEntity.class, nbt -> nbt.putInt("State", 9));
        scene.world().modifyBlock(util.grid().at(1, 1, 1), s -> s.setValue(RedstoneDividerBlock.POWER, 9), false);
        scene.world().modifyBlock(util.grid().at(2, 1, 1), s -> s.setValue(RedstoneDividerBlock.POWER, 8), false);
        scene.world().modifyBlock(util.grid().at(3, 1, 1), s -> s.setValue(RedstoneDividerBlock.POWER, 7), false);
        scene.world().modifyBlock(util.grid().at(3, 1, 2), s -> s.setValue(RedstoneDividerBlock.POWER, 6), false);
        scene.world().modifyBlock(dividerPos, s -> s.setValue(RedstoneDividerBlock.POWER, 6), false);
        scene.effects().indicateRedstone(leverPos);
        scene.world().modifyKineticSpeed(util.select().fromTo(1, 1, 3, 2, 1, 3), f -> 16f);
        scene.overlay().showText(60)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(dividerPos))
                .text("Stronger signals divide the speed further");
        scene.idle(80);

        // Click 3: lever state 12 -> wire powers 12,11,10,9 -> divider power 9 (model power_3, x0.25)
        scene.overlay().showControls(util.vector().centerOf(leverPos), Pointing.DOWN, 25).rightClick();
        scene.idle(5);
        scene.world().modifyBlockEntityNBT(util.select().position(leverPos), AnalogLeverBlockEntity.class, nbt -> nbt.putInt("State", 12));
        scene.world().modifyBlock(util.grid().at(1, 1, 1), s -> s.setValue(RedstoneDividerBlock.POWER, 12), false);
        scene.world().modifyBlock(util.grid().at(2, 1, 1), s -> s.setValue(RedstoneDividerBlock.POWER, 11), false);
        scene.world().modifyBlock(util.grid().at(3, 1, 1), s -> s.setValue(RedstoneDividerBlock.POWER, 10), false);
        scene.world().modifyBlock(util.grid().at(3, 1, 2), s -> s.setValue(RedstoneDividerBlock.POWER, 9), false);
        scene.world().modifyBlock(dividerPos, s -> s.setValue(RedstoneDividerBlock.POWER, 9), false);
        scene.effects().indicateRedstone(leverPos);
        scene.world().modifyKineticSpeed(util.select().fromTo(1, 1, 3, 2, 1, 3), f -> 8f);
        scene.idle(60);

        // Click 4: lever state 15 -> wire powers 15,14,13,12 -> divider power 12 (model power_4, x0.00)
        scene.overlay().showControls(util.vector().centerOf(leverPos), Pointing.DOWN, 25).rightClick();
        scene.idle(5);
        scene.world().modifyBlockEntityNBT(util.select().position(leverPos), AnalogLeverBlockEntity.class, nbt -> nbt.putInt("State", 15));
        scene.world().modifyBlock(util.grid().at(1, 1, 1), s -> s.setValue(RedstoneDividerBlock.POWER, 15), false);
        scene.world().modifyBlock(util.grid().at(2, 1, 1), s -> s.setValue(RedstoneDividerBlock.POWER, 14), false);
        scene.world().modifyBlock(util.grid().at(3, 1, 1), s -> s.setValue(RedstoneDividerBlock.POWER, 13), false);
        scene.world().modifyBlock(util.grid().at(3, 1, 2), s -> s.setValue(RedstoneDividerBlock.POWER, 12), false);
        scene.world().modifyBlock(dividerPos, s -> s.setValue(RedstoneDividerBlock.POWER, 12), false);
        scene.effects().indicateRedstone(leverPos);
        scene.world().modifyKineticSpeed(util.select().fromTo(1, 1, 3, 2, 1, 3), f -> 0f);
        scene.overlay().showText(60)
                .colored(PonderPalette.RED)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(dividerPos))
                .text("At full signal strength, the output is brought to a complete stop");
        scene.idle(70);
        scene.markAsFinished();
        //endregion
    }

    public static void boreBlock(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("bore_block", "Mining through blocks with the Bore Block");
        scene.configureBasePlate(0, 0, 5);

        BlockPos borePos = util.grid().at(4, 1, 1);

        //region Setup
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(0, 1, 3, 5, 1, 3), Direction.DOWN);
        scene.world().showSection(util.select().position(5, 0, 2), Direction.UP);
        scene.idle(10);
        ElementLink<WorldSectionElement> bore = scene.world().showIndependentSection(
                util.select().fromTo(4, 1, 1, 4, 1, 2), Direction.DOWN);
        scene.idle(15);
        //endregion

        //region Intro
        scene.overlay().showText(60)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(borePos))
                .text("The Bore Block destroys any block in its path when moved by a contraption");
        scene.idle(70);
        //endregion

        //region Forward
        scene.world().showSection(util.select().position(1, 1, 1), Direction.EAST);
        scene.overlay().showText(55)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(util.grid().at(1, 1, 1)))
                .text("Blocks in its path are broken as the bore advances");
        scene.idle(65);

        scene.world().setKineticSpeed(util.select().fromTo(0, 1, 3, 5, 1, 3), 32);
        scene.world().setKineticSpeed(util.select().position(5, 0, 2), 16);

        scene.world().moveSection(bore, util.vector().of(-4, 0, 0), 80);
        scene.idle(60);
        scene.world().replaceBlocks(util.select().position(1, 1, 1), Blocks.AIR.defaultBlockState(), true);
        scene.idle(20);
        scene.idle(15);
        //endregion

        //region Reverse
        scene.world().modifyKineticSpeed(util.select().fromTo(0, 1, 3, 5, 1, 3), f -> -f);
        scene.world().modifyKineticSpeed(util.select().position(5, 0, 2), f -> -f);
        scene.overlay().showText(60)
                .attachKeyFrame()
                .colored(PonderPalette.GREEN)
                .placeNearTarget()
                .pointAt(util.vector().topOf(borePos))
                .text("Reversing the shaft lets the bore clear any blocks in its path on the return pass");
        scene.world().moveSection(bore, util.vector().of(4, 0, 0), 80);
        scene.idle(5);
        scene.world().showSection(util.select().position(3, 1, 1), Direction.WEST);
        scene.idle(40);
        scene.world().replaceBlocks(util.select().position(3, 1, 1), Blocks.AIR.defaultBlockState(), true);
        scene.idle(20);
        scene.idle(15);
        scene.markAsFinished();
        //endregion
    }
}
