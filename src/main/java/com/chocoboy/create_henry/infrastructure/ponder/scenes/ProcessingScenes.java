package com.chocoboy.create_henry.infrastructure.ponder.scenes;

import com.chocoboy.create_henry.content.blocks.kinetics.golden_mixer.GoldenMixerBlockEntity;
import com.chocoboy.create_henry.content.blocks.kinetics.multimeter.MultiMeterBlockEntity;
import com.google.common.collect.ImmutableList;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.data.IntAttached;
import net.createmod.catnip.nbt.NBTHelper;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.ParticleEmitter;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.phys.Vec3;

public class ProcessingScenes {

    public static void goldenMixing(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("golden_mixer", "Processing Items with the Golden Mixer");
        scene.configureBasePlate(0, 0, 5);
        scene.world().setBlock(util.grid().at(1, 1, 2), AllBlocks.ANDESITE_CASING.getDefaultState(), false);
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(3, 1, 4, 3, 1, 2), Direction.EAST);
        scene.idle(5);
        scene.world().showSection(util.select().position(1, 1, 2), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(1, 2, 2), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(1, 4, 2), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(1, 1, 3), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(0, 1, 3, 0, 4, 3), Direction.WEST);
        scene.idle(5);
        scene.world().showSection(util.select().position(2, 1, 4), Direction.SOUTH);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(3, 1, 1, 1, 1, 1), Direction.DOWN);
        scene.idle(20);

        BlockPos basin = util.grid().at(1, 2, 2);
        BlockPos mixerPos = util.grid().at(1, 4, 2);
        Vec3 basinSide = util.vector().blockSurface(basin, Direction.WEST);

        scene.overlay().showText(70)
                .pointAt(basinSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("The Golden Mixer is an upgraded Mechanical Mixer that operates 2.5x times faster, processing recipes much faster");
        scene.idle(70);

        ItemStack blue = new ItemStack(Items.BLUE_DYE);
        ItemStack red = new ItemStack(Items.RED_DYE);
        ItemStack purple = new ItemStack(Items.PURPLE_DYE);

        scene.overlay().showControls(util.vector().topOf(basin), Pointing.LEFT, 30).withItem(blue);
        scene.overlay().showControls(util.vector().topOf(basin), Pointing.RIGHT, 30).withItem(red);
        scene.idle(30);
        scene.world().modifyBlockEntity(mixerPos, GoldenMixerBlockEntity.class, pte -> pte.startProcessingBasin());
        scene.world().createItemOnBeltLike(basin, Direction.UP, red);
        scene.world().createItemOnBeltLike(basin, Direction.UP, blue);
        scene.idle(80);
        scene.world().modifyBlockEntityNBT(util.select().position(basin), BasinBlockEntity.class, nbt -> {
            nbt.put("VisualizedItems",
                    NBTHelper.writeCompoundList(ImmutableList.of(IntAttached.with(1, purple)), ia -> ia.getValue()
                            .serializeNBT()));
        });
        scene.idle(4);
        scene.world().createItemOnBelt(util.grid().at(1, 1, 1), Direction.UP, purple);
        scene.idle(30);

        scene.overlay().showText(80)
                .pointAt(basinSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("It handles Shapeless Crafting and Mixing recipes, just like the Mechanical Mixer");
        scene.idle(80);

        ItemStack waterBottle = new ItemStack(Items.POTION);
        ItemStack netherWart = new ItemStack(Items.NETHER_WART);

        scene.overlay().showControls(util.vector().topOf(basin), Pointing.LEFT, 40).withItem(waterBottle);
        scene.overlay().showControls(util.vector().topOf(basin), Pointing.RIGHT, 40).withItem(netherWart);
        scene.idle(40);

        scene.overlay().showText(80)
                .pointAt(basinSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("It can also brew Potions");
        scene.idle(80);

        scene.rotateCameraY(-30);
        scene.idle(10);
        scene.world().setBlock(util.grid().at(1, 1, 2), AllBlocks.BLAZE_BURNER.getDefaultState()
                .setValue(BlazeBurnerBlock.HEAT_LEVEL, HeatLevel.KINDLED), true);
        scene.idle(10);

        scene.overlay().showText(70)
                .pointAt(basinSide.subtract(0, 1, 0))
                .placeNearTarget()
                .text("Some recipes require the heat of a Blaze Burner placed below the Basin");
        scene.idle(40);

        scene.rotateCameraY(30);
        scene.idle(60);

        Vec3 filterPos = util.vector().of(1, 2.75f, 2.5f);
        scene.overlay().showFilterSlotInput(filterPos, Direction.WEST, 100);
        scene.overlay().showText(100)
                .pointAt(filterPos)
                .placeNearTarget()
                .attachKeyFrame()
                .text("The filter slot can be used to resolve conflicts when multiple recipes are compatible");
        scene.idle(80);
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

}