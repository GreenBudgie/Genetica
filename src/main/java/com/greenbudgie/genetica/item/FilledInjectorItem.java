package com.greenbudgie.genetica.item;

import com.greenbudgie.genetica.block.entity.SubstanceHolderBlockEntity;
import com.greenbudgie.genetica.engineering.IEResult;
import com.greenbudgie.genetica.engineering.Substance;
import com.greenbudgie.genetica.engineering.handler.IEHandler;
import com.greenbudgie.genetica.engineering.handler.IEHandlers;
import com.greenbudgie.genetica.util.InventoryUtils;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

/**
 * The main mod item. An injector, aka syringe, provides a player an ability to extract blood from
 * any entity and make an injection of {@link com.greenbudgie.genetica.engineering.Substance Substance} to any entity.
 */
public class FilledInjectorItem extends SubstanceHolderItem {

    public FilledInjectorItem(Settings settings) {
        super(settings);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex == 0 ? getSubstanceColor(stack) : -1, this);
    }

    private IEResult makeInjection(ItemStack fromItem, LivingEntity toEntity, PlayerEntity player, Hand hand) {
        IEHandler<?> handler = IEHandlers.getHandler(toEntity.getType());
        IEResult result = handler.getIEResult();
        if(result == IEResult.SUCCESS) {
            emptyOnUse(fromItem, player, hand);
        } else if(result == IEResult.ITEM_BREAK) {
            breakOnUse(fromItem, player, hand);
        }
        return result;
    }

    /**
     * Makes the injector broken. Also may play sounds TODO
     * @param stack The injector stack to break
     * @param player The player who used an item
     * @param hand Hand in which the item has been used
     */
    public void breakOnUse(ItemStack stack, PlayerEntity player, Hand hand) {
        InventoryUtils.replaceOrDropHandItem(stack, new ItemStack(GeneticaItems.BROKEN_INJECTOR), player, hand);
    }

    /**
     * Makes the injector empty. This not just remove the substance from the injector but also
     * converts the item to another type of {@link EmptyInjectorItem}.
     * Also may play sounds TODO
     * @param stack The injector stack to empty
     * @param player The player who used an item
     * @param hand Hand in which the item has been used
     */
    public void emptyOnUse(ItemStack stack, PlayerEntity player, Hand hand) {
        InventoryUtils.replaceOrDropHandItem(stack, new ItemStack(GeneticaItems.EMPTY_INJECTOR), player, hand);
    }

    /**
     * Transfers (sets or mixes) the substance to a block that can hold it.
     * Empties the injector.
     * @param stack The injector
     * @param player The player who used an item
     * @param hand Hand in which the item has been used
     * @param holder A holder to the substance transfer to
     * @return Whether the transfer has been completed
     */
    public boolean transferSubstanceToBlockHolder(ItemStack stack, PlayerEntity player, Hand hand,
                                               SubstanceHolderBlockEntity holder) {
        Substance substance = getSubstance(stack);
        if(substance != null) {
            holder.fill(holder, substance);
            emptyOnUse(stack, player, hand);
            return true;
        }
        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockEntity blockEntity = context.getWorld().getBlockEntity((context.getBlockPos()));
        if(blockEntity instanceof SubstanceHolderBlockEntity) {
            SubstanceHolderBlockEntity holder = (SubstanceHolderBlockEntity) blockEntity;
            boolean complete = transferSubstanceToBlockHolder(
                    context.getStack(), context.getPlayer(), context.getHand(), holder);
            if(complete) return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack currentItem = user.getStackInHand(hand);
        IEResult result = makeInjection(currentItem, entity, user, hand);
        return result == IEResult.FAIL ? ActionResult.PASS : ActionResult.SUCCESS;
    }

}
