package net.petersil98.utilcraft_world.worldgen.biome;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;

public class GraveyardBiomeFeatures {

    public static void withGraves(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, UtilcraftWorldFeatures.GRAVES);
    }
}
