package net.petersil98.utilcraft_world.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.petersil98.utilcraft_world.UtilcraftWorld;

public class ItemTags extends ItemTagsProvider {

    public ItemTags(DataGenerator generator, BlockTags blockTags, ExistingFileHelper existingFileHelper) {
        super(generator, blockTags, UtilcraftWorld.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
    }
}
