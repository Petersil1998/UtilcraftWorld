package net.petersil98.utilcraft_world.worldgen.biome;

import net.minecraft.util.ColorHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

public class Graveyard {

    public static final float DEPTH = 1;
    public static final float SCALE = 1;
    public static final Biome.RainType PRECIPITATION = Biome.RainType.NONE;
    public static final Biome.Category CATEGORY = Biome.Category.NONE;
    public static final float TEMPERATURE = 0.5f;
    public static final Biome.TemperatureModifier TEMPERATURE_MODIFIER = Biome.TemperatureModifier.NONE;
    public static final float DOWNFALL = 0.8f;
    public static final boolean PLAYER_SPAWN_FRIENDLY = false;
    public static final float CREATURE_SPAWN_PROBABILITY = 0.1f;
    public static final int FOG_COLOR = -16770141;
    public static final int FOLIAGE_COLOR = -16770141;
    public static final int GRASS_COLOR = -16770141;
    public static final int SKY_COLOR = -16770141;
    public static final int WATER_COLOR = ColorHelper.PackedColor.color(255, 255,236,0);
    public static final int WATER_FOG_COLOR = ColorHelper.PackedColor.color(255, 255,236,0);
    public static final BiomeAmbience.GrassColorModifier GRASS_COLOR_MODIFIER = BiomeAmbience.GrassColorModifier.NONE;

    public static Biome makeGraveyardBiome() {
        MobSpawnInfo.Builder modSpawner = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.plainsSpawns(modSpawner);
        if(PLAYER_SPAWN_FRIENDLY) {
            modSpawner.setPlayerCanSpawn();
        }
        modSpawner.creatureGenerationProbability(CREATURE_SPAWN_PROBABILITY);

        BiomeGenerationSettings.Builder generationSettings = (new BiomeGenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);

        DefaultBiomeFeatures.addDefaultCarvers(generationSettings);

        DefaultBiomeFeatures.addDefaultUndergroundVariety(generationSettings);
        DefaultBiomeFeatures.addDefaultSoftDisks(generationSettings);

        DefaultBiomeFeatures.addSurfaceFreezing(generationSettings);
        GraveyardBiomeFeatures.withGraves(generationSettings);
        return new Biome.Builder()
                .precipitation(PRECIPITATION)
                .biomeCategory(CATEGORY)
                .depth(DEPTH)
                .scale(SCALE)
                .temperature(TEMPERATURE)
                .temperatureAdjustment(TEMPERATURE_MODIFIER)
                .downfall(DOWNFALL)
                .specialEffects((new BiomeAmbience.Builder())
                        .waterColor(WATER_COLOR)
                        .waterFogColor(WATER_FOG_COLOR)
                        .fogColor(FOG_COLOR)
                        .skyColor(SKY_COLOR)
                        .foliageColorOverride(FOLIAGE_COLOR)
                        .grassColorOverride(GRASS_COLOR)
                        .grassColorModifier(GRASS_COLOR_MODIFIER)
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS)
                        .build())
                .mobSpawnSettings(modSpawner.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}
