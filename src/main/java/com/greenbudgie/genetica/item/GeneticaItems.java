package com.greenbudgie.genetica.item;

import com.greenbudgie.genetica.Genetica;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GeneticaItems {

    public static final EmptyInjectorItem EMPTY_INJECTOR = new EmptyInjectorItem(
            new Item.Settings().maxCount(16).group(Genetica.GENETICA_ITEM_GROUP));
    public static final FilledInjectorItem FILLED_INJECTOR = new FilledInjectorItem(
            new Item.Settings().maxCount(1));
    public static final BrokenInjectorItem BROKEN_INJECTOR = new BrokenInjectorItem(
            new Item.Settings().maxCount(64));

    public static void registerAll() {
        Registry.register(Registry.ITEM, new Identifier(Genetica.MODID, "empty_injector"), EMPTY_INJECTOR);
        Registry.register(Registry.ITEM, new Identifier(Genetica.MODID, "filled_injector"), FILLED_INJECTOR);
        Registry.register(Registry.ITEM, new Identifier(Genetica.MODID, "broken_injector"), BROKEN_INJECTOR);
    }

}
