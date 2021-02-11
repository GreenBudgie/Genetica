package com.greenbudgie.genetica.engineering;

import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A substance that an {@link com.greenbudgie.genetica.item.InjectorItem Injector} may contain.
 */
public class Substance {

    public enum Type {
        BLOOD, UNKNOWN
    }

    /**
     * When the amount of mixins reaches the specified number the substance turns into {@link Type#UNKNOWN}
     */
    public static final int maxMixins = 5;

    public static final String TAG_MIXIN = "mixin";
    public static final String TAG_COLOR = "color";
    public static final String TAG_ENTITY = "entity";
    public static final String TAG_TYPE = "type";

    /**
     * A mixin of the current substance. Mixin may contain another mixin, forming a top-down structure of mixin.
     * Might be null.
     * <p>Note that whenever a new mixin is applied it should stay immutable.
     * Some changes to a mixin may not affect the current substance because the mixin cannot
     * update its parent substance.</p>
     */
    private Substance mixin;

    /**
     * A DNA of an entity that this substance is holding. Might be null.
     */
    private EntityType<?> entityDNAInside;

    /**
     * Type of the current substance. Cannot be null
     */
    private Type type;

    /**
     * Color of the current substance. It calculates by mixing the current color with all the mixins.
     */
    private Color color;

    private Substance() {}

    /**
     * Creates a new substance from an entity blood, providing its color and DNA.
     * @param entity An entity to get the color from
     * @return Instance of substance with entity's blood
     */
    public static Substance fromBlood(EntityType<?> entity) {
        Objects.requireNonNull(entity);
        Substance substance = new Substance();
        substance.entityDNAInside = entity;
        substance.type = Type.BLOOD;
        substance.color = BloodColor.getColor(entity);
        return substance;
    }

    /**
     * Creates a substance from the given tag.
     * <p><b>This method should not be called every tick. May cause lags.
     * Sometimes it's better to get partial information from NBT data using provided tags.</b></p>
     * @param tag NBT
     * @return A substance retrieved from NBT
     */
    public static Substance fromNBT(CompoundTag tag) {
        Objects.requireNonNull(tag);
        Substance substance = new Substance();
        if(tag.contains(TAG_ENTITY)) {
            substance.entityDNAInside = Registry.ENTITY_TYPE.get(Identifier.tryParse(tag.getString(TAG_ENTITY)));
        }
        substance.color = new Color(tag.getInt(TAG_COLOR));
        LogManager.getLogger().info(substance);
        substance.type = Type.valueOf(tag.getString(TAG_TYPE));
        if(tag.contains(TAG_MIXIN)) {
            substance.mixin = fromNBT(tag.getCompound(TAG_MIXIN));
        }
        return substance;
    }

    /**
     * Creates a clone of a given substance, also cloning its mixins recursively
     * @param anotherSubstance Substance to clone
     * @return A clone of a substance with cloned mixins
     */
    public static Substance cloneOf(Substance anotherSubstance) {
        Substance substance = new Substance();
        if(substance.isMixed()) {
            substance.mixin = cloneOf(anotherSubstance.mixin);
        }
        substance.color = anotherSubstance.color;
        substance.entityDNAInside = anotherSubstance.entityDNAInside;
        substance.type = anotherSubstance.type;
        return substance;
    }

    /**
     * Mixes the current substance with the given substance.
     * If substance is already mixed then mixin adds to the bottom of mixins hierarchy.
     * @param mixinSubstance The mixin to add
     */
    public void mixWith(Substance mixinSubstance) {
        if(!acceptsMixins()) return;
        getUnmixedMixinSubstance().mixin = mixinSubstance;
        updateProperties();
    }

    /**
     * Mixes the current substance with all the given substances.
     * See also: {@link #mixWith(Substance) mixWith}
     * @param mixinSubstances The substances to mix the current one with
     */
    public void mixWith(Substance... mixinSubstances) {
        Stream.of(mixinSubstances).forEach(this::mixWith);
    }

    /**
     * Checks whether the substance has one or more mixins
     * @return Whether the substance is mixed
     */
    public boolean isMixed() {
        return mixin != null;
    }

    /**
     * Recalculates the information about the current substance and its mixins. Usually performed after mixing.
     */
    public void updateProperties() {
        if(isMixed()) {
            if(getMixins().size() > maxMixins) {
                convertToUnknown();
                return;
            }
            mixin.updateProperties();
            color = mixColor(color, mixin.color);
        }
    }

    /**
     * Converts the current substance to {@link Type#UNKNOWN}.
     * It erases all of the mixins and makes it impossible to add more.
     * See also: {@link #maxMixins}
     */
    private void convertToUnknown() {
        type = Type.UNKNOWN;
        color = Color.black;
        mixin = null;
    }

    /**
     * Gets whether the current substance can be mixed
     * Condition: {@link #maxMixins}
     * @return Whether the substance can be mixed
     */
    public boolean acceptsMixins() {
        return type != Type.UNKNOWN;
    }

    private Color mixColor(Color a, Color b) {
        double percent = 0.5;
        return new Color((int) (a.getRed() * percent + b.getRed() * (1.0 - percent)),
                (int) (a.getGreen() * percent + b.getGreen() * (1.0 - percent)),
                (int) (a.getBlue() * percent + b.getBlue() * (1.0 - percent)));
    }

    /**
     * Finds the bottom, i.e. unmixed, substance in mixins hierarchy
     * @return Unmixed mixin of the current substance, or itself if no mixins found
     */
    public Substance getUnmixedMixinSubstance() {
        return isMixed() ? this : mixin.getUnmixedMixinSubstance();
    }

    /**
     * Recursively gets all of the mixins in the current substance
     * @return List of mixins, or an empty list if not mixed
     */
    public List<Substance> getMixins() {
        List<Substance> mixins = new ArrayList<>();
        if(!isMixed()) return mixins;
        for(Substance currentSubstance = mixin; currentSubstance.isMixed(); currentSubstance = currentSubstance.mixin) {
            mixins.add(currentSubstance);
        }
        return mixins;
    }

    /**
     * Gets all of the mixins of the current substance, also including itself
     * @return A list of mixins and this substance itself
     */
    public List<Substance> getMixinsAndItself() {
        List<Substance> substances = getMixins();
        substances.add(this);
        return substances;
    }

    /**
     * Checks whether the currents substance or one of its mixins has the specified entity in DNA
     * @param entityType An entity DNA to search for
     * @return Whether the substance contains a given DNA
     */
    public boolean containsDNA(EntityType<?> entityType) {
        return deepSearch(substance -> substance.entityDNAInside == entityType);
    }

    /**
     * Checks whether the current substance or any of its mixins satisfies the expression
     * @param expression An expression
     * @return Whether the substance satisfies the expression
     */
    public boolean deepSearch(Predicate<Substance> expression) {
        return isMixed()
                ? (expression.test(this) || mixin.deepSearch(expression))
                : expression.test(this);
    }

    @Nullable
    public EntityType<?> getEntityDNAInside() {
        return entityDNAInside;
    }

    @NotNull
    public Type getType() {
        return type;
    }

    /**
     * The color of the current substance. It calculates by mixing the current color with all the mixins.
     * @return Substance color
     */
    @NotNull
    public Color getColor() {
        return color;
    }

    /**
     * The color of the current substance. It calculates by mixing the current color with all the mixins.
     * @return Substance color, converted to integer for NBT
     */
    public int getIntColor() {
        return color.getRGB();
    }

    public CompoundTag generateTag() {
        CompoundTag tag = new CompoundTag();
        if(entityDNAInside != null) {
            tag.putString(TAG_ENTITY, Registry.ENTITY_TYPE.getId(entityDNAInside).toString());
        }
        tag.putInt(TAG_COLOR, getIntColor());
        tag.putString(TAG_TYPE, getType().name());
        if(isMixed()) {
            tag.put(TAG_MIXIN, mixin.generateTag());
        }
        return tag;
    }

}
