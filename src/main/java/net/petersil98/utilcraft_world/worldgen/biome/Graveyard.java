package net.petersil98.utilcraft_world.worldgen.biome;

import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
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
    public static final int WATER_COLOR = -16770141;
    public static final int WATER_FOG_COLOR = -16770141;
    public static final BiomeAmbience.GrassColorModifier GRASS_COLOR_MODIFIER = BiomeAmbience.GrassColorModifier.NONE;

    public static Biome makeGraveyardBiome() {
        MobSpawnInfo.Builder modSpawner = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withSpawnsWithHorseAndDonkey(modSpawner);
        if(PLAYER_SPAWN_FRIENDLY) {
            modSpawner.isValidSpawnBiomeForPlayer();
        }
        modSpawner.withCreatureSpawnProbability(CREATURE_SPAWN_PROBABILITY);

        BiomeGenerationSettings.Builder generationSettings = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        /*if (!isSunflowerVariant) {
            generationSettings.withStructure(StructureFeatures.VILLAGE_PLAINS).withStructure(StructureFeatures.PILLAGER_OUTPOST);
        }*/

        DefaultBiomeFeatures.withStrongholdAndMineshaft(generationSettings);
        generationSettings.withStructure(StructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.withCavesAndCanyons(generationSettings);
        DefaultBiomeFeatures.withLavaAndWaterLakes(generationSettings);
        DefaultBiomeFeatures.withMonsterRoom(generationSettings);
        DefaultBiomeFeatures.withNoiseTallGrass(generationSettings);

        DefaultBiomeFeatures.withCommonOverworldBlocks(generationSettings);
        DefaultBiomeFeatures.withOverworldOres(generationSettings);
        DefaultBiomeFeatures.withDisks(generationSettings);
        DefaultBiomeFeatures.withPlainGrassVegetation(generationSettings);

        DefaultBiomeFeatures.withNormalMushroomGeneration(generationSettings);

        DefaultBiomeFeatures.withLavaAndWaterSprings(generationSettings);
        DefaultBiomeFeatures.withFrozenTopLayer(generationSettings);
        GraveyardBiomeFeatures.withGraves(generationSettings);
        return new Biome.Builder()
                .precipitation(PRECIPITATION)
                .category(CATEGORY)
                .depth(DEPTH)
                .scale(SCALE)
                .temperature(TEMPERATURE)
                .withTemperatureModifier(TEMPERATURE_MODIFIER)
                .downfall(DOWNFALL)
                .setEffects((new BiomeAmbience.Builder())
                        .setWaterColor(WATER_COLOR)
                        .setWaterFogColor(WATER_FOG_COLOR)
                        .setFogColor(FOG_COLOR)
                        .withSkyColor(SKY_COLOR)
                        .withFoliageColor(FOLIAGE_COLOR)
                        .withGrassColor(GRASS_COLOR)
                        .withGrassColorModifier(GRASS_COLOR_MODIFIER)
                        .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE)
                        .build())
                .withMobSpawnSettings(modSpawner.copy())
                .withGenerationSettings(generationSettings.build())
                .build();
    }
}
