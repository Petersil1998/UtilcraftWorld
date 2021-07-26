package net.petersil98.utilcraft_world.utils;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;

import javax.annotation.Nonnull;

public class BlockItemUtils {

    @Nonnull
    public static String name(@Nonnull Item item) {
        return item.getRegistryName().getPath();
    }

    @Nonnull
    public static String name(@Nonnull Block block) {
        return block.getRegistryName().getPath();
    }

    @Nonnull
    public static String namespace(@Nonnull Item item) {
        return item.getRegistryName().getNamespace();
    }

    @Nonnull
    public static String namespace(@Nonnull Block block) {
        return block.getRegistryName().getNamespace();
    }
}
