package net.badware.dieofdeath.world;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.block.ModBlocks;
import net.badware.dieofdeath.block.advanced.BackyardBushBlock;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.AttachedToLeavesTreeDecorator;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> BONUS_PAD_ORE_KEY = registryKey("bonuspad_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> TUNDRA_TRENCH_TREE_KEY = registryKey("tundra_trench_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_TUNDRA_TRENCH_TREE_KEY = registryKey("mega_tundra_trench_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BACKYARD_BUSH_KEY = registryKey("backyard_bush");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreFeatureConfig.Target> overworldBonuspadOre =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.BONUSPAD_ORE.getDefaultState()),
                OreFeatureConfig.createTarget(deepslateReplaceable, ModBlocks.BONUSPAD_DEEPSLATE_ORE.getDefaultState()));

        register(context, BONUS_PAD_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldBonuspadOre, 9));

        register(context, TUNDRA_TRENCH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(6, 3, 1),
                BlockStateProvider.of(Blocks.SPRUCE_LEAVES),
                new SpruceFoliagePlacer(UniformIntProvider.create(3, 5), UniformIntProvider.create(0, 2), UniformIntProvider.create(0, 2)),
                new TwoLayersFeatureSize(2, 0, 2)
        ).decorators(List.of(new AttachedToLeavesTreeDecorator(
                0.5f,
                0,
                0,
                BlockStateProvider.of(Blocks.SNOW),
                1,
                List.of(Direction.UP)
        )))
                .dirtProvider(BlockStateProvider.of(Blocks.STONE))
                .build());

        register(context, MEGA_TUNDRA_TRENCH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(Blocks.SPRUCE_LOG),
                new GiantTrunkPlacer(15, 1, 12),
                BlockStateProvider.of(Blocks.SPRUCE_LEAVES),
                new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(15, 18)),
                new TwoLayersFeatureSize(1, 1, 2)
        ).decorators(List.of(new AttachedToLeavesTreeDecorator(
                        0.7f,
                        0,
                        0,
                        BlockStateProvider.of(Blocks.SNOW),
                        1,
                        List.of(Direction.UP)
                )))
                .dirtProvider(BlockStateProvider.of(Blocks.DIORITE))
                .build());

        register(context, BACKYARD_BUSH_KEY, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                BlockStateProvider.of(ModBlocks.BACKYARD_BUSH.getDefaultState().with(BackyardBushBlock.AGE, 1))
        ));

    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registryKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(DieOfDeath.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}