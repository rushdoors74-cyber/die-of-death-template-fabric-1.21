package net.badware.dieofdeath.entity;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<PursuerEntity> PURSUER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DieOfDeath.MOD_ID, "pursuer"),
            EntityType.Builder.create(PursuerEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.9f, 2.3f).eyeHeight(1.9f)
                    .build()
    );

    public static final EntityType<BadwareEntity> BADWARE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DieOfDeath.MOD_ID, "badware"),
            EntityType.Builder.create(BadwareEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.0f, 2.38f).eyeHeight(2.15f)
                    .build()
    );

    public static final EntityType<BadwarePCEntity> BADWARE_PC = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DieOfDeath.MOD_ID,"badware_pc"),
            EntityType.Builder.create(BadwarePCEntity::new, SpawnGroup.CREATURE)
                    .dimensions(2.0f, 2.38f)
                    .build()
    );

    public static final EntityType<ArtfulEntity> ARTFUL = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DieOfDeath.MOD_ID, "artful"),
            EntityType.Builder.create(ArtfulEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.0f, 1.75f).eyeHeight(1.55f)
                    .build()
    );

    public static final EntityType<ArtfulWallEntity> ARTFUL_WALL = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DieOfDeath.MOD_ID, "artful_wall"),
            EntityType.Builder.create(ArtfulWallEntity::new, SpawnGroup.MISC)
                    .dimensions(3.0f, 2.0f)
                    .build()
    );

    public static final EntityType<ArtfulMusicBoxEntity> ARTFUL_MUSIC_BOX = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DieOfDeath.MOD_ID, "artful_music_box"),
            EntityType.Builder.create(ArtfulMusicBoxEntity::new, SpawnGroup.MISC)
                    .dimensions(0.75f, 0.75f)
                    .build()
    );

    public static final EntityType<ArtfulPuppetEntity> ARTFUL_PUPPET = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DieOfDeath.MOD_ID, "artful_puppet"),
            EntityType.Builder.create(ArtfulPuppetEntity::new, SpawnGroup.MISC)
                    .dimensions(0.6f, 1.8f)
                    .build()
    );

    public static void registerModEntities() {
        DieOfDeath.LOGGER.info("Registering Entities for " + DieOfDeath.MOD_ID);
    }
}