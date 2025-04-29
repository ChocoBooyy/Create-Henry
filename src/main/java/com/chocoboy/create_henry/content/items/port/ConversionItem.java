package com.chocoboy.create_henry.content.items.port;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Deprecated(forRemoval=true)
public class ConversionItem extends Item {

    public Item converted;

    public ConversionItem(Properties pProperties, ItemLike convertInto) {
        super(pProperties);
        converted = convertInto.asItem();
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        getDefaultInstance();
        asItem();
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return new ItemStack(converted);
    }

    @Override
    public @NotNull Item asItem() {
        return converted;
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        return new ItemStack(converted);
    }
}
