package net.badware.dieofdeath.world.gen;

import net.badware.dieofdeath.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        ModOreGeneration.generateOres();

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                BiomeKeys.JAGGED_PEAKS,
                BiomeKeys.GROVE,
                BiomeKeys.SNOWY_SLOPES,
                BiomeKeys.SNOWY_TAIGA
        ),
                GenerationStep.Feature.VEGETAL_DECORATION,
                ModPlacedFeatures.TUNDRA_TRENCH_TREE_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                BiomeKeys.PLAINS,
                BiomeKeys.BIRCH_FOREST,
                BiomeKeys.DARK_FOREST,
                BiomeKeys.FLOWER_FOREST,
                BiomeKeys.FOREST,
                BiomeKeys.SUNFLOWER_PLAINS
        ), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BACKYARD_BUSH_PLACED_KEY);


    }
}
