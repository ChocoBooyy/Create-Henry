package com.chocoboy.create_henry.content.blocks.kinetics.industrial_brake;

import com.chocoboy.create_henry.content.blocks.kinetics.DirectionalMotorBlock;
import com.chocoboy.create_henry.registry.HenryBlockEntityTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class IndustrialBrakeBlock extends DirectionalMotorBlock<IndustrialBrakeBlockEntity> {

    public IndustrialBrakeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public Class<IndustrialBrakeBlockEntity> getBlockEntityClass() {
        return IndustrialBrakeBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends IndustrialBrakeBlockEntity> getBlockEntityType() {
        return HenryBlockEntityTypes.INDUSTRIAL_BRAKE.get();
    }
}
