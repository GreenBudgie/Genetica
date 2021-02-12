package com.greenbudgie.genetica.engineering.handler;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

/**
 * Generalized IEHandler. Handles every entity which has no custom handler.
 * @param <T>
 */
public class DefaultIEHandler<T> extends IEHandler<LivingEntity> {

    @Override
    public EntityType<LivingEntity> getEntityType() {
        return null;
    }

}
