package net.petersil98.utilcraft_world;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
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
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
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
import java.lang.reflect.Field;

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
            Field effects = ObfuscationReflectionHelper.findField(DimensionRenderInfo.class, "field_239208_a_");
            try {
                DimensionRenderInfo dimensionRenderInfo = new DimensionRenderInfo.Overworld();
                dimensionRenderInfo.setCloudRenderHandler((ticks, partialTicks, matrixStack, world, mc, viewEntityX, viewEntityY, viewEntityZ) -> {});
                ((Object2ObjectMap)effects.get(null)).put(AFTERLIFE_WORLD.location(), dimensionRenderInfo);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
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
            itemRegistryEvent.getRegistry().register(new BlockItem(UtilcraftWorldBlocks.GRAVE, new Item.Properties().tab(ITEM_GROUP)).setRegistryName("grave"));

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
            UtilcraftWorldFeatures.GRAVES = Feature.RANDOM_PATCH.configured(
                    new BlockClusterFeatureConfig.Builder(
                            new WeightedBlockStateProvider()
                                    .add(UtilcraftWorldBlocks.GRAVE.defaultBlockState(), 1)
                                    .add(UtilcraftWorldBlocks.GRAVE.defaultBlockState().setValue(Grave.FACING, Direction.SOUTH), 1)
                                    .add(UtilcraftWorldBlocks.GRAVE.defaultBlockState().setValue(Grave.FACING, Direction.EAST), 1)
                                    .add(UtilcraftWorldBlocks.GRAVE.defaultBlockState().setValue(Grave.FACING, Direction.WEST), 1)
                            , SimpleBlockPlacer.INSTANCE)
                            .tries(10)
                            .build())
                    .decorated(Features.Placements.HEIGHTMAP_DOUBLE);
            UtilcraftWorldFeatures.register("grave_feature", UtilcraftWorldFeatures.GRAVES);
            biomeRegister.getRegistry().register(Graveyard.makeGraveyardBiome().setRegistryName(MOD_ID, "graveyard"));
        }

        @SubscribeEvent
        public static void registerFeatures(@Nonnull final RegistryEvent.Register<Feature<?>> featureRegister) {
        }
    }
}
