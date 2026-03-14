package com.chocoboy.create_henry.content.blocks.kinetics.kinetic_motor;

import com.chocoboy.create_henry.content.blocks.kinetics.DirectionalMotorBlock;
import com.chocoboy.create_henry.registry.HenryBlockEntityTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class KineticMotorBlock extends DirectionalMotorBlock<KineticMotorBlockEntity> {

    public KineticMotorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public Class<KineticMotorBlockEntity> getBlockEntityClass() {
        return KineticMotorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends KineticMotorBlockEntity> getBlockEntityType() {
        return HenryBlockEntityTypes.KINETIC_MOTOR.get();
    }
}
