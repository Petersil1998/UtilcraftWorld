package net.petersil98.utilcraft_world;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.petersil98.utilcraft_world.blocks.Grave;
import net.petersil98.utilcraft_world.blocks.UtilcraftWorldBlocks;
import net.petersil98.utilcraft_world.items.Switcher;
import net.petersil98.utilcraft_world.network.PacketHandler;
import net.petersil98.utilcraft_world.worldgen.biome.Graveyard;
import net.petersil98.utilcraft_world.worldgen.biome.UtilcraftWorldFeatures;

import javax.annotation.Nonnull;

@Mod(UtilcraftWorld.MOD_ID)
public class UtilcraftWorld
{
    public static final String MOD_ID = "utilcraft_world";
    public static final RegistryKey<World> AFTERLIFE_WORLD = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(MOD_ID, "afterlife"));
    public static final RegistryKey<Biome> MOD_BIOME = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(MOD_ID, "graveyard"));

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {

        @Nonnull
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.END_PORTAL_FRAME);
        }
    };

    public UtilcraftWorld() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.registerMessages();
            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(MOD_BIOME, 10));
        });
    }

    private void doClientStuff(@Nonnull final FMLClientSetupEvent event) {}

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> blockRegistryEvent) {
            blockRegistryEvent.getRegistry().register(new Grave().setRegistryName("grave"));
        }

        @SubscribeEvent
        public static void registerItems(@Nonnull final RegistryEvent.Register<Item> itemRegistryEvent) {
            itemRegistryEvent.getRegistry().register(new BlockItem(UtilcraftWorldBlocks.GRAVE, new Item.Properties().group(ITEM_GROUP)).setRegistryName("grave"));

            itemRegistryEvent.getRegistry().register(new Switcher().setRegistryName("switcher"));
        }

        @SubscribeEvent
        public static void registerEffects(@Nonnull final RegistryEvent.Register<Effect> effectRegistryEvent) {
        }

        @SubscribeEvent
        public static void registerEntities(@Nonnull final RegistryEvent.Register<EntityType<?>> entityRegister) {
        }

        @SubscribeEvent
        public static void registerBiomes(@Nonnull final RegistryEvent.Register<Biome> biomeRegister) {
            UtilcraftWorldFeatures.GRAVES = Feature.RANDOM_PATCH.withConfiguration(
                    new BlockClusterFeatureConfig.Builder(
                            new WeightedBlockStateProvider()
                                    .addWeightedBlockstate(UtilcraftWorldBlocks.GRAVE.getDefaultState(), 1)
                                    .addWeightedBlockstate(UtilcraftWorldBlocks.GRAVE.getDefaultState().with(Grave.FACING, Direction.SOUTH), 1)
                                    .addWeightedBlockstate(UtilcraftWorldBlocks.GRAVE.getDefaultState().with(Grave.FACING, Direction.EAST), 1)
                                    .addWeightedBlockstate(UtilcraftWorldBlocks.GRAVE.getDefaultState().with(Grave.FACING, Direction.WEST), 1)
                            , SimpleBlockPlacer.PLACER)
                            .tries(10)
                            .build())
                    .withPlacement(Features.Placements.HEIGHTMAP_SPREAD_DOUBLE_PLACEMENT);
            UtilcraftWorldFeatures.register("grave_feature", UtilcraftWorldFeatures.GRAVES);
            biomeRegister.getRegistry().register(Graveyard.makeGraveyardBiome().setRegistryName(MOD_ID, "graveyard"));
        }

        @SubscribeEvent
        public static void registerFeatures(@Nonnull final RegistryEvent.Register<Feature<?>> featureRegister) {
        }
    }
}
