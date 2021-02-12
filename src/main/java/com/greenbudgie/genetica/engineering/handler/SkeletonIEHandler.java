package com.greenbudgie.genetica.engineering.handler;

import com.greenbudgie.genetica.engineering.IEResult;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;

public class SkeletonIEHandler<T> extends IEHandler<SkeletonEntity> {

    @Override
    public EntityType<SkeletonEntity> getEntityType() {
        return EntityType.SKELETON;
    }

    @Override
    public IEResult getIEResult() {
        return IEResult.ITEM_BREAK;
    }
}
