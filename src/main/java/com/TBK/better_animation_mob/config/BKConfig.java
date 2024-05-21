package com.TBK.better_animation_mob.config;

import com.TBK.better_animation_mob.BetterAnimationMob;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = BetterAnimationMob.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BKConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue VANILLA_MOBS_ANIMATIONS = BUILDER
            .comment("enable chainmail block sound")
            .define("vanilla_mobs_animations",true);

    private static final ForgeConfigSpec.BooleanValue SAVAGE_AND_RAVAGE_ANIMATIONS = BUILDER
            .comment("enable chainmail block sound")
            .define("savager_and_ravage_animations",true);

    private static final ForgeConfigSpec.BooleanValue BYGONE_NETHER_ANIMATIONS = BUILDER
            .comment("enable chainmail block sound")
            .define("bygone_nether_animations",true);

    private static final ForgeConfigSpec.BooleanValue DUNGEONS_MOBS_ANIMATIONS = BUILDER
            .comment("enable chainmail block sound")
            .define("dungeons_mobs_animations",true);

    private static final ForgeConfigSpec.BooleanValue QUARK_ANIMATIONS = BUILDER
            .comment("enable chainmail block sound")
            .define("quark_animations",true);

    private static final ForgeConfigSpec.BooleanValue GUARD_VILLAGERS_ANIMATIONS = BUILDER
            .comment("enable chainmail block sound")
            .define("guard_villagers_animations",true);

    private static final ForgeConfigSpec.BooleanValue MEET_YOUR_FIGHT_ANIMATIONS = BUILDER
            .comment("enable chainmail block sound")
            .define("meet_your_fight_animations",true);
    public static final ForgeConfigSpec SPEC = BUILDER.build();
    public static boolean vanillaMobsAnimations;
    public static boolean savageAndRavageAnimations;
    public static boolean bygoneNetherAnimations;
    public static boolean dungeonsMobsAnimations;
    public static boolean quarkAnimations;
    public static boolean guardVillagersAnimations;
    public static boolean meetYourFightAnimations;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        vanillaMobsAnimations = VANILLA_MOBS_ANIMATIONS.get();
        savageAndRavageAnimations = SAVAGE_AND_RAVAGE_ANIMATIONS.get();
        bygoneNetherAnimations = BYGONE_NETHER_ANIMATIONS.get();
        dungeonsMobsAnimations = DUNGEONS_MOBS_ANIMATIONS.get();
        quarkAnimations = QUARK_ANIMATIONS.get();
        guardVillagersAnimations = GUARD_VILLAGERS_ANIMATIONS.get();
        meetYourFightAnimations = MEET_YOUR_FIGHT_ANIMATIONS.get();

    }
}
