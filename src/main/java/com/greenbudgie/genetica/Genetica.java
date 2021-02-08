package com.greenbudgie.genetica;

import com.greenbudgie.genetica.item.GeneticaItems;
import net.fabricmc.api.ModInitializer;

public class Genetica implements ModInitializer {

    public static final String MODID = "genetica";

    @Override
    public void onInitialize() {
        GeneticaItems.registerAll();
    }
}
