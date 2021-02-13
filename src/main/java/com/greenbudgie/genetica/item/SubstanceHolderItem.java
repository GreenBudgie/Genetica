package com.greenbudgie.genetica.item;

import com.greenbudgie.genetica.engineering.ISubstanceHolder;
import com.greenbudgie.genetica.engineering.Substance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

public abstract class SubstanceHolderItem extends Item implements ISubstanceHolder<ItemStack> {

    public SubstanceHolderItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasSubstance(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains(Substance.TAG_SUBSTANCE);
    }

    @Override
    public void setSubstance(ItemStack stack, Substance substance) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.put(Substance.TAG_SUBSTANCE, substance.generateTag());
    }

    @Override
    public void mixSubstance(ItemStack stack, Substance mixin) {
        if(!hasSubstance(stack)) throw new RuntimeException("Cannot mix a substance with no substance");
        Substance substanceInside = getSubstance(stack);
        substanceInside.mixWith(mixin);
        setSubstance(stack, substanceInside);
    }

    @Override
    public void removeSubstance(ItemStack stack) {
        if(!hasSubstance(stack)) throw new RuntimeException("There is no substance to remove");
        stack.removeSubTag(Substance.TAG_SUBSTANCE);
    }

    @Override
    @Nullable
    public Substance getSubstance(ItemStack stack) {
        if(!hasSubstance(stack)) return null;
        return Substance.fromNBT(stack.getSubTag(Substance.TAG_SUBSTANCE));
    }

    @Override
    @Nullable
    public CompoundTag getSubstanceTag(ItemStack stack) {
        return stack.getSubTag(Substance.TAG_SUBSTANCE);
    }

    @Override
    public int getSubstanceColor(ItemStack stack) {
        return hasSubstance(stack) ? getSubstanceTag(stack).getInt(Substance.TAG_COLOR) : -1;
    }

    @Override
    public float getSubstanceVolume(ItemStack stack) {
        return hasSubstance(stack) ? getSubstanceTag(stack).getInt(Substance.TAG_COLOR) : -1;
    }

}
