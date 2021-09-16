package net.petersil98.utilcraft_world.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.petersil98.utilcraft_world.UtilcraftWorld;
import net.petersil98.utilcraft_world.blocks.UtilcraftWorldBlocks;
import net.petersil98.utilcraft_world.utils.BlockItemUtils;

public class ItemModels extends ItemModelProvider {

    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, UtilcraftWorld.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerBlockItem(UtilcraftWorldBlocks.GRAVE.get());
    }

    private void registerHandheld(Item item) {
        ResourceLocation location = new ResourceLocation(BlockItemUtils.namespace(item), ITEM_FOLDER +"/"+BlockItemUtils.name(item));
        singleTexture(BlockItemUtils.name(item), mcLoc(ITEM_FOLDER+"/handheld"), "layer0", location);
    }

    private void registerGeneratedItem(Block block, Block realItem) {
        ResourceLocation location = new ResourceLocation(BlockItemUtils.namespace(realItem), ITEM_FOLDER +"/"+BlockItemUtils.name(realItem));
        singleTexture(BlockItemUtils.name(block), mcLoc(ITEM_FOLDER+"/generated"), "layer0", location);
    }

    private void registerGeneratedBlock(Block block, Block realItem) {
        ResourceLocation location = new ResourceLocation(BlockItemUtils.namespace(realItem), BLOCK_FOLDER +"/"+BlockItemUtils.name(realItem));
        singleTexture(BlockItemUtils.name(block), mcLoc(ITEM_FOLDER+"/generated"), "layer0", location);
    }

    private void registerBlockItem(Block block) {
        registerBlockItem(block, "");
    }

    private void registerBlockItem(Block block, String key) {
        ResourceLocation location = new ResourceLocation(BlockItemUtils.namespace(block), BLOCK_FOLDER +"/"+BlockItemUtils.name(block)+key);
        withExistingParent(BlockItemUtils.name(block), location);
    }
}
