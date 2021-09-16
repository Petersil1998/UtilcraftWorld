package net.petersil98.utilcraft_world.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.petersil98.utilcraft_world.UtilcraftWorld;
import net.petersil98.utilcraft_world.blocks.UtilcraftWorldBlocks;

public class UtilcraftWorldItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UtilcraftWorld.MOD_ID);

    public static final RegistryObject<BlockItem> GRAVE = ITEMS.register("grave", () -> new BlockItem(UtilcraftWorldBlocks.GRAVE.get(), new Item.Properties().tab(UtilcraftWorld.ITEM_GROUP)));

    public static final RegistryObject<Switcher> SWITCHER = ITEMS.register("switcher", () -> new Switcher(new Item.Properties().tab(UtilcraftWorld.ITEM_GROUP)));
}
