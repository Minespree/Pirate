package net.minespree.pirate.cosmetics;

import lombok.Getter;
import net.minespree.pirate.cosmetics.games.blockwars.ExplosionCoreDestructionCosmetic;
import net.minespree.pirate.cosmetics.games.skywars.cages.*;
import net.minespree.pirate.cosmetics.games.thimble.markers.HeadMarkerCosmetic;
import net.minespree.pirate.cosmetics.games.thimble.successful.*;
import net.minespree.pirate.cosmetics.games.thimble.trails.MagitCritTrailCosmetic;
import net.minespree.pirate.cosmetics.types.gadgets.*;
import net.minespree.pirate.cosmetics.types.gadgets.fishslapper.FishSlapperGadget;
import net.minespree.pirate.cosmetics.types.superhats.*;
import net.minespree.pirate.cosmetics.types.wineffects.HulkSmashWinEffect;
import net.minespree.pirate.cosmetics.types.wineffects.ToTheHeavensWinEffect;

public enum CosmeticInstance {

    FISH_SLAPPER_GADGET(new FishSlapperGadget()),
    FIREWORK_BOW_GADGET(new FireworkBowGadget()),
    PAINTBALL_GUN_GADGET(new PaintballGadget()),
    PRAISE_PINEAPPLE_GADGET(new PraisePineappleGadget()),
    ROCKET_JUMP_GADGET(new RocketJumpGadget()),
    SADDLE_GADGET(new SaddleGadget()),
    TNT_THROWER_GADGET(new TNTThrowerGadget()),

    ALPHA_SUPER_HAT(new AlphaSuperHat()),
    BETA_SUPER_HAT(new BetaSuperHat()),
    BREAST_CANCER_SUPER_HAT(new BreastCancerSuperHat()),
    DIAMOND_SUPER_HAT(new DiamondSuperHat()),
    FLOWER_SUPER_HAT(new FlowerSuperHat()),
    GOLD_SUPER_HAT(new GoldSuperHat()),
    IRON_SUPER_HAT(new IronSuperHat()),
    LEAF_SUPER_HAT(new LeafSuperHat()),
    RAINBOW_SUPER_HAT(new RainbowSuperHat()),

    HULK_SMASH_WIN_EFFECT(new HulkSmashWinEffect()),
    TO_THE_HEAVENS_WIN_EFFECT(new ToTheHeavensWinEffect()),

    DEFAULT_CAGE(new DefaultCageCosmetic()),
    HALLOWEEN_17_CAGE(new Halloween17CageCosmetic()),
    DUCK_CAGE(new DuckCageCosmetic()),
    TREE_CAGE(new TreeCageCosmetic()),

    SKULL_MARKER(new HeadMarkerCosmetic()),

    EXPLOSION_LAND(new ExplosionLandCosmetic()),
    CHICKEN_LAND(new ChickenLandCosmetic()),
    YAY_LAND(new YayLandCosmetic()),
    SWIM_LAND(new SwimLandCosmetic()),
    SAD_LAND(new SadLandCosmetic()),

    MAGIC_CRIT_JUMP_TRAIL(new MagitCritTrailCosmetic()),

    EXPLOSION_CORE_DESTRUCTION(new ExplosionCoreDestructionCosmetic()),

    ;

    @Getter
    Cosmetic cosmetic;

    CosmeticInstance(Cosmetic cosmetic) {
        this.cosmetic = cosmetic;
    }

}
