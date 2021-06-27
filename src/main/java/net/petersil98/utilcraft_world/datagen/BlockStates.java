package net.petersil98.utilcraft_world.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.petersil98.utilcraft_world.blocks.Grave;
import net.petersil98.utilcraft_world.blocks.UtilcraftWorldBlocks;
import net.petersil98.utilcraft_world.utils.BlockItemUtils;
import net.petersil98.utilcraft_world.UtilcraftWorld;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, UtilcraftWorld.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerGrave();
    }

    private void registerGrave() {
        ResourceLocation location = new ResourceLocation(BlockItemUtils.namespace(UtilcraftWorldBlocks.GRAVE), ModelProvider.BLOCK_FOLDER +"/"+BlockItemUtils.name(UtilcraftWorldBlocks.GRAVE));
        getVariantBuilder(UtilcraftWorldBlocks.GRAVE).forAllStates(blockState -> {
            ModelFile modelFile = models().getExistingFile(location);
            switch (blockState.get(Grave.FACING)) {
                case EAST: {
                    return ConfiguredModel.builder().modelFile(modelFile).rotationY(90).build();
                }
                case SOUTH: {
                    return ConfiguredModel.builder().modelFile(modelFile).rotationY(180).build();
                }
                case WEST: {
                    return ConfiguredModel.builder().modelFile(modelFile).rotationY(270).build();
                }
                default: {
                    return ConfiguredModel.builder().modelFile(modelFile).build();
                }
            }
        });
    }
}
