package net.badware.dieofdeath.datagen;

import net.badware.dieofdeath.block.ModBlocks;
import net.badware.dieofdeath.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.IMPLEMENT_WALL, block ->
                dropsWithSilkTouch(block,
                        ItemEntry.builder(ModItems.WALL_BRICK)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(6.0f)))
                )
        );
        addDrop(ModBlocks.WOOD_BOX);

        addDrop(ModBlocks.BONUSPAD_ORE, oreDrops(ModBlocks.BONUSPAD_ORE, ModItems.BONUSPAD_SHARD));
        addDrop(ModBlocks.BONUSPAD_DEEPSLATE_ORE, multipleOreDrops(ModBlocks.BONUSPAD_DEEPSLATE_ORE, ModItems.BONUSPAD_SHARD, 4, 8));

        addDrop(ModBlocks.STAIRS_0);
        addDrop(ModBlocks.SLAB_0, slabDrops(ModBlocks.SLAB_0));

        addDrop(ModBlocks.BUTTON_0);
        addDrop(ModBlocks.PRESSURE_PLATE_0);

        addDrop(ModBlocks.WALL_0);
        addDrop(ModBlocks.FENCE_0);
        addDrop(ModBlocks.FENCE_GATE_0);

        addDrop(ModBlocks.TRAPDOOR_0);
        addDrop(ModBlocks.DOOR_0, doorDrops(ModBlocks.DOOR_0));

        this.addDrop(ModBlocks.BACKYARD_BUSH);

    }

    public LootTable.Builder multipleOreDrops(Block drop, Item item, float minDrops, float maxDrops) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop, ((LeafEntry.Builder<?>)
                ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops))))
                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
    }
}
