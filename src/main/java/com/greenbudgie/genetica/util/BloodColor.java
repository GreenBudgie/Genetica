package com.greenbudgie.genetica.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class BloodColor {

    private static final Map<EntityType<?>, Color> colors = new HashMap<>();
    private static final Color defaultColor = new Color(143, 12, 41);

    static {
        colors.put(EntityType.CREEPER, new Color(73, 182, 53));
    }

    /**
     * Gets the blood color of the specified entity type
     * @param entityType The entity type
     * @return Blood color of an entity, or {@link #defaultColor} if not declared
     */
    public static Color getColor(@NotNull EntityType<?> entityType) {
        Objects.requireNonNull(entityType);
        return colors.getOrDefault(entityType, defaultColor);
    }

}
