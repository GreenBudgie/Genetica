package com.greenbudgie.genetica.item;

import com.greenbudgie.genetica.Genetica;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GeneticaItems {

    public static final InjectorItem INJECTOR = new InjectorItem(new Item.Settings().maxCount(16).group(ItemGroup.MISC));

    public static void registerAll() {
        Registry.register(Registry.ITEM, new Identifier(Genetica.MODID, "injector"), INJECTOR);
    }

}