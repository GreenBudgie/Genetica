package com.greenbudgie.genetica.item;

import com.greenbudgie.genetica.engineering.Substance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

public abstract class SubstanceHolderItem extends Item {

    protected static final String TAG_SUBSTANCE = "substance";

    public SubstanceHolderItem(Settings settings) {
        super(settings);
    }

    /**
     * Checks whether the injector has any substance inside
     * @param stack An item to chech
     * @return Whether the injector has a substance inside
     */
    public boolean hasSubstance(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains(TAG_SUBSTANCE);
    }

    /**
     * Sets the substance stored inside of an item
     * <p><b>This method overrides any substance that have been stored inside of an item before!</b></p>
     * To mix the given substance with the existing one use {@link #mixSubstance(ItemStack, Substance)}
     * @param stack An item to add the substance in
     * @param substance A substance to add
     */
    public void setSubstance(ItemStack stack, Substance substance) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.put(TAG_SUBSTANCE, substance.generateTag());
    }

    /**
     * Mixes the given substance with the one that already inside of an item.
     * <p><b>Make sure to call this method only if an item already has a substance.
     * Otherwise use {@link #setSubstance(ItemStack, Substance)}</b></p>
     * @param stack Stack to use
     * @param mixin A mixin to add to the existing substance
     * @throws RuntimeException If there is no substance to mix with
     */
    public void mixSubstance(ItemStack stack, Substance mixin) {
        if(!hasSubstance(stack)) throw new RuntimeException("Cannot mix a substance with no substance");
        Substance substanceInside = getSubstance(stack);
        substanceInside.mixWith(mixin);
        setSubstance(stack, substanceInside);
    }

    /**
     * Removes any substance that is inside of an item.
     * @param stack Stack to use
     * @throws RuntimeException If there is not substance to remove
     */
    public void removeSubstance(ItemStack stack) {
        if(!hasSubstance(stack)) throw new RuntimeException("There is no substance to remove");
        stack.removeSubTag(TAG_SUBSTANCE);
    }

    /**
     * Gets the substance stored inside of an item.
     * <p><b>Don't use this method to render/update an item every tick!
     * It may cause lags. To consistently get partial information use other methods listed below.
     * See also: {@link Substance#fromNBT(CompoundTag)}</b></p>
     * @param stack An item to get substance from
     * @return A substance inside of an item, or null if empty
     */
    @Nullable
    public Substance getSubstance(ItemStack stack) {
        if(!hasSubstance(stack)) return null;
        return Substance.fromNBT(stack.getSubTag(TAG_SUBSTANCE));
    }

    /**
     * Gets the sub-tag to provide all the inner partial information about the substance.
     * Use this method to get partial information rather than {@link #getSubstance(ItemStack)}
     * @param stack An item to get tag from
     * @return Substance tag, or null if empty
     */
    @Nullable
    public CompoundTag getSubstanceTag(ItemStack stack) {
        return stack.getSubTag(TAG_SUBSTANCE);
    }

    /**
     * Gets the color of a substance inside of an item
     * @param stack An item to get color from
     * @return A substance color, or -1 if empty
     */
    public int getSubstanceColor(ItemStack stack) {
        return hasSubstance(stack) ? getSubstanceTag(stack).getInt(Substance.TAG_COLOR) : -1;
    }

}
