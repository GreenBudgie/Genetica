package com.greenbudgie.genetica.block;

import com.greenbudgie.genetica.block.entity.FlaskBlockEntity;
import com.greenbudgie.genetica.engineering.Substance;
import com.greenbudgie.genetica.item.GeneticaItems;
import javafx.beans.property.ObjectProperty;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FlaskBlock extends AbstractGlassBlock implements BlockEntityProvider {

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

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new FlaskBlockEntity();
    }

}
