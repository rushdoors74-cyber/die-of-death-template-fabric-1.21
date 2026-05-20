package net.badware.dieofdeath;

import net.badware.dieofdeath.block.ModBlocks;
import net.badware.dieofdeath.effect.ModEffects;
import net.badware.dieofdeath.enchantment.ModEnchantments;
import net.badware.dieofdeath.enchantment.custom.EntanglementEnchantmentEffect;
import net.badware.dieofdeath.entity.ModEntities;
import net.badware.dieofdeath.entity.custom.*;
import net.badware.dieofdeath.item.ModItemGroups;
import net.badware.dieofdeath.item.ModItems;
import net.badware.dieofdeath.item.advanced.ModArmorItem;
import net.badware.dieofdeath.particle.ModParticles;
import net.badware.dieofdeath.sound.ModSounds;
import net.badware.dieofdeath.util.HammerUsageEvent;
import net.badware.dieofdeath.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetEnchantmentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DieOfDeath implements ModInitializer {
	public static final String MOD_ID = "dieofdeath";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BACKYARD_BUSH, 30, 60);

		ModSounds.registerSounds();

		ModEffects.registerEffects();

		ModWorldGeneration.generateModWorldGen();

		Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
				Identifier.of("dieofdeath", "entanglement"),
				EntanglementEnchantmentEffect.CODEC);

		FuelRegistry.INSTANCE.add(ModItems.BOILING_WATER, 6020);

		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());

		ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
			return ModArmorItem.onPlayerDamage(entity, source, amount);
		});

		ModEntities.registerModEntities();

		FabricDefaultAttributeRegistry.register(ModEntities.PURSUER, PursuerEntity.createPursuerAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.BADWARE, BadwareEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.BADWARE_PC, BadwarePCEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ARTFUL, ArtfulEntity.createArtfulAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ARTFUL_WALL, ArtfulWallEntity.createWallAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ARTFUL_MUSIC_BOX, ArtfulMusicBoxEntity.createMusicBoxAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ARTFUL_PUPPET, ArtfulPuppetEntity.createPuppetAttributes());

		ModParticles.registerParticles();

		ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, sender, params) -> {
			if (sender.hasStatusEffect(ModEffects.RAGDOLLED)) {
				sender.sendMessage(Text.literal("§c[SYSTEM LOCKED: CANNOT ACCESS CHAT]"), true);
				return false;
			}
			return true;
		});

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
		});

		UseItemCallback.EVENT.register((player, world, hand) -> {
			if (player.hasStatusEffect(ModEffects.RAGDOLLED)) {
				return TypedActionResult.fail(player.getStackInHand(hand));
			}
			return TypedActionResult.pass(player.getStackInHand(hand));
		});

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (player.hasStatusEffect(ModEffects.RAGDOLLED)) {
				return ActionResult.FAIL;
			}
			return ActionResult.PASS;
		});

		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
			if (player.hasStatusEffect(ModEffects.RAGDOLLED)) {
				return ActionResult.FAIL;
			}
			return ActionResult.PASS;
		});

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (source.isBuiltin() && (
					key.equals(LootTables.STRONGHOLD_LIBRARY_CHEST) ||
							key.equals(LootTables.STRONGHOLD_CORRIDOR_CHEST) ||
							key.equals(LootTables.STRONGHOLD_CROSSING_CHEST) ||
							key.equals(LootTables.ANCIENT_CITY_CHEST) ||
							key.equals(LootTables.END_CITY_TREASURE_CHEST))) {

				var enchantmentRegistry = registries.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
				var entanglementKey = enchantmentRegistry.getOrThrow(ModEnchantments.ENTANGLEMENT);

				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(0.20f))
						.with(ItemEntry.builder(Items.ENCHANTED_BOOK).weight(20)
								.apply(new SetEnchantmentsLootFunction.Builder().enchantment(entanglementKey, ConstantLootNumberProvider.create(1))))
						.with(ItemEntry.builder(Items.ENCHANTED_BOOK).weight(15)
								.apply(new SetEnchantmentsLootFunction.Builder().enchantment(entanglementKey, ConstantLootNumberProvider.create(2))))
						.with(ItemEntry.builder(Items.ENCHANTED_BOOK).weight(10)
								.apply(new SetEnchantmentsLootFunction.Builder().enchantment(entanglementKey, ConstantLootNumberProvider.create(3))))
						.with(ItemEntry.builder(Items.ENCHANTED_BOOK).weight(5)
								.apply(new SetEnchantmentsLootFunction.Builder().enchantment(entanglementKey, ConstantLootNumberProvider.create(4))));

				tableBuilder.pool(poolBuilder);
			}
		});

	}
}