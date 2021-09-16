package net.petersil98.utilcraft_world.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
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
        getVariantBuilder(UtilcraftWorldBlocks.GRAVE.get()).forAllStates(blockState -> {
            ModelFile defaultModel = models().getExistingFile(new ResourceLocation(BlockItemUtils.namespace(UtilcraftWorldBlocks.GRAVE.get()), ModelProvider.BLOCK_FOLDER +"/"+BlockItemUtils.name(UtilcraftWorldBlocks.GRAVE.get())));
            ModelFile graveModel1 = models().getExistingFile(new ResourceLocation(BlockItemUtils.namespace(UtilcraftWorldBlocks.GRAVE.get()), ModelProvider.BLOCK_FOLDER +"/"+BlockItemUtils.name(UtilcraftWorldBlocks.GRAVE.get())+"2"));
            ModelFile graveModel2 = models().getExistingFile(new ResourceLocation(BlockItemUtils.namespace(UtilcraftWorldBlocks.GRAVE.get()), ModelProvider.BLOCK_FOLDER +"/"+BlockItemUtils.name(UtilcraftWorldBlocks.GRAVE.get())+"3"));
            int rotation;
            switch (blockState.getValue(Grave.FACING)) {
                case EAST -> rotation = 90;
                case SOUTH -> rotation = 180;
                case WEST -> rotation = 270;
                default -> rotation = 0;
            }
            return ConfiguredModel.builder()
                    .modelFile(defaultModel).rotationY(rotation).nextModel()
                    .modelFile(graveModel1).rotationY(rotation).nextModel()
                    .modelFile(graveModel2).rotationY(rotation)
                    .build();
        });
    }
}
