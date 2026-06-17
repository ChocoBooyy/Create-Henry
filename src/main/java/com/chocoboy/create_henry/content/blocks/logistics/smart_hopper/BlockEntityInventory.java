package com.chocoboy.create_henry.content.blocks.logistics.smart_hopper;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerContainer;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public abstract class BlockEntityInventory<BE extends SyncedBlockEntity> extends ItemStackHandlerContainer {
    public BE blockEntity;
    public boolean extractionAllowed;
    public boolean insertionAllowed;
    public boolean stackNonStackables;
    public int stackSize;

    private Consumer<Integer> updateCallback;

    public BlockEntityInventory(int slots, BE blockEntity, int stackSize, boolean stackNonStackables) {
        super(slots);
        this.blockEntity = blockEntity;
        this.stackNonStackables = stackNonStackables;
        this.insertionAllowed = true;
        this.extractionAllowed = true;
        this.stackSize = stackSize;
    }

    public void load(CompoundTag tag) {
        deserializeNBT(tag);
    }

    public void save(CompoundTag tag) {
        tag.merge(serializeNBT());
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        if (!insertionAllowed) return 0;
        return super.insert(resource, maxAmount, transaction);
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        if (!extractionAllowed) return 0;
        if (stackNonStackables) {
            try (Transaction nested = transaction.openNested()) {
                long extracted = super.extract(resource, maxAmount, nested);
                nested.abort();
                if (extracted != 0 && resource.getItem().getMaxStackSize() < extracted) {
                    maxAmount = resource.getItem().getMaxStackSize();
                }
            }
        }
        return super.extract(resource, maxAmount, transaction);
    }

    @Override
    public int getSlotLimit(int slot) {
        return Math.min(stackNonStackables ? 64 : 99, stackSize);
    }

    @Override
    public boolean isItemValid(int slot, ItemVariant resource, int count) {
        return true;
    }

    @SuppressWarnings("unused")
    public void whenContentsChange(Consumer<Integer> updateCallback) {
        this.updateCallback = updateCallback;
    }

    @Override
    protected void onContentsChanged(int slot) {
        if (updateCallback != null) updateCallback.accept(slot);
        blockEntity.notifyUpdate();
    }

    public boolean isEmpty() {
        return empty();
    }
}
