package com.greenbudgie.genetica.block;

import com.greenbudgie.genetica.Genetica;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

public class GeneticaBlocks {

    public static final FlaskBlock FLASK = new FlaskBlock(
            AbstractBlock.Settings.of(Material.GLASS).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque()
                    .allowsSpawning(GeneticaBlocks::never).solidBlock(GeneticaBlocks::never)
                    .suffocates(GeneticaBlocks::never).blockVision(GeneticaBlocks::never));

    private static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) {
        return false;
    }

    private static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    public static void registerAll() {
        Registry.register(Registry.BLOCK, new Identifier(Genetica.MODID, "flask"), FLASK);
        Registry.register(Registry.ITEM, new Identifier(Genetica.MODID, "flask"),
                new BlockItem(FLASK, new FabricItemSettings().group(Genetica.GENETICA_ITEM_GROUP)));
    }


}
