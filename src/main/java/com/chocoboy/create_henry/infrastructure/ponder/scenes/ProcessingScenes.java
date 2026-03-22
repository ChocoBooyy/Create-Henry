package com.chocoboy.create_henry.infrastructure.ponder.scenes;

import com.chocoboy.create_henry.content.blocks.kinetics.golden_mixer.GoldenMixerBlockEntity;
import com.google.common.collect.ImmutableList;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.data.IntAttached;
import net.createmod.catnip.nbt.NBTHelper;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class ProcessingScenes {

    public static void golden_mixing(SceneBuilder builder, SceneBuildingUtil util) {
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
                .text("The Golden Mixer is an upgraded Mechanical Mixer that operates at 2.5x the effective speed of its shaft, processing recipes much faster");
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

}