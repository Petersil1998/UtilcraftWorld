package net.petersil98.utilcraft_world;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
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

import javax.annotation.Nonnull;

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
                    new RandomPatchConfiguration.GrassConfigurationBuilder(
                            new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                    .add(UtilcraftWorldBlocks.GRAVE.get().defaultBlockState(), 1)
                                    .add(UtilcraftWorldBlocks.GRAVE.get().defaultBlockState().setValue(Grave.FACING, Direction.SOUTH), 1)
                                    .add(UtilcraftWorldBlocks.GRAVE.get().defaultBlockState().setValue(Grave.FACING, Direction.EAST), 1)
                                    .add(UtilcraftWorldBlocks.GRAVE.get().defaultBlockState().setValue(Grave.FACING, Direction.WEST), 1).build()
                            ), SimpleBlockPlacer.INSTANCE)
                            .tries(10)
                            .build())
                    .decorated(Features.Decorators.HEIGHTMAP_DOUBLE);
            UtilcraftWorldFeatures.register("grave_feature", UtilcraftWorldFeatures.GRAVES);
            biomeRegister.getRegistry().register(Graveyard.makeGraveyardBiome().setRegistryName(MOD_ID, "graveyard"));
        }
    }
}
