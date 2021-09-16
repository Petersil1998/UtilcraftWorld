package net.petersil98.utilcraft_world.utils;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ColorHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.petersil98.utilcraft_world.UtilcraftWorld;

import java.lang.reflect.Field;

public class ClientSetup {

    public static void disableCloudRenderingInAfterlife(){
        System.out.println("Color " + ColorHelper.PackedColor.color(255, 255,236,0));
        Field effects = ObfuscationReflectionHelper.findField(DimensionRenderInfo.class, "field_239208_a_");
        try {
            DimensionRenderInfo dimensionRenderInfo = new DimensionRenderInfo.Overworld();
            dimensionRenderInfo.setCloudRenderHandler((ticks, partialTicks, matrixStack, world, mc, viewEntityX, viewEntityY, viewEntityZ) -> {});
            ((Object2ObjectMap)effects.get(null)).put(UtilcraftWorld.AFTERLIFE_WORLD.location(), dimensionRenderInfo);
        } catch (IllegalAccessException e) {
            UtilcraftWorld.LOGGER.error("Couldn't disable Cloud Rendering", e);
        }
    }
}
