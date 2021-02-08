package com.greenbudgie.genetica.registry;

import com.greenbudgie.genetica.Genetica;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GeneticaItems {

    public static final Item INJECTOR = new Item(new Item.Settings().group(ItemGroup.TOOLS));

    public static void registerAll() {
        Registry.register(Registry.ITEM, new Identifier(Genetica.MODID, "injector"), INJECTOR);
    }

}
