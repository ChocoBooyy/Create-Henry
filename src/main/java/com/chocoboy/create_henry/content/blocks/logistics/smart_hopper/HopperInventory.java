package com.chocoboy.create_henry.content.blocks.logistics.smart_hopper;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;

public class HopperInventory extends BlockEntityInventory<SmartHopperBlockEntity> {

    public HopperInventory(int slots, SmartHopperBlockEntity blockEntity) {
        super(slots, blockEntity, 64, true);
    }

    @Override
    public void setChanged() {
        var state = blockEntity.getBlockState();
        var flag = state.hasProperty(SmartHopperBlock.POWERED) && !state.getValue(SmartHopperBlock.POWERED);
        extractionAllowed = flag;
        insertionAllowed = flag;
    }

    @Override
    public boolean isItemValid(int slot, ItemVariant resource, int count) {
        if (blockEntity.filtering.test(resource.toStack())) return super.isItemValid(slot, resource, count);
        return false;
    }
}
