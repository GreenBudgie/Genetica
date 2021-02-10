package com.greenbudgie.genetica.item;

import com.greenbudgie.genetica.util.BloodColor;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.awt.*;

public class InjectorItem extends Item {

    private static final String TAG_BLOOD = "blood";

    public InjectorItem(Settings settings) {
        super(settings);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                tintIndex == 0 ? getBloodColorInside(stack) : -1, this);
    }

    private boolean hasBloodInside(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains(TAG_BLOOD);
    }

    private int getBloodColorInside(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if(tag.contains(TAG_BLOOD)) {
            return tag.getInt(TAG_BLOOD);
        }
        return -1;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack currentItem = user.getStackInHand(hand);
        Color bloodColor = BloodColor.getColor(entity.getType());
        currentItem.getOrCreateTag().putInt(TAG_BLOOD, bloodColor.getRGB());
        return ActionResult.SUCCESS;
    }

}
