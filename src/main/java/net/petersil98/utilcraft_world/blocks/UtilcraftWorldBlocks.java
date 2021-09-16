package net.petersil98.utilcraft_world.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.petersil98.utilcraft_world.UtilcraftWorld;

public class UtilcraftWorldBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UtilcraftWorld.MOD_ID);

    public static final RegistryObject<Grave> GRAVE = BLOCKS.register("grave", () -> new Grave(AbstractBlock.Properties.of(Material.STONE, MaterialColor.STONE).noOcclusion().noDrops()));
}
