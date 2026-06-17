package com.chocoboy.create_henry.content.blocks.logistics.roll_table;

import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class RollTableItemHandler extends SingleStackStorage {

    private final RollTableBlockEntity be;
    private final Direction side;

    public RollTableItemHandler(RollTableBlockEntity be, Direction side) {
        this.be = be;
        this.side = side;
    }

    @Override
    protected ItemStack getStack() {
        return be.getHeldItemStack();
    }

    @Override
    protected void setStack(ItemStack stack) {
        if (stack.isEmpty()) {
            be.heldItem = null;
        } else if (be.heldItem == null) {
            TransportedItemStack heldItem = new TransportedItemStack(stack);
            heldItem.prevBeltPosition = 0;
            be.setHeldItem(heldItem, side.getOpposite());
        } else {
            be.heldItem.stack = stack;
        }
    }

    @Override
    protected boolean canInsert(ItemVariant resource) {
        return be.getHeldItemStack().isEmpty();
    }

    @Override
    protected int getCapacity(ItemVariant variant) {
        return Math.min(64, variant.getItem().getMaxStackSize());
    }

    @Override
    protected void onFinalCommit() {
        be.notifyUpdate();
    }
}
