package com.chocoboy.create_henry.content.blocks.kinetics.kinetic_motor;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.chocoboy.create_henry.registry.HenryBlockEntityTypes;

public class KineticMotorBlock extends DirectionalKineticBlock implements IBE<KineticMotorBlockEntity> {
    public KineticMotorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return AllShapes.MOTOR_BLOCK.get((Direction)state.getValue(FACING));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction preferred = this.getPreferredFacing(context);
        return (context.getPlayer() == null || !context.getPlayer().isShiftKeyDown()) && preferred != null ? (BlockState)this.defaultBlockState().setValue(FACING, preferred) : super.getStateForPlacement(context);
    }

    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(FACING);
    }

    public Direction.Axis getRotationAxis(BlockState state) {
        return ((Direction)state.getValue(FACING)).getAxis();
    }

    public boolean hideStressImpact() {
        return true;
    }

    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }

    public Class<KineticMotorBlockEntity> getBlockEntityClass() {
        return KineticMotorBlockEntity.class;
    }

    public BlockEntityType<? extends KineticMotorBlockEntity> getBlockEntityType() {
        return HenryBlockEntityTypes.KINETIC_MOTOR.get();
    }
}
