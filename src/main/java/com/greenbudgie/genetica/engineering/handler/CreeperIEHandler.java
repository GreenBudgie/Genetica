package com.greenbudgie.genetica.engineering.handler;

import com.greenbudgie.genetica.engineering.Substance;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;

public class CreeperIEHandler<T> extends IEHandler<CreeperEntity> {

    @Override
    public EntityType<CreeperEntity> getEntityType() {
        return EntityType.CREEPER;
    }

    @Override
    public void makeInjection(CreeperEntity entity, Substance substance) {
        super.makeInjection(entity, substance);
    }
}
