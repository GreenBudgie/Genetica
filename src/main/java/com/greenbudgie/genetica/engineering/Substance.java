package com.greenbudgie.genetica.engineering;

import net.minecraft.entity.EntityType;

import java.awt.Color;
import java.util.*;
import java.util.stream.IntStream;
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
     * A type of the current substance. Cannot be null
     */
    private Type type;

    /**
     * A color of the current substance. It calculates by mixing the current color with all the mixins.
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
            mixin.updateProperties();
            color = mixColor(color, mixin.color);
        }
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

}
