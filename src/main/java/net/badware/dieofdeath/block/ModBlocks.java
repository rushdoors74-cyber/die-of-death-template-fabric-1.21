package net.badware.dieofdeath.block;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.block.advanced.*;
import net.badware.dieofdeath.item.advanced.TundraTrenchLamp;
import net.badware.dieofdeath.sound.ModSounds;
import net.badware.dieofdeath.world.ModConfiguredFeatures;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;
import java.util.Optional;

public class ModBlocks {
    public static final Block IMPLEMENT_WALL = registerBlock("implement_wall",
            new Block(AbstractBlock.Settings.create().strength(2.5f, 8f)
                    .requiresTool().noBlockBreakParticles().sounds(ModSounds.IMPLEMENT_SOUNDS)) {
                @Override
                public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
                    tooltip.add(Text.translatable("The only thing stopping you from achieving your dreams are the mental roadblocks you set before yourself!"));
                    super.appendTooltip(stack, context, tooltip, options);
                }
            });

    public static final Block KILLER_ONLY_BLOCK = registerBlock("killer_only_block",
            new Block(AbstractBlock.Settings.create().strength(-1f, 982500000)
                    .noCollision().nonOpaque().emissiveLighting((state, world, pos) -> true)));

    public static final Block WOOD_BOX = registerBlock("wood_box",
            new Block(AbstractBlock.Settings.create().strength(1.5f, 1f).sounds(ModSounds.WOOD_BREAKING_FIRE)));

    public static final Block BONUSPAD_ORE = registerBlock("bonuspad_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 4),
                    AbstractBlock.Settings.create().strength(2.7f).requiresTool().sounds(BlockSoundGroup.STONE)));

    public static final Block BONUSPAD_DEEPSLATE_ORE = registerBlock("bonuspad_deepslate_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(4, 6),
                    AbstractBlock.Settings.create().strength(3.8f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block TEST_RAIL = registerBlock("test_rail",
            new Test_Rail(AbstractBlock.Settings.create().strength(0.5f, 1f).requiresTool()));

    public static final Block STAIRS_0 = registerBlock("stairs_0",
            new StairsBlock(ModBlocks.WOOD_BOX.getDefaultState(), AbstractBlock.Settings.create().strength(1.5f, 1f).requiresTool().sounds(ModSounds.WOOD_BREAKING_FIRE)));
public static final Block SLAB_0 = registerBlock("slab_0",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.5f, 1f).requiresTool().sounds(ModSounds.WOOD_BREAKING_FIRE)));

public static final Block BUTTON_0 = registerBlock("button_0",
            new ButtonBlock(BlockSetType.IRON, 15, AbstractBlock.Settings.create().strength(1.5f, 1f).requiresTool().noCollision().sounds(ModSounds.WOOD_BREAKING_FIRE)));
public static final Block PRESSURE_PLATE_0 = registerBlock("pressure_plate_0",
            new PressurePlateBlock(BlockSetType.IRON, AbstractBlock.Settings.create().strength(1.5f, 1f).requiresTool().sounds(ModSounds.WOOD_BREAKING_FIRE)));

public static final Block FENCE_0 = registerBlock("fence_0",
            new FenceBlock(AbstractBlock.Settings.create().strength(1.5f, 1f).requiresTool().sounds(ModSounds.WOOD_BREAKING_FIRE)));
public static final Block FENCE_GATE_0 = registerBlock("fence_gate_0",
            new FenceGateBlock(WoodType.OAK, AbstractBlock.Settings.create().strength(1.5f, 1f).requiresTool().sounds(ModSounds.WOOD_BREAKING_FIRE)));
public static final Block WALL_0 = registerBlock("wall_0",
            new WallBlock(AbstractBlock.Settings.create().strength(1.5f, 1f).requiresTool().sounds(ModSounds.WOOD_BREAKING_FIRE)));

public static final Block DOOR_0 = registerBlock("door_0",
            new DoorBlock(BlockSetType.IRON, AbstractBlock.Settings.create().strength(1.5f, 1f).requiresTool().nonOpaque().sounds(ModSounds.WOOD_BREAKING_FIRE)));
public static final Block TRAPDOOR_0 = registerBlock("trapdoor_0",
            new TrapdoorBlock(BlockSetType.IRON, AbstractBlock.Settings.create().strength(1.5f, 1f).requiresTool().nonOpaque().sounds(ModSounds.WOOD_BREAKING_FIRE)));

public static final Block TUNDRA_TRENCH_LAMP = registerBlock("tundra_trench_lamp", new TundraTrenchLamp(AbstractBlock.Settings.create().strength(0.5f).luminance(state -> state.get(TundraTrenchLamp.CLICKED) ? 15 : 0)));

public static final Block BACKYARD_BUSH = registerBlock("backyard_bush",
            new BackyardBushBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.GRASS)
                    .nonOpaque()
                    .burnable()));

public static final SaplingGenerator TUNDRA_TRENCH_GENERATING = new SaplingGenerator("tundra_trench_generating",
            Optional.of(ModConfiguredFeatures.MEGA_TUNDRA_TRENCH_TREE_KEY),
            Optional.of(ModConfiguredFeatures.TUNDRA_TRENCH_TREE_KEY),
            Optional.empty());

public static final Block TUNDRA_TRENCH_SAPLING = registerBlock("tundra_trench_sapling",
        new StoneSaplingBlock(TUNDRA_TRENCH_GENERATING, AbstractBlock.Settings.copy(Blocks.SPRUCE_SAPLING)));

public static final Block BONUSPAD = registerBlock("bonuspad",
        new BonuspadBlock(AbstractBlock.Settings.create().strength(3f,3f).requiresTool().nonOpaque()));

public static final Block CAREPAD = registerBlock("carepad",
        new CarepadBlock(AbstractBlock.Settings.create().strength(3f, 3f).requiresTool().nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(DieOfDeath.MOD_ID, name), block);
    }

    private static void  registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(DieOfDeath.MOD_ID,name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        DieOfDeath.LOGGER.info("Registering Mod Blocks for " + DieOfDeath.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(ModBlocks.IMPLEMENT_WALL);
            fabricItemGroupEntries.add(ModBlocks.KILLER_ONLY_BLOCK);
            fabricItemGroupEntries.add(ModBlocks.WOOD_BOX);
        });
    }
}
