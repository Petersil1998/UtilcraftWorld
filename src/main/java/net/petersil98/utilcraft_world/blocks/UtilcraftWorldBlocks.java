package net.petersil98.utilcraft_world.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.petersil98.utilcraft_world.UtilcraftWorld;

public class UtilcraftWorldBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UtilcraftWorld.MOD_ID);

    public static final RegistryObject<Grave> GRAVE = BLOCKS.register("grave", () -> new Grave(Block.Properties.of(Material.STONE, MaterialColor.STONE).noOcclusion().noDrops()));
}
