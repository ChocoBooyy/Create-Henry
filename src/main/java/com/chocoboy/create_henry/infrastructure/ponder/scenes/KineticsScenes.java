package com.chocoboy.create_henry.infrastructure.ponder.scenes;

import com.chocoboy.create_henry.registry.HenryBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.gauge.GaugeBlock;
import com.chocoboy.create_henry.content.blocks.kinetics.multimeter.MultiMeterBlockEntity;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
}
