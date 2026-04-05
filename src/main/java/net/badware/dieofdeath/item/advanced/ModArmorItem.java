package net.badware.dieofdeath.item.advanced;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class ModArmorItem extends ArmorItem {
    public ModArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, net.minecraft.entity.Entity entity, int slot, boolean selected) {
        if (!world.isClient && entity instanceof PlayerEntity player) {
            ItemStack chestStack = player.getEquippedStack(EquipmentSlot.CHEST);

            if (stack == chestStack) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 1, false, false, true));
            }
        }
    }

    public static boolean onPlayerDamage(LivingEntity entity, DamageSource source, float amount) {
        ItemStack chest = entity.getEquippedStack(EquipmentSlot.CHEST);

        if (chest.getItem() instanceof ModArmorItem) {
            if (source.getAttacker() != null || source.isOf(DamageTypes.ARROW)) {

                entity.heal(2.0f);
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 40, 4));

                if (entity instanceof PlayerEntity player) {
                    player.sendMessage(Text.literal("§a§lBLOCK SUCCESSFUL!"), true);
                    player.getWorld().playSound(null, player.getBlockPos(),
                            SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.0f, 0.5f);
                }

                chest.damage(1, entity, EquipmentSlot.CHEST);

                return false;
            }
        }
        return true;
    }

}