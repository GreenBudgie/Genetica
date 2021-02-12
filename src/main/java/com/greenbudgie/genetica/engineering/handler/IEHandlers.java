package com.greenbudgie.genetica.engineering.handler;

import com.google.common.collect.Sets;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;

import java.util.HashSet;
import java.util.Set;

public class IEHandlers {

    private static final Set<IEHandler<? extends LivingEntity>> handlers;

    public static final DefaultIEHandler<LivingEntity> DEFAULT = new DefaultIEHandler<>();

    public static final CreeperIEHandler<CreeperEntity> CREEPER = new CreeperIEHandler<>();
    public static final SkeletonIEHandler<SkeletonEntity> SKELETON = new SkeletonIEHandler<>();

    static {
        handlers = Sets.<IEHandler<?>>newHashSet(CREEPER, SKELETON);
    }

    /**
     * Gets the IE handler for the specified entity type
     * @param type Entity type
     * @return A bound IE handler, or a default handler if not yet specified
     */
    public static IEHandler<? extends LivingEntity> getHandler(EntityType<?> type) {
        for(IEHandler<? extends LivingEntity> handler : handlers) {
            if(handler.getEntityType() == type) return handler;
        }
        return DEFAULT;
    }

    public static Set<IEHandler<? extends LivingEntity>> getHandlers() {
        return handlers;
    }

}
