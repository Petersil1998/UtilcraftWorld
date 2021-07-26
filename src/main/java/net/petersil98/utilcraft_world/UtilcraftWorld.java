package net.petersil98.utilcraft_world;

import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.data.worldgen.Features;
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
import net.petersil98.utilcraft_world.utils.ClientSetup;
import net.petersil98.utilcraft_world.worldgen.biome.Graveyard;
import net.petersil98.utilcraft_world.worldgen.biome.UtilcraftWorldFeatures;

import javax.annotation.Nonnull;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Mod(UtilcraftWorld.MOD_ID)
public class UtilcraftWorld
{
    public static final String MOD_ID = "utilcraft_world";
    public static final ResourceKey<Level> AFTERLIFE_WORLD = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(MOD_ID, "afterlife"));
    public static final ResourceKey<Biome> MOD_BIOME = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(MOD_ID, "graveyard"));

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(MOD_ID) {

        @Nonnull
        @Override
        public ItemStack makeIcon() {
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

    private void doClientStuff(@Nonnull final FMLClientSetupEvent event) {
        ClientSetup.disableCloudRenderingInAfterlife();
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> blockRegistryEvent) {
            blockRegistryEvent.getRegistry().register(new Grave().setRegistryName("grave"));
        }

        @SubscribeEvent
        public static void registerItems(@Nonnull final RegistryEvent.Register<Item> itemRegistryEvent) {
            itemRegistryEvent.getRegistry().register(new BlockItem(UtilcraftWorldBlocks.GRAVE, new Item.Properties().tab(ITEM_GROUP)).setRegistryName("grave"));

            itemRegistryEvent.getRegistry().register(new Switcher().setRegistryName("switcher"));
        }

        @SubscribeEvent
        public static void registerEffects(@Nonnull final RegistryEvent.Register<MobEffect> effectRegistryEvent) {
        }

        @SubscribeEvent
        public static void registerEntities(@Nonnull final RegistryEvent.Register<EntityType<?>> entityRegister) {
        }

        @SubscribeEvent
        public static void registerBiomes(@Nonnull final RegistryEvent.Register<Biome> biomeRegister) {
            UtilcraftWorldFeatures.GRAVES = Feature.RANDOM_PATCH.configured(
                    new RandomPatchConfiguration.GrassConfigurationBuilder(
                            new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                    .add(UtilcraftWorldBlocks.GRAVE.defaultBlockState(), 1)
                                    .add(UtilcraftWorldBlocks.GRAVE.defaultBlockState().setValue(Grave.FACING, Direction.SOUTH), 1)
                                    .add(UtilcraftWorldBlocks.GRAVE.defaultBlockState().setValue(Grave.FACING, Direction.EAST), 1)
                                    .add(UtilcraftWorldBlocks.GRAVE.defaultBlockState().setValue(Grave.FACING, Direction.WEST), 1).build()
                            ), SimpleBlockPlacer.INSTANCE)
                            .tries(10)
                            .build())
                    .decorated(Features.Decorators.HEIGHTMAP_DOUBLE);
            UtilcraftWorldFeatures.register("grave_feature", UtilcraftWorldFeatures.GRAVES);
            biomeRegister.getRegistry().register(Graveyard.makeGraveyardBiome().setRegistryName(MOD_ID, "graveyard"));
        }

        @SubscribeEvent
        public static void registerFeatures(@Nonnull final RegistryEvent.Register<Feature<?>> featureRegister) {
        }
    }
}
