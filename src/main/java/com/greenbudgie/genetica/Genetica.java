package com.greenbudgie.genetica;

import com.greenbudgie.genetica.block.GeneticaBlocks;
import com.greenbudgie.genetica.item.GeneticaItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Genetica implements ModInitializer {

    public static final String MODID = "genetica";

    public static final ItemGroup GENETICA_ITEM_GROUP = FabricItemGroupBuilder.create(
            new Identifier(MODID, "genetica")).icon(() -> new ItemStack(GeneticaItems.INJECTOR)).build();

    @Override
    public void onInitialize() {
        GeneticaItems.registerAll();
        GeneticaBlocks.registerAll();
    }
}
