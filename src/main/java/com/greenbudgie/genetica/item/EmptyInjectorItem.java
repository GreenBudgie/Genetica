package com.greenbudgie.genetica.item;

import com.greenbudgie.genetica.block.entity.SubstanceHolderBlockEntity;
import com.greenbudgie.genetica.engineering.IEResult;
import com.greenbudgie.genetica.engineering.Substance;
import com.greenbudgie.genetica.engineering.handler.IEHandler;
import com.greenbudgie.genetica.engineering.handler.IEHandlers;
import com.greenbudgie.genetica.util.InventoryUtils;
import net.minecraft.block.entity.BlockEntity;
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
            fill(emptyInjector, player, hand, Substance.fromBlood(fromEntity.getType()));
        } else if(result == IEResult.ITEM_BREAK) {
            InventoryUtils.replaceOrDropHandItem(emptyInjector, new ItemStack(GeneticaItems.BROKEN_INJECTOR), player, hand);
        }
        return result;
    }

    /**
     * Fill the injector with the given substance.
     * Changes the type of the injector to {@link FilledInjectorItem}
     * @param stack Injector to fill
     * @param player Player who fills an item
     * @param hand The hand in which item has been used
     * @param substance Substance to fill with
     */
    public void fill(ItemStack stack, PlayerEntity player, Hand hand, Substance substance) {
        ItemStack filledInjector = new ItemStack(GeneticaItems.FILLED_INJECTOR);
        GeneticaItems.FILLED_INJECTOR.setSubstance(filledInjector, substance);
        InventoryUtils.replaceOrDropHandItem(stack, filledInjector, player, hand);
    }

    /**
     * Fills the injector with substance from the block holder.
     * Empties the block entity.
     * If holder is empty nothing happens.
     * @param stack Injector to fill
     * @param player Player who used an item
     * @param hand The hand in which item has been used
     * @param holder The substance holder to empty
     * @return Whether the transfer has been completed
     */
    public boolean transferSubstanceFromBlockHolder(ItemStack stack, PlayerEntity player, Hand hand,
                                                 SubstanceHolderBlockEntity holder) {
        Substance substance = holder.getSubstance();
        if(substance != null) {
            fill(stack, player, hand, substance);
            holder.removeSubstance();
            return true;
        }
        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockEntity blockEntity = context.getWorld().getBlockEntity((context.getBlockPos()));
        if(blockEntity instanceof SubstanceHolderBlockEntity) {
            SubstanceHolderBlockEntity holder = (SubstanceHolderBlockEntity) blockEntity;
            boolean complete = transferSubstanceFromBlockHolder(
                    context.getStack(), context.getPlayer(), context.getHand(), holder);
            if(complete) return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack currentItem = user.getStackInHand(hand);
        IEResult result = extractBlood(currentItem, entity, user, hand);
        return result == IEResult.FAIL ? ActionResult.PASS : ActionResult.SUCCESS;
    }

}
