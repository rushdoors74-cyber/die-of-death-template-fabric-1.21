package net.badware.dieofdeath;

import net.badware.dieofdeath.block.ModBlocks;
import net.badware.dieofdeath.effect.ModEffects;
import net.badware.dieofdeath.enchantment.custom.EntanglementEnchantmentEffect;
import net.badware.dieofdeath.entity.ModEntities;
import net.badware.dieofdeath.entity.custom.BadwareEntity;
import net.badware.dieofdeath.entity.custom.BadwarePCEntity;
import net.badware.dieofdeath.entity.custom.PursuerEntity;
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
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
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
	}
}