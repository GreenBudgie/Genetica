package com.greenbudgie.genetica.item;

import com.greenbudgie.genetica.engineering.BloodColor;
import com.greenbudgie.genetica.engineering.handler.IEHandler;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.awt.*;

/**
 * The main mod item. An injector, aka syringe, provides a player an ability to extract blood from
 * any entity and make an injection of {@link com.greenbudgie.genetica.engineering.Substance Substance} to any entity.
 */
public class InjectorItem extends Item {

    private static final String TAG_BLOOD_COLOR = "blood";
    private static final String TAG_ENTITY_TYPE = "entity";

    public InjectorItem(Settings settings) {
        super(settings);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex == 0 ? getBloodColorInside(stack) : -1, this);
    }

    private boolean hasBloodInside(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains(TAG_BLOOD_COLOR);
    }

    private int getBloodColorInside(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if(tag.contains(TAG_BLOOD_COLOR)) {
            return tag.getInt(TAG_BLOOD_COLOR);
        }
        return -1;
    }

    private void extractBlood(ItemStack toItem, LivingEntity fromEntity, PlayerEntity player) {

    }

    private void makeInjection(ItemStack fromItem, LivingEntity toEntity, PlayerEntity player) {

    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack currentItem = user.getStackInHand(hand);
        Color bloodColor = BloodColor.getColor(entity.getType());
        currentItem.getOrCreateTag().putInt(TAG_BLOOD_COLOR, bloodColor.getRGB());
        return ActionResult.SUCCESS;
    }

}
