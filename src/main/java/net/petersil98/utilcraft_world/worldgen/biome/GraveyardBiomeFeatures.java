package net.petersil98.utilcraft_world.worldgen.biome;

import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class GraveyardBiomeFeatures {

    public static void withGraves(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, UtilcraftWorldFeatures.GRAVES);
    }
}
