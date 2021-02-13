package com.greenbudgie.genetica.block.entity;

import com.greenbudgie.genetica.Genetica;
import com.greenbudgie.genetica.block.GeneticaBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GeneticaBlockEntities {

    public static BlockEntityType<FlaskBlockEntity> FLASK;

    public static void registerAll() {
        FLASK = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Genetica.MODID, "flask"),
                BlockEntityType.Builder.create(FlaskBlockEntity::new, GeneticaBlocks.FLASK).build(null));
    }

}
