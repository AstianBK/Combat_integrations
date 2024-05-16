package com.TBK.better_animation_mob;

import com.TBK.better_animation_mob.client.renderers.compi.bgn.*;
import com.TBK.better_animation_mob.client.renderers.compi.dm.*;
import com.TBK.better_animation_mob.client.renderers.compi.qrk.ReplacedForgottenRenderer;
import com.TBK.better_animation_mob.client.renderers.compi.qrk.ReplacedFoxhoundRenderer;
import com.TBK.better_animation_mob.client.renderers.compi.qrk.ReplacedWraithRenderer;
import com.TBK.better_animation_mob.client.renderers.enderman.ReplacedEnderManRenderer;
import com.TBK.better_animation_mob.client.renderers.piglin.ReplacedZombiePiglinRenderer;
import com.TBK.better_animation_mob.client.renderers.spider.ReplacedCaveSpiderRenderer;
import com.TBK.better_animation_mob.client.renderers.spider.ReplacedSpiderRenderer;
import com.TBK.better_animation_mob.client.renderers.boss.ReplacedWardenRenderer;
import com.TBK.better_animation_mob.client.renderers.golem.ReplacedIronGolemRenderer;
import com.TBK.better_animation_mob.client.renderers.illager.*;
import com.TBK.better_animation_mob.client.renderers.piglin.ReplacedPiglinBruteRenderer;
import com.TBK.better_animation_mob.client.renderers.piglin.ReplacedPiglinRenderer;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedStrayRenderer;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedWitherSkeletonRenderer;
import com.TBK.better_animation_mob.client.renderers.wolf.ReplacedWolfRenderer;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedDrownedRenderer;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedHuskRenderer;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedZombieRenderer;
import com.TBK.better_animation_mob.client.util.Compati;
import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.dm.ReplacedZombieFungusThrower;
import com.TBK.better_animation_mob.server.modbusevent.network.PacketHandler;
import com.TBK.better_animation_mob.server.modbusevent.provider.PatchProvider;
import com.TBK.better_animation_mob.server.modbusevent.register.BkEffect;
import com.TBK.better_animation_mob.server.modbusevent.register.BkEntityTypes;
import com.TBK.better_animation_mob.server.modbusevent.register.BkItems;
import com.google.common.collect.Maps;
import com.infamous.dungeons_mobs.DungeonsMobs;
import com.izofar.bygonenether.init.ModEntityTypes;
import com.teamabnormals.savage_and_ravage.core.registry.SREntityTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;
import vazkii.quark.base.Quark;
import vazkii.quark.base.proxy.CommonProxy;
import vazkii.quark.content.mobs.entity.Forgotten;
import vazkii.quark.content.mobs.module.ForgottenModule;
import vazkii.quark.content.mobs.module.FoxhoundModule;
import vazkii.quark.content.mobs.module.WraithModule;

import java.util.Map;

@Mod(BetterAnimationMob.MODID)
public class BetterAnimationMob {
    public static final String MODID = "better_animation_mob";
    private static final Map<EntityType<?>, EntityRendererProvider<?>> PROVIDERS = Maps.newHashMap();


    public BetterAnimationMob() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();
        BkEntityTypes.register(modEventBus);
        BkEffect.EFFECT.register(modEventBus);
        BkItems.ITEMS.register(modEventBus);
        PacketHandler.registerMessages();
        modEventBus.register(this);
        modEventBus.addListener(this::Common);
        modEventBus.addListener(Capabilities::registerCapabilities);
        modEventBus.addListener(this::registerRenderer);
    }
    private void Common(final FMLCommonSetupEvent event) {
        event.enqueueWork(PatchProvider::registerEntityPatches);
    }
    public void registerRenderer(final FMLCommonSetupEvent event) {
        //Zombies
        register(EntityType.ZOMBIE, ReplacedZombieRenderer::new);
        register(EntityType.DROWNED, ReplacedDrownedRenderer::new);
        register(EntityType.HUSK, ReplacedHuskRenderer::new);

        //Skeletons
        register(EntityType.SKELETON, ReplacedSkeletonRenderer::new);
        register(EntityType.STRAY, ReplacedStrayRenderer::new);
        register(EntityType.WITHER_SKELETON, ReplacedWitherSkeletonRenderer::new);

        //Raiders
        register(EntityType.VINDICATOR, ReplacedVindicatorRenderer::new);
        register(EntityType.PILLAGER, ReplacedPillagerRenderer::new);
        register(EntityType.RAVAGER, ReplacedRavagerRenderer::new);
        register(EntityType.EVOKER, ReplacedEvokerRenderer::new);

        //Piglins
        register(EntityType.PIGLIN_BRUTE, ReplacedPiglinBruteRenderer::new);
        register(EntityType.PIGLIN, ReplacedPiglinRenderer::new);
        register(EntityType.ZOMBIFIED_PIGLIN, ReplacedZombiePiglinRenderer::new);

        //Spider
        register(EntityType.SPIDER, ReplacedSpiderRenderer::new);
        register(EntityType.CAVE_SPIDER, ReplacedCaveSpiderRenderer::new);

        //Boss
        register(EntityType.WARDEN, ReplacedWardenRenderer::new);

        //Golems
        register(EntityType.IRON_GOLEM, ReplacedIronGolemRenderer::new);

        //EnderMan
        register(EntityType.ENDERMAN, ReplacedEnderManRenderer::new);

        //Wolf
        register(EntityType.WOLF, ReplacedWolfRenderer::new);

        if(isLoaded(Compati.SAVAGE_AND_RAVEGER)){
            register(SREntityTypes.ICEOLOGER.get(), ReplacedIceologerRenderer::new);
            register(SREntityTypes.EXECUTIONER.get(), ReplacedExecutionerRenderer::new);
            register(SREntityTypes.TRICKSTER.get(), ReplacedTricksterRenderer::new);
            register(SREntityTypes.GRIEFER.get(), ReplacedGrieferRenderer::new);
        }
        if(isLoaded(Compati.BYGONE_NETHER)){
            register(ModEntityTypes.CORPOR.get(), ReplacedCorporRenderer::new);
            register(ModEntityTypes.PIGLIN_HUNTER.get(), ReplacedPiglinHunterRenderer::new);
            register(ModEntityTypes.PIGLIN_PRISONER.get(), ReplacedPiglinPrisonerRenderer::new);
            register(ModEntityTypes.WARPED_ENDERMAN.get(), ReplacedWarpedEnderManRenderer::new);
            register(ModEntityTypes.WEX.get(), ReplacedWexRenderer::new);
            register(ModEntityTypes.WITHER_SKELETON_KNIGHT.get(), ReplacedWitherSkeletonKnightRenderer::new);
            register(ModEntityTypes.WRAITHER.get(), ReplacedWraitherRenderer::new);
        }

        if(isLoaded(Compati.DUNGEONS_MOBS)){
            register(com.infamous.dungeons_mobs.mod.ModEntityTypes.FROZEN_ZOMBIE.get(), ReplacedFrozenZombieRenderer::new);
            register(com.infamous.dungeons_mobs.mod.ModEntityTypes.JUNGLE_ZOMBIE.get(), ReplacedJungleZombieRenderer::new);
            register(com.infamous.dungeons_mobs.mod.ModEntityTypes.MOSSY_SKELETON.get(), ReplacedMossySkeletonRenderer::new);
            register(com.infamous.dungeons_mobs.mod.ModEntityTypes.SUNKEN_SKELETON.get(), ReplacedSunkenSkeletonRenderer::new);
            register(com.infamous.dungeons_mobs.mod.ModEntityTypes.ZOMBIFIED_FUNGUS_THROWER.get(), ReplacedZombiefierFungusThrowerRenderer::new);
            register(com.infamous.dungeons_mobs.mod.ModEntityTypes.FUNGUS_THROWER.get(), ReplacedFungusThrowerRenderer::new);
        }
        if(isLoaded(Compati.QUARK)){
            register(FoxhoundModule.foxhoundType, ReplacedFoxhoundRenderer::new);
            register(ForgottenModule.forgottenType, ReplacedForgottenRenderer::new);
            register(WraithModule.wraithType, ReplacedWraithRenderer::new);
        }
    }
    
    public static void register(EntityType<?> entityType, EntityRendererProvider<?> renderer){
        PROVIDERS.put(entityType,renderer);
    }
    public static Map<EntityType<?>, EntityRendererProvider<?>> getProviders(){
        return PROVIDERS;
    }

    public static boolean isLoaded(Compati name){
        return ModList.get().isLoaded(name.id);
    }
}
