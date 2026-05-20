package net.badware.dieofdeath.datagen;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry root = Advancement.Builder.create()
                .display(ModItems.BADWARE_PC_SPAWN_EGG,
                        Text.literal("Die of Death"),
                        Text.literal("The digital plague begins"),
                        Identifier.of("minecraft", "textures/block/black_concrete.png"),
                        AdvancementFrame.TASK, true, true, false)
                .criterion("tick", InventoryChangedCriterion.Conditions.items(Items.AIR))
                .build(consumer, DieOfDeath.MOD_ID + ":root");

        AdvancementEntry badwareKill = Advancement.Builder.create().parent(root)
                .display(Items.WATER_BUCKET,
                        Text.literal("Virus Neutralized...?"),
                        Text.literal("Defeat the Badware for the first time."),
                        null, AdvancementFrame.CHALLENGE, true, true, false)
                .criterion("manual", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(consumer, DieOfDeath.MOD_ID + ":badware_first_kill");

        AdvancementEntry badwareSecondKill = Advancement.Builder.create().parent(badwareKill)
                .display(ModItems.Y2K,
                        Text.literal("How is it still alive?!"),
                        Text.literal("Defeat 2 Badwares."),
                        null, AdvancementFrame.CHALLENGE, true, true, false)
                .criterion("manual", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(consumer, DieOfDeath.MOD_ID + ":badware_second_kill");

        AdvancementEntry pursuerKill = Advancement.Builder.create().parent(root)
                .display(ModItems.PURSUER_CLEAVE,
                        Text.literal("AHH, FRESH MEAT!"),
                        Text.literal("Defeat the Pursuer for the first time."),
                        null, AdvancementFrame.CHALLENGE, true, true, false)
                .criterion("manual", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(consumer, DieOfDeath.MOD_ID + ":pursuer_first_kill");

        AdvancementEntry pursuerVariantKill = Advancement.Builder.create().parent(pursuerKill)
                .display(ModItems.PURSUER_SPAWN_EGG,
                        Text.literal("Pursuer again..?"),
                        Text.literal("Kill a variant of Pursuer."),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("manual", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(consumer, DieOfDeath.MOD_ID + ":pursuer_variant_kill");

        AdvancementEntry badwarePeonKill = Advancement.Builder.create().parent(badwareKill)
                .display(ModItems.BADWARE_SPAWN_EGG,
                        Text.literal("Badware again..?"),
                        Text.literal("Kill a peon of Badware."),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("manual", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(consumer, DieOfDeath.MOD_ID + ":badware_peon_kill");

        AdvancementEntry killerFirstKill = Advancement.Builder.create().parent(root)
                .display(ModItems.ETERNITY_V2_MUSIC_DISC,
                        Text.literal("Killer gets killed"),
                        Text.literal("Take down any one of the killers."),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("manual", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(consumer, DieOfDeath.MOD_ID + ":any_killer_kill");

        AdvancementEntry doubleTrouble = Advancement.Builder.create().parent(killerFirstKill)
                .display(ModItems.ETERNITY,
                        Text.literal("Double Trouble"),
                        Text.literal("You're a strong one!"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("manual", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(consumer, DieOfDeath.MOD_ID + ":double_trouble");

        AdvancementEntry oneBounce = Advancement.Builder.create().parent(doubleTrouble)
                .display(ModItems.ONE_BOUNCE,
                        Text.literal("One Bounce"),
                        Text.literal("How did you do it?"),
                        null, AdvancementFrame.CHALLENGE, true, true, false)
                .criterion("manual", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(consumer, DieOfDeath.MOD_ID + ":one_bounce");

        AdvancementEntry artfulKill = Advancement.Builder.create().parent(root)
                .display(ModItems.ARTFUL_WAND,
                        Text.literal("Not even magic can save you"),
                        Text.literal("Defeat the Artful for the first time."),
                        null, AdvancementFrame.CHALLENGE, true, true, false)
                .criterion("manual", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(consumer, DieOfDeath.MOD_ID + ":artful_first_kill");

        AdvancementEntry artfulVariantKill = Advancement.Builder.create().parent(pursuerKill)
                .display(ModItems.ARTFUL_SPAWN_EGG,
                        Text.literal("Artful again..?"),
                        Text.literal("Kill a variant of Artful."),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("manual", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(consumer, DieOfDeath.MOD_ID + ":artful_variant_kill");
    }
}