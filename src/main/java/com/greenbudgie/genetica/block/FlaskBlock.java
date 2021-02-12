package com.greenbudgie.genetica.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class FlaskBlock extends AbstractGlassBlock {

    public FlaskBlock(Settings settings) {
        super(settings);
        BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getTranslucent());
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape container = Block.createCuboidShape(4, 0, 4, 12, 8, 12);
        VoxelShape tube = Block.createCuboidShape(7, 8, 7, 9, 14, 9);
        VoxelShape cap = Block.createCuboidShape(6, 14, 6, 10, 15, 10);
        return VoxelShapes.union(container, tube, cap);
    }

}
