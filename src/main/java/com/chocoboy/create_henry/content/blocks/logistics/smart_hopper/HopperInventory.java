package com.chocoboy.create_henry.content.blocks.logistics.smart_hopper;

import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

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
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (blockEntity.filtering.test(stack)) return super.isItemValid(slot, stack);
        return false;
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        stacks.set(slot, stack);
        int max = stack.getMaxStackSize();
        if (!stack.isEmpty() && stack.getCount() > max) stack.setCount(max);
        setChanged();
        onContentsChanged(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int count) {
        return ContainerHelper.removeItem(stacks, slot, count);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(blockEntity, player);
    }
}
