package net.badware.dieofdeath.world;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> BONUSPAD_ORE_PLACED_KEY = registerKey("bonuspad_ore_placed");
    public static final RegistryKey<PlacedFeature> TUNDRA_TRENCH_TREE_PLACED_KEY = registerKey("tundra_trench_tree_placed");
    public static final RegistryKey<PlacedFeature> BACKYARD_BUSH_PLACED_KEY = registerKey("backyard_bush_placed");


    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, BONUSPAD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BONUS_PAD_ORE_KEY),
                ModOrePlacement.modifiersWithCount(7,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-50), YOffset.fixed(40))));

        register(context, TUNDRA_TRENCH_TREE_PLACED_KEY, context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE)
                        .getOrThrow(ModConfiguredFeatures.TUNDRA_TRENCH_TREE_KEY),
                List.of(
                        PlacedFeatures.createCountExtraModifier(3, 0.2f, 2),
                        SquarePlacementModifier.of(),
                        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                        PlacedFeatures.wouldSurvive(ModBlocks.TUNDRA_TRENCH_SAPLING),
                        BiomePlacementModifier.of()
                ));

        register(context, BACKYARD_BUSH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BACKYARD_BUSH_KEY),
                List.of(
                        PlacedFeatures.createCountExtraModifier(10, 0.1f, 6),
                        SquarePlacementModifier.of(),
                        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                        BiomePlacementModifier.of()
                ));

    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(DieOfDeath.MOD_ID, name));

    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {

        register(context, key, configuration, List.of(modifiers));
    }
}