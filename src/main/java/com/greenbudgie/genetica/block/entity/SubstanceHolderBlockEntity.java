package com.greenbudgie.genetica.block.entity;

import com.greenbudgie.genetica.engineering.ISubstanceHolder;
import com.greenbudgie.genetica.engineering.Substance;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a block entity that can hold a substance.
 * You should pass <b>null</b> to interface provided methods or use methods this class provide.
 */
public class SubstanceHolderBlockEntity extends BlockEntity implements ISubstanceHolder<BlockEntity> {

    private Substance substance;

    public SubstanceHolderBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        if(tag.contains(Substance.TAG_SUBSTANCE)) {
            substance = Substance.fromNBT(tag.getCompound(Substance.TAG_SUBSTANCE));
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        if(substance != null) {
            tag.put(Substance.TAG_SUBSTANCE, substance.generateTag());
        }
        return tag;
    }

    public boolean hasSubstance() {
        return substance != null;
    }

    public void setSubstance(Substance substance) {
        this.substance = substance;
        markDirty();
    }

    public void mixSubstance(Substance mixin) {
        if(!hasSubstance()) throw new RuntimeException("Cannot mix substance with no substance");
        substance.mixWith(mixin);
        markDirty();
    }

    public void removeSubstance() {
        substance = null;
        markDirty();
    }

    /**
     * Gets the copy of a substance stored inside.
     * To make changes please use methods form this class rather than from the substance.
     * @return Copy of substance
     */
    public @Nullable Substance getSubstance() {
        return substance == null ? null : Substance.cloneOf(substance);
    }

    public @Nullable CompoundTag getSubstanceTag() {
        return hasSubstance() ? substance.generateTag() : null;
    }

    public int getSubstanceColor() {
        return substance.getIntColor();
    }

    public float getSubstanceVolume() {
        return substance.getVolume();
    }

    @Override
    public boolean hasSubstance(BlockEntity holder) {
        return hasSubstance();
    }

    @Override
    public void setSubstance(BlockEntity holder, Substance substance) {
        setSubstance(substance);
    }

    @Override
    public void mixSubstance(BlockEntity holder, Substance mixin) {
        mixSubstance(mixin);
    }

    @Override
    public void removeSubstance(BlockEntity holder) {
        removeSubstance();
    }

    @Override
    public @Nullable Substance getSubstance(BlockEntity holder) {
        return getSubstance();
    }

    @Override
    public @Nullable CompoundTag getSubstanceTag(BlockEntity holder) {
        return getSubstanceTag();
    }

    @Override
    public int getSubstanceColor(BlockEntity holder) {
        return getSubstanceColor();
    }

    @Override
    public float getSubstanceVolume(BlockEntity holder) {
        return getSubstanceVolume();
    }

}
