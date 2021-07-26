package net.petersil98.utilcraft_world.worldgen.biome;

import net.minecraft.util.FastColor;
import net.minecraft.data.worldgen.SurfaceBuilders;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class Graveyard {

    public static final float DEPTH = 1;
    public static final float SCALE = 1;
    public static final Biome.Precipitation PRECIPITATION = Biome.Precipitation.NONE;
    public static final Biome.BiomeCategory CATEGORY = Biome.BiomeCategory.NONE;
    public static final float TEMPERATURE = 0.5f;
    public static final Biome.TemperatureModifier TEMPERATURE_MODIFIER = Biome.TemperatureModifier.NONE;
    public static final float DOWNFALL = 0.8f;
    public static final boolean PLAYER_SPAWN_FRIENDLY = false;
    public static final float CREATURE_SPAWN_PROBABILITY = 0.1f;
    public static final int FOG_COLOR = -16770141;
    public static final int FOLIAGE_COLOR = -16770141;
    public static final int GRASS_COLOR = -16770141;
    public static final int SKY_COLOR = -16770141;
    public static final int WATER_COLOR = FastColor.ARGB32.color(255, 255,236,0);
    public static final int WATER_FOG_COLOR = FastColor.ARGB32.color(255, 255,236,0);
    public static final BiomeSpecialEffects.GrassColorModifier GRASS_COLOR_MODIFIER = BiomeSpecialEffects.GrassColorModifier.NONE;

    public static Biome makeGraveyardBiome() {
        MobSpawnSettings.Builder modSpawner = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.plainsSpawns(modSpawner);
        if(PLAYER_SPAWN_FRIENDLY) {
            modSpawner.setPlayerCanSpawn();
        }
        modSpawner.creatureGenerationProbability(CREATURE_SPAWN_PROBABILITY);

        BiomeGenerationSettings.Builder generationSettings = (new BiomeGenerationSettings.Builder()).surfaceBuilder(SurfaceBuilders.GRASS);

        BiomeDefaultFeatures.addDefaultCarvers(generationSettings);

        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);

        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
        GraveyardBiomeFeatures.withGraves(generationSettings);
        return new Biome.BiomeBuilder()
                .precipitation(PRECIPITATION)
                .biomeCategory(CATEGORY)
                .depth(DEPTH)
                .scale(SCALE)
                .temperature(TEMPERATURE)
                .temperatureAdjustment(TEMPERATURE_MODIFIER)
                .downfall(DOWNFALL)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(WATER_COLOR)
                        .waterFogColor(WATER_FOG_COLOR)
                        .fogColor(FOG_COLOR)
                        .skyColor(SKY_COLOR)
                        .foliageColorOverride(FOLIAGE_COLOR)
                        .grassColorOverride(GRASS_COLOR)
                        .grassColorModifier(GRASS_COLOR_MODIFIER)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .mobSpawnSettings(modSpawner.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}
