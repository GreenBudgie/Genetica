package com.greenbudgie.genetica.engineering;

import net.minecraft.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class BloodColor {

    private static final Map<EntityType<?>, Color> colors = new HashMap<>();
    private static final Color defaultColor = new Color(143, 12, 41);

    static {
        setColor(new Color(73, 182, 53), EntityType.CREEPER);
        setColor(new Color(102, 60, 0),
                EntityType.ZOMBIE, EntityType.DROWNED, EntityType.ZOMBIE_HORSE,
                EntityType.ZOMBIE_VILLAGER, EntityType.HUSK);
        setColor(new Color(84,35,60),
                EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.SHULKER,
                EntityType.ENDER_DRAGON, EntityType.PHANTOM);
        setColor(new Color(213,195,195), EntityType.GHAST);
        setColor(new Color(213,114,0), EntityType.BLAZE);
        setColor(new Color(118,36,4), EntityType.MAGMA_CUBE);
        setColor(new Color(188,119,93), EntityType.MOOSHROOM);
        setColor(new Color(79, 155, 60), EntityType.SLIME);
    }

    private static void setColor(Color color, EntityType<?>... entityTypes) {
        for(EntityType<?> entityType : entityTypes) {
            colors.put(entityType, color);
        }
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
