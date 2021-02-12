package com.greenbudgie.genetica.item;

import com.greenbudgie.genetica.engineering.IEResult;
import com.greenbudgie.genetica.engineering.Substance;
import com.greenbudgie.genetica.engineering.handler.IEHandler;
import com.greenbudgie.genetica.engineering.handler.IEHandlers;
import com.greenbudgie.genetica.util.InventoryUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class EmptyInjectorItem extends Item {

    public EmptyInjectorItem(Settings settings) {
        super(settings);
    }

    /**
     * Extracts a blood from the given entity
     * @param emptyInjector An item to extract blood to
     * @param fromEntity Extract from this entity
     * @param player Player who extracts blood
     * @return Whether the blood has been extracted successfully
     */
    private IEResult extractBlood(ItemStack emptyInjector, LivingEntity fromEntity, PlayerEntity player, Hand hand) {
        IEHandler<?> handler = IEHandlers.getHandler(fromEntity.getType());
        IEResult result = handler.getIEResult();
        if(result == IEResult.SUCCESS) {
            ItemStack filledInjector = new ItemStack(GeneticaItems.FILLED_INJECTOR);
            GeneticaItems.FILLED_INJECTOR.setSubstance(filledInjector, Substance.fromBlood(fromEntity.getType()));
            InventoryUtils.replaceOrDropHandItem(emptyInjector, filledInjector, player, hand);
        } else if(result == IEResult.ITEM_BREAK) {
            InventoryUtils.replaceOrDropHandItem(emptyInjector, new ItemStack(GeneticaItems.BROKEN_INJECTOR), player, hand);
        }
        return result;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack currentItem = user.getStackInHand(hand);
        IEResult result = extractBlood(currentItem, entity, user, hand);
        return result == IEResult.FAIL ? ActionResult.PASS : ActionResult.SUCCESS;
    }

}
