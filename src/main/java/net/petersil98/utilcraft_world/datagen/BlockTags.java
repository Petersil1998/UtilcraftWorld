package net.petersil98.utilcraft_world.datagen;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.petersil98.utilcraft_world.UtilcraftWorld;

public class BlockTags extends BlockTagsProvider {

    public BlockTags(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, UtilcraftWorld.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
    }
}
