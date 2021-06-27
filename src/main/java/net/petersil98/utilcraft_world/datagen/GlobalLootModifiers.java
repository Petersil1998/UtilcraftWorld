package net.petersil98.utilcraft_world.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.petersil98.utilcraft_world.UtilcraftWorld;

public class GlobalLootModifiers extends GlobalLootModifierProvider {

    public GlobalLootModifiers(DataGenerator generator) {
        super(generator, UtilcraftWorld.MOD_ID);
    }

    @Override
    protected void start() {

    }
}
