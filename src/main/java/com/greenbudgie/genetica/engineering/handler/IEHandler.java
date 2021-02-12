package com.greenbudgie.genetica.engineering.handler;

import com.greenbudgie.genetica.engineering.IEResult;
import com.greenbudgie.genetica.engineering.Substance;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

/**
 * Handles all the Injection/Extraction processes of an entity
 * @param <T> An entity that handles an I/E
 */
public abstract class IEHandler<T extends LivingEntity> {

    public abstract EntityType<T> getEntityType();

    /**
     * Represents a possibility to make an injection or extraction for/from an entity.<br>
     * <p>Sometimes it's not possible due to no-blood entity or a solid entity (Snow Golem, Skeleton, e.t.c).</p>
     * @return An injection/extraction result for the entity
     */
    public IEResult getIEResult() { return IEResult.SUCCESS; }

    /**
     * Calls when an entity receives an injection.
     * <p><b>Note: this method will only be called if {@link #getIEResult() IEResult}
     * is a {@link IEResult#SUCCESS SUCCESS}</b></p>
     * @param entity An entity
     * @param substance An injected substance
     */
    public void makeInjection(T entity, Substance substance) {}

    /**
     * Calls when someone extracts blood from an entity
     * <p><b>Note: this method will only be called if {@link #getIEResult() IEResult}
     * is a {@link IEResult#SUCCESS SUCCESS}</b></p>
     * @param entity An entity
     */
    public void onExtract(T entity) {}

}
