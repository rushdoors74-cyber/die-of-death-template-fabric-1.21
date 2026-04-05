package net.badware.dieofdeath.item.advanced;

import net.badware.dieofdeath.block.ModBlocks;
import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class ArtfulWand extends Item {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.STONE_BRICK_WALL,
                    Blocks.END_STONE, Blocks.END_STONE_BRICK_WALL,
                    Blocks.DIRT, Blocks.ROOTED_DIRT,
                    Blocks.COBBLESTONE_WALL, ModBlocks.IMPLEMENT_WALL,
                    Blocks.DIAMOND_BLOCK, Blocks.EMERALD_BLOCK,
                    Blocks.OAK_PLANKS, ModBlocks.WOOD_BOX,
                    Blocks.LAPIS_ORE, ModBlocks.BONUSPAD_ORE
            );



    public ArtfulWand(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if(CHISEL_MAP.containsKey(clickedBlock)) {
            if(!world.isClient()) {
                world.setBlockState(context.getBlockPos(), CHISEL_MAP.get(clickedBlock).getDefaultState());

                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), ModSounds.WAND_USE, SoundCategory.BLOCKS);

            }

        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("With a flick of my wand- I bring your wildest dreams- into reality...!"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
