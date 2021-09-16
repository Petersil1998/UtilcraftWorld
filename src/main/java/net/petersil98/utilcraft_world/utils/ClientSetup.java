package net.petersil98.utilcraft_world.utils;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.petersil98.utilcraft_world.UtilcraftWorld;

import java.lang.reflect.Field;

public class ClientSetup {

    public static void disableCloudRenderingInAfterlife(){
        Field effects = ObfuscationReflectionHelper.findField(DimensionSpecialEffects.class, "f_108857_");
        try {
            DimensionSpecialEffects dimensionRenderInfo = new DimensionSpecialEffects.OverworldEffects();
            dimensionRenderInfo.setCloudRenderHandler((ticks, partialTicks, matrixStack, world, mc, viewEntityX, viewEntityY, viewEntityZ) -> {});
            ((Object2ObjectMap)effects.get(null)).put(UtilcraftWorld.AFTERLIFE_WORLD.location(), dimensionRenderInfo);
        } catch (IllegalAccessException e) {
            UtilcraftWorld.LOGGER.error("Couldn't disable Cloud Rendering", e);
        }
    }
}
