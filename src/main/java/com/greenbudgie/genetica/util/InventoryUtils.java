package com.greenbudgie.genetica.util;

import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public final class InventoryUtils {

    private InventoryUtils() {}

    /**
     * Replaces the given item in the player's inventory.
     * Drops the item if no space.
     * Does not consume an item if the player is in creative mode.
     * @param consumed The item to replace
     * @param created The item to insert
     * @param player The player
     * @param hand Player's hand in which he holds the item
     */
    public static void replaceOrDropHandItem(ItemStack consumed, ItemStack created, PlayerEntity player, Hand hand) {
        boolean doReplace = consumed.getCount() == 1;
        if(!player.abilities.creativeMode) {
            if(!doReplace) {
                consumed.decrement(1);
            }
        }
        if(doReplace) {
            player.setStackInHand(hand, created);
        } else if(!player.inventory.insertStack(created)) {
            player.dropItem(created, false);
        }
    }

}
