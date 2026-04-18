package net.badware.dieofdeath.datagen;

import net.badware.dieofdeath.block.ModBlocks;
import net.badware.dieofdeath.block.advanced.BackyardBushBlock;
import net.badware.dieofdeath.item.ModItems;
import net.badware.dieofdeath.item.advanced.TundraTrenchLamp;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.KILLER_ONLY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BONUSPAD_DEEPSLATE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BONUSPAD_ORE);

        BlockStateModelGenerator.BlockTexturePool woodBox = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.WOOD_BOX);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.IMPLEMENT_WALL);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TEST_RAIL);

        woodBox.stairs(ModBlocks.STAIRS_0);
        woodBox.button(ModBlocks.BUTTON_0);
        woodBox.fence(ModBlocks.FENCE_0);
        woodBox.fenceGate(ModBlocks.FENCE_GATE_0);
        woodBox.pressurePlate(ModBlocks.PRESSURE_PLATE_0);
        woodBox.slab(ModBlocks.SLAB_0);
        woodBox.wall(ModBlocks.WALL_0);

        blockStateModelGenerator.registerDoor(ModBlocks.DOOR_0);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.TRAPDOOR_0);

        Identifier lampOffIdentifier = TexturedModel.CUBE_ALL.upload(ModBlocks.TUNDRA_TRENCH_LAMP, blockStateModelGenerator.modelCollector);
        Identifier lampOnIdentifier = blockStateModelGenerator.createSubModel(ModBlocks.TUNDRA_TRENCH_LAMP, "_on", Models.CUBE_ALL, TextureMap::all);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.TUNDRA_TRENCH_LAMP).coordinate(BlockStateModelGenerator.createBooleanModelMap(TundraTrenchLamp.CLICKED, lampOnIdentifier, lampOffIdentifier)));

        Identifier stage0 = blockStateModelGenerator.createSubModel(ModBlocks.BACKYARD_BUSH, "_stage0", Models.CROSS, TextureMap::cross);
        Identifier stage1 = blockStateModelGenerator.createSubModel(ModBlocks.BACKYARD_BUSH, "_stage1", Models.CROSS, TextureMap::cross);

        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(ModBlocks.BACKYARD_BUSH)
                        .coordinate(BlockStateVariantMap.create(BackyardBushBlock.AGE)
                                .register(0, BlockStateVariant.create().put(VariantSettings.MODEL, stage0))
                                .register(1, BlockStateVariant.create().put(VariantSettings.MODEL, stage1))
                        )
        );

        blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.TUNDRA_TRENCH_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BONUSPAD_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CAREPAD_BLOCK);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ARTFUL_WAND, Models.GENERATED);
        itemModelGenerator.register(ModItems.BONUSPAD_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.WALL_BRICK, Models.GENERATED);
        itemModelGenerator.register(ModItems.BOILING_WATER, Models.GENERATED);
        itemModelGenerator.register(ModItems.BOX_CHAIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.CEMENT, Models.GENERATED);
        itemModelGenerator.register(ModItems.HOTDOG, Models.GENERATED);
        itemModelGenerator.register(ModItems.BONUSPAD_INGOT, Models.GENERATED);

        itemModelGenerator.register(ModItems.PURSUER_CLEAVE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.YELLOW_THING, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BLUE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SHOVEL_STUFF, Models.HANDHELD);
        itemModelGenerator.register(ModItems.WEIRD_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.IRON_HAMMER, Models.HANDHELD);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.BLOCK_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BLOCK_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BLOCK_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BLOCK_BOOTS);

        itemModelGenerator.register(ModItems.ETERNITY_V2_MUSIC_DISC, Models.GENERATED);
        itemModelGenerator.register(ModItems.GRANDMA_BACKYARD_MUSIC_DISC, Models.GENERATED);
        itemModelGenerator.register(ModItems.TEMPLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.ROBLOX_HQ, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASEPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.TUNDRA_TRENCH, Models.GENERATED);
        itemModelGenerator.register(ModItems.TEAPOT_PALACE_TOUR_CALM, Models.GENERATED);
        itemModelGenerator.register(ModItems.TEAPOT_PALACE_TOUR_RAIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.TEAPOT_PALACE_TOUR_LMS, Models.GENERATED);
        itemModelGenerator.register(ModItems.OMEGAS_FINAL_STAND, Models.GENERATED);
        itemModelGenerator.register(ModItems.CARELESS, Models.GENERATED);
        itemModelGenerator.register(ModItems.VIGILANTE_SHOOTDOWN, Models.GENERATED);
        itemModelGenerator.register(ModItems.SHOWTIME, Models.GENERATED);
        itemModelGenerator.register(ModItems.ETERNITY, Models.GENERATED);
        itemModelGenerator.register(ModItems.Y2K, Models.GENERATED);
        itemModelGenerator.register(ModItems.ONE_BOUNCE, Models.GENERATED);

        itemModelGenerator.register(ModBlocks.BACKYARD_BUSH.asItem(), Models.GENERATED);

        itemModelGenerator.register(ModItems.CAREPAD_INGOT, Models.GENERATED);

        itemModelGenerator.register(ModItems.PURSUER_SPAWN_EGG,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));

    }
}
