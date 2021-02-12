package com.greenbudgie.genetica.item;

import com.greenbudgie.genetica.Genetica;
import com.greenbudgie.genetica.engineering.IEResult;
import com.greenbudgie.genetica.engineering.Substance;
import com.greenbudgie.genetica.engineering.handler.IEHandler;
import com.greenbudgie.genetica.engineering.handler.IEHandlers;
import com.greenbudgie.genetica.util.InventoryUtils;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

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
            InventoryUtils.replaceOrDropHandItem(fromItem, new ItemStack(GeneticaItems.EMPTY_INJECTOR), player, hand);
        } else if(result == IEResult.ITEM_BREAK) {
            InventoryUtils.replaceOrDropHandItem(fromItem, new ItemStack(GeneticaItems.BROKEN_INJECTOR), player, hand);
        }
        return result;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack currentItem = user.getStackInHand(hand);
        IEResult result = makeInjection(currentItem, entity, user, hand);
        return result == IEResult.FAIL ? ActionResult.PASS : ActionResult.SUCCESS;
    }

}
