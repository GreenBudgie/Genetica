package com.greenbudgie.genetica.item;

import com.greenbudgie.genetica.engineering.BloodColor;
import com.greenbudgie.genetica.engineering.Substance;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * The main mod item. An injector, aka syringe, provides a player an ability to extract blood from
 * any entity and make an injection of {@link com.greenbudgie.genetica.engineering.Substance Substance} to any entity.
 */
public class InjectorItem extends SubstanceHolderItem {

    public InjectorItem(Settings settings) {
        super(settings);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex == 0 ? getSubstanceColor(stack) : -1, this);
    }

    private void extractBlood(ItemStack toItem, LivingEntity fromEntity, PlayerEntity player) {

    }

    private void makeInjection(ItemStack fromItem, LivingEntity toEntity, PlayerEntity player) {

    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack currentItem = user.getStackInHand(hand);
        setSubstance(currentItem, Substance.fromBlood(entity.getType()));
        return ActionResult.SUCCESS;
    }

}
