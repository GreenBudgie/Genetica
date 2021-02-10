package com.greenbudgie.genetica.engineering;

import net.minecraft.entity.EntityType;

import java.awt.*;

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
     * A mixin of the current substance. Mixin may contain another mixin, forming a hierarchy of substances.
     */
    private Substance mixin;
    private EntityType<?> entityDNAInside;
    private Type type;
    private Color color;

}
