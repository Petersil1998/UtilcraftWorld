package net.petersil98.utilcraft_world;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.petersil98.utilcraft_world.blocks.Grave;
import net.petersil98.utilcraft_world.blocks.UtilcraftWorldBlocks;
import net.petersil98.utilcraft_world.items.UtilcraftWorldItems;
import net.petersil98.utilcraft_world.network.PacketHandler;
import net.petersil98.utilcraft_world.utils.ClientSetup;
import net.petersil98.utilcraft_world.worldgen.biome.Graveyard;
import net.petersil98.utilcraft_world.worldgen.biome.UtilcraftWorldFeatures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(UtilcraftWorld.MOD_ID)
public class UtilcraftWorld
{
    public static final String MOD_ID = "utilcraft_world";
    public static final RegistryKey<World> AFTERLIFE_WORLD = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(MOD_ID, "afterlife"));
    public static final RegistryKey<Biome> MOD_BIOME = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(MOD_ID, "graveyard"));

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {

        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.END_PORTAL_FRAME);
        }
    };

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public UtilcraftWorld() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::doClientStuff);
        UtilcraftWorldBlocks.BLOCKS.register(eventBus);
        UtilcraftWorldItems.ITEMS.register(eventBus);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.registerMessages();
            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(MOD_BIOME, 10));
        });
    }

    private void doClientStuff(@Nonnull final FMLClientSetupEvent event) {
        event.enqueueWork(ClientSetup::disableCloudRenderingInAfterlife);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerBiomes(@Nonnull final RegistryEvent.Register<Biome> biomeRegister) {
            UtilcraftWorldFeatures.GRAVES = Feature.RANDOM_PATCH.configured(
                            new BlockClusterFeatureConfig.Builder(
                                    new WeightedBlockStateProvider()
                                            .add(UtilcraftWorldBlocks.GRAVE.get().defaultBlockState(), 1)
                                            .add(UtilcraftWorldBlocks.GRAVE.get().defaultBlockState().setValue(Grave.FACING, Direction.SOUTH), 1)
                                            .add(UtilcraftWorldBlocks.GRAVE.get().defaultBlockState().setValue(Grave.FACING, Direction.EAST), 1)
                                            .add(UtilcraftWorldBlocks.GRAVE.get().defaultBlockState().setValue(Grave.FACING, Direction.WEST), 1)
                                    , SimpleBlockPlacer.INSTANCE)
                                    .tries(10)
                                    .build())
                    .decorated(Features.Placements.HEIGHTMAP_DOUBLE);
            UtilcraftWorldFeatures.register("grave_feature", UtilcraftWorldFeatures.GRAVES);
            biomeRegister.getRegistry().register(Graveyard.makeGraveyardBiome().setRegistryName(MOD_ID, "graveyard"));
        }
    }
}
