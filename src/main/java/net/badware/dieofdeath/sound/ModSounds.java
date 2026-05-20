package net.badware.dieofdeath.sound;

import net.badware.dieofdeath.DieOfDeath;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent IMPLEMENT_BREAK = registerSoundEvent("implement_break");
    public static final SoundEvent IMPLEMENT_PLACE = registerSoundEvent("implement_place");
    public static final SoundEvent ENTANGLED = registerSoundEvent("entangled");
    public static final SoundEvent WOOD_BOX_BREAK = registerSoundEvent("wood_box_break");

    public static final BlockSoundGroup IMPLEMENT_SOUNDS = new BlockSoundGroup(2f, 1.2f,
            ModSounds.IMPLEMENT_BREAK,
            SoundEvents.BLOCK_STONE_STEP,
            ModSounds.IMPLEMENT_PLACE,
            SoundEvents.BLOCK_STONE_HIT,
            SoundEvents.BLOCK_STONE_FALL);

    public static final BlockSoundGroup WOOD_BREAKING_FIRE = new BlockSoundGroup(
                            1.0f,
                            1.0f,
                            ModSounds.WOOD_BOX_BREAK,
                            SoundEvents.BLOCK_WOOD_STEP,
                            SoundEvents.BLOCK_WOOD_PLACE,
                            SoundEvents.BLOCK_WOOD_HIT,
                            SoundEvents.BLOCK_WOOD_FALL);

    public static final SoundEvent WAND_USE = registerSoundEvent("wand_use");
    public static final SoundEvent PURSUER_CLEAVE_SFX = registerSoundEvent("pursuer_cleave_sfx");
    public static final SoundEvent BONUSPAD_STEPPED = registerSoundEvent("bonuspad_stepped");
    public static final SoundEvent CAREPAD_HEAL = registerSoundEvent("carepad_heal");
    public static final SoundEvent BLOCKED = registerSoundEvent("blocked");
    public static final SoundEvent ENTANGLED_CRITICAL = registerSoundEvent("entangled_critical");
    public static final SoundEvent ENTANGLED_END = registerSoundEvent("entangled_end");
    public static final SoundEvent BLEEDING = registerSoundEvent("bleeding");
    public static final SoundEvent PURSUER_SWINGING = registerSoundEvent("pursuer_swinging");
    public static final SoundEvent PURSUER_CLEAVE_START = registerSoundEvent("pursuer_cleave_start");
    public static final SoundEvent PURSUER_CLEAVE_END = registerSoundEvent("pursuer_cleave_end");
    public static final SoundEvent PURSUER_SCREAM = registerSoundEvent("pursuer_scream");
    public static final SoundEvent PURSUER_SHOCKWAVE = registerSoundEvent("pursuer_shockwave");
    public static final SoundEvent STARVATION_V3 = registerSoundEvent("starvation_v3");
    public static final SoundEvent INAPPETENCE = registerSoundEvent("inappetence");
    public static final SoundEvent PURSUER_STUNNED = registerSoundEvent("pursuer_stunned");
    public static final SoundEvent A_FRIENDS_BRO = registerSoundEvent("a_friends_bro");
    public static final SoundEvent PHANTASM_HOWL = registerSoundEvent("phantasm_howl");
    public static final SoundEvent FRIGHTENED = registerSoundEvent("frightened");
    public static final SoundEvent APOCALYPSE = registerSoundEvent("apocalypse");
    public static final SoundEvent ZOMBIE_HOWL = registerSoundEvent("zombie_howl");
    public static final SoundEvent ZOMBIE_STUNNED = registerSoundEvent("zombie_stunned");
    public static final SoundEvent ZOMBIE_2_HOWL = registerSoundEvent("zombie_2_howl");
    public static final SoundEvent STALKER_HOWL = registerSoundEvent("stalker_howl");
    public static final SoundEvent STALKER_STUNNED = registerSoundEvent("stalker_stunned");
    public static final SoundEvent STALKER_CHASE_THEME = registerSoundEvent("stalker_chase_theme");
    public static final SoundEvent MEQUOT_HOWL = registerSoundEvent("mequot_howl");
    public static final SoundEvent MEQUOT_STUNNED = registerSoundEvent("mequot_stunned");
    public static final SoundEvent INSANELY = registerSoundEvent("insanely");
    public static final SoundEvent PLAY_MY_MAZE_GAME = registerSoundEvent("play_my_maze_game");
    public static final SoundEvent BEYOND_THE_BOUNDS_OF_POSSIBILITY = registerSoundEvent("beyond_the_bounds_of_possibility");
    public static final SoundEvent ACIDULUS_V2 = registerSoundEvent("acidulus_v2");
    public static final SoundEvent CLAWS_PIERCE = registerSoundEvent("claws_pierce");
    public static final SoundEvent CLAWSGUY_HOWL = registerSoundEvent("clawsguy_howl");
    public static final SoundEvent SEESAWS_HIT = registerSoundEvent("seesaws_hit");
    public static final SoundEvent THEC_CHASE_THEME = registerSoundEvent("thec_chase_theme");
    public static final SoundEvent THEC_HOWL = registerSoundEvent("thec_howl");
    public static final SoundEvent THEC_STUNNED = registerSoundEvent("thec_stunned");
    public static final SoundEvent MONETIZATION = registerSoundEvent("monetization");
    public static final SoundEvent BLING_HIT = registerSoundEvent("bling_hit");
    public static final SoundEvent BLING_STUNNED = registerSoundEvent("bling_stunned");
    public static final SoundEvent BADWARE_SWING = registerSoundEvent("badware_swing");
    public static final SoundEvent BADWARE_PC_MAKING = registerSoundEvent("badware_pc_making");
    public static final SoundEvent BADWARE_PC_EXPLODE_BY_PLAYER = registerSoundEvent("badware_pc_explode_by_player");
    public static final SoundEvent BADWARE_PC_EXPLODE = registerSoundEvent("badware_pc_explode");
    public static final SoundEvent BOLT_START = registerSoundEvent("bolt_start");
    public static final SoundEvent BOLT_MISS = registerSoundEvent("bolt_miss");
    public static final SoundEvent BOLT_HIT = registerSoundEvent("bolt_hit");
    public static final SoundEvent RIFT_TELEPORT = registerSoundEvent("rift_teleport");
    public static final SoundEvent BADWARE_STUNNED = registerSoundEvent("badware_stunned");
    public static final SoundEvent POLYMORPHIC = registerSoundEvent("polymorphic");
    public static final SoundEvent INSCRIPTUS = registerSoundEvent("inscriptus");
    public static final SoundEvent SPYWARE_STUNNED = registerSoundEvent("spyware_stunned");
    public static final SoundEvent EYESPY = registerSoundEvent("eyespy");
    public static final SoundEvent I_HEART_U = registerSoundEvent("i_heart_you");
    public static final SoundEvent DEVILWARE_STUNNED = registerSoundEvent("devilware_stunned");
    public static final SoundEvent PANDEMIC_PANDEMONIUM = registerSoundEvent("pandemic_pandemonium");
    public static final SoundEvent PURIFIED = registerSoundEvent("purified");
    public static final SoundEvent THORNS_THAT_PIERCE_BY_LEXXIEMOW = registerSoundEvent("thorns_that_pierce");
    public static final SoundEvent MADE_WITH_CARE = registerSoundEvent("made_with_care");
    public static final SoundEvent AEROWARE_SWING = registerSoundEvent("aeroware_swing");
    public static final SoundEvent AEROWARE_PC_MAKING = registerSoundEvent("aeroware_pc_making");
    public static final SoundEvent AEROWARE_PC_MAKING_2 = registerSoundEvent("aeroware_pc_making_2");
    public static final SoundEvent AEROWARE_BOLT_START = registerSoundEvent("aeroware_bolt_start");
    public static final SoundEvent AEROWARE_BOLT_START_2 = registerSoundEvent("aeroware_bolt_start_2");
    public static final SoundEvent AEROWARE_BOLT_START_3 = registerSoundEvent("aeroware_bolt_start_3");
    public static final SoundEvent AEROWARE_BOLT_HIT = registerSoundEvent("aeroware_bolt_hit");
    public static final SoundEvent AEROWARE_BOLT_HIT_2 = registerSoundEvent("aeroware_bolt_hit_2");
    public static final SoundEvent AEROWARE_BOLT_MISS = registerSoundEvent("aeroware_bolt_miss");
    public static final SoundEvent AEROWARE_RIFT_TELEPORT = registerSoundEvent("aeroware_rift_teleport");
    public static final SoundEvent AEROWARE_PC_EXPLODE = registerSoundEvent("aeroware_pc_explode");
    public static final SoundEvent AEROWARE_KILL = registerSoundEvent("aeroware_kill");
    public static final SoundEvent AEROWARE_KILL_2 = registerSoundEvent("aeroware_kill_2");
    public static final SoundEvent AEROWARE_PC_SPAWN_1 = registerSoundEvent("aeroware_pc_spawn_1");
    public static final SoundEvent AEROWARE_PC_SPAWN_2 = registerSoundEvent("aeroware_pc_spawn_2");
    public static final SoundEvent AEROWARE_PC_SPAWN_3 = registerSoundEvent("aeroware_pc_spawn_3");
    public static final SoundEvent AEROWARE_PC_SPAWN_4 = registerSoundEvent("aeroware_pc_spawn_4");
    public static final SoundEvent AEROWARE_PC_SPAWN_5 = registerSoundEvent("aeroware_pc_spawn_5");
    public static final SoundEvent AEROWARE_PC_SPAWN_6 = registerSoundEvent("aeroware_pc_spawn_6");
    public static final SoundEvent AEROWARE_PC_SPAWN_7 = registerSoundEvent("aeroware_pc_spawn_7");
    public static final SoundEvent AEROWARE_STUNNED = registerSoundEvent("aeroware_stunned");
    public static final SoundEvent AEROWARE_STUNNED_2 = registerSoundEvent("aeroware_stunned_2");
    public static final SoundEvent HEATED_ROCKS_FROM_MAR = registerSoundEvent("heated_rocks_from_mar");
    public static final SoundEvent T0_B3_3RA5ED = registerSoundEvent("to_b3_3ra5ed");
    public static final SoundEvent DIED_OF_DEATH = registerSoundEvent("died_of_death");
    public static final SoundEvent LOVEWARE_STUNNED = registerSoundEvent("loveware_stunned");
    public static final SoundEvent ARTFUL_SWING = registerSoundEvent("artful_swing");
    public static final SoundEvent ARTFUL_ABILITY_USE = registerSoundEvent("artful_ability_use");
    public static final SoundEvent ARTFUL_COPYWRITE_PLACED = registerSoundEvent("artful_copywrite_placed");
    public static final SoundEvent ARTFUL_COPYWRITE_AMBIENCE = registerSoundEvent("artful_copywrite_ambience");
    public static final SoundEvent ARTFUL_PUPPET_AMBIENCE = registerSoundEvent("artful_puppet_ambience");
    public static final SoundEvent ARTFUL_STUNNED = registerSoundEvent("artful_stunned");
    public static final SoundEvent EST_CE_TA_CARTE = registerSoundEvent("est-ce_ta_carte");
    public static final SoundEvent CONSTRUCTION = registerSoundEvent("construction");

    public static SoundEvent ETERNITY_V2 = registerSoundEvent("eternity_v2");
    public static final RegistryKey<JukeboxSong> ETERNITY_V2_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "eternity_v2"));

    public static final SoundEvent GRANDMA_BACKYARD = registerSoundEvent("grandma_backyard");
    public static final RegistryKey<JukeboxSong> GRANDMA_BACKYARD_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "grandma_backyard"));

    public static final SoundEvent TEMPLE = registerSoundEvent("temple");
    public static final RegistryKey<JukeboxSong> TEMPLE_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "temple"));

    public static final SoundEvent ROBLOX_HQ = registerSoundEvent("roblox_hq");
    public static final RegistryKey<JukeboxSong> ROBLOX_HQ_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "roblox_hq"));

    public static final SoundEvent BASEPLATE = registerSoundEvent("baseplate");
    public static final RegistryKey<JukeboxSong> BASEPLATE_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "baseplate"));

    public static final SoundEvent TUNDRA_TRENCH = registerSoundEvent("tundra_trench");
    public static final RegistryKey<JukeboxSong> TUNDRA_TRENCH_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "tundra_trench"));

    public static final SoundEvent TEAPOT_PALACE_TOUR_CALM = registerSoundEvent("teapot_palace_tour_calm");
    public static final RegistryKey<JukeboxSong> TEAPOT_PALACE_TOUR_CALM_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "teapot_palace_tour_calm"));

    public static final SoundEvent TEAPOT_PALACE_TOUR_RAIN = registerSoundEvent("teapot_palace_tour_rain");
    public static final RegistryKey<JukeboxSong> TEAPOT_PALACE_TOUR_RAIN_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "teapot_palace_tour_rain"));

    public static final SoundEvent TEAPOT_PALACE_TOUR_LMS = registerSoundEvent("teapot_palace_tour_lms");
    public static final RegistryKey<JukeboxSong> TEAPOT_PALACE_TOUR_LMS_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "teapot_palace_tour_lms"));

    public static final SoundEvent OMEGAS_FINAL_STAND = registerSoundEvent("omegas_final_stand");
    public static final RegistryKey<JukeboxSong> OMEGAS_FINAL_STAND_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "omegas_final_stand"));

    public static final SoundEvent CARELESS = registerSoundEvent("careless");
    public static final RegistryKey<JukeboxSong> CARELESS_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "careless"));

    public static final SoundEvent VIGILANTE_SHOOTDOWN = registerSoundEvent("vigilante_shootdown");
    public static final RegistryKey<JukeboxSong> VIGILANTE_SHOOTDOWN_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "vigilante_shootdown"));

    public static final SoundEvent SHOWTIME = registerSoundEvent("showtime");
    public static final RegistryKey<JukeboxSong> SHOWTIME_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "showtime"));

    public static final SoundEvent ETERNITY = registerSoundEvent("eternity");
    public static final RegistryKey<JukeboxSong> ETERNITY_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "eternity"));

    public static final SoundEvent Y2K = registerSoundEvent("y2k");
    public static final RegistryKey<JukeboxSong> Y2K_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "y2k"));

    public static final SoundEvent ONE_BOUNCE = registerSoundEvent("one_bounce");
    public static final RegistryKey<JukeboxSong> ONE_BOUNCE_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "one_bounce"));

    public static final SoundEvent VOYAGE_TO = registerSoundEvent("voyage_to");
    public static final RegistryKey<JukeboxSong> VOYAGE_TO_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "voyage_to"));


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(DieOfDeath.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        DieOfDeath.LOGGER.info("Registering Mod Sounds for " + DieOfDeath.MOD_ID);
    }
}
