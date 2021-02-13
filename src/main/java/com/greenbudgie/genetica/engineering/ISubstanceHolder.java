package com.greenbudgie.genetica.engineering;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an item, or block, or any other thing that can store a {@link Substance} <b>by external component</b>.
 * @param <T> A substance holder
 */
public interface ISubstanceHolder<T> {

    /**
     * Checks whether a holder has any substance inside
     * @param holder An holder to check
     * @return Whether a holder has a substance inside
     */
    boolean hasSubstance(T holder);

    /**
     * Sets the substance stored inside of a holder
     * <p><b>This method overrides any substance that have been stored inside of a holder before!</b></p>
     * To mix the given substance with the existing one use {@link #mixSubstance(T, Substance)}.
     * <p>You can also use {@link #fill(T, Substance)} to mix or set substance based on {@link #hasSubstance(T)}</p>
     * @param holder A holder to add the substance in
     * @param substance A substance to add
     */
    void setSubstance(T holder, Substance substance);

    /**
     * Mixes the given substance with the one that already inside of a holder.
     * <p><b>Make sure to call this method only if a holder already has a substance.
     * Otherwise use {@link #setSubstance(T, Substance)}</b></p>
     * @param holder Holder to use
     * @param mixin A mixin to add to the existing substance
     * @throws RuntimeException If there is no substance to mix with
     */
    void mixSubstance(T holder, Substance mixin);

    /**
     * Removes any substance that is inside of an item.
     * @param holder Holder to use
     * @throws RuntimeException If there is no substance to remove
     */
    void removeSubstance(T holder);

    /**
     * Gets the substance stored inside of a holder.
     * Any change to returned value will not be applied.
     * <p><b>Don't use this method to render/update a holder every tick!
     * It may cause lags. To consistently get partial information use other methods listed below.
     * See also: {@link Substance#fromNBT(CompoundTag)}</b></p>
     * @param holder An item to get substance from
     * @return A substance inside of a holder, or null if empty
     */
    @Nullable
    Substance getSubstance(T holder);

    /**
     * Gets the sub-tag to provide all the inner partial information about the substance.
     * Use this method to get partial information rather than {@link #getSubstance(T)}
     * @param holder A holder to get substance tag from
     * @return Substance tag, or null if empty
     */
    @Nullable
    CompoundTag getSubstanceTag(T holder);

    /**
     * Gets the color of a substance stored inside of a holder
     * <p><i>Update-optimized</i></p>
     * @param holder A holder to get substance color from
     * @return A substance color, or -1 if empty
     */
    int getSubstanceColor(T holder);

    /**
     * Gets the {@link Substance#getVolume() volume} of a substance stored inside.
     * <p><i>Update-optimized</i></p>
     * @param holder A holder to use
     * @return Volume of a substance, or -1 if empty
     */
    float getSubstanceVolume(T holder);

    /**
     * Mixes the given substance with the stored one, or adds it to the holder without mixing.
     * @param holder The holder to use
     * @param substance The substance to add or mix with existing
     */
    default void fill(T holder, Substance substance) {
        if(hasSubstance(holder)) {
            mixSubstance(holder, substance);
        } else {
            setSubstance(holder, substance);
        }
    }

}
