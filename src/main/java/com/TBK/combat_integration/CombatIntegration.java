package com.TBK.combat_integration;

import com.TBK.combat_integration.client.renderers.compi.bgn.*;
import com.TBK.combat_integration.client.renderers.compi.dm.*;
import com.TBK.combat_integration.client.renderers.compi.gv.ReplacedGuardVillagerRenderer;
import com.TBK.combat_integration.client.renderers.compi.myf.ReplacedBellringerRenderer;
import com.TBK.combat_integration.client.renderers.compi.myf.ReplacedFortunaRenderer;
import com.TBK.combat_integration.client.renderers.compi.myf.ReplacedRosalyneRenderer;
import com.TBK.combat_integration.client.renderers.compi.myf.ReplacedSwampJawRenderer;
import com.TBK.combat_integration.client.renderers.compi.qrk.ReplacedForgottenRenderer;
import com.TBK.combat_integration.client.renderers.compi.qrk.ReplacedFoxhoundRenderer;
import com.TBK.combat_integration.client.renderers.compi.qrk.ReplacedWraithRenderer;
import com.TBK.combat_integration.client.renderers.enderman.ReplacedEnderManRenderer;
import com.TBK.combat_integration.client.renderers.piglin.ReplacedZombiePiglinRenderer;
import com.TBK.combat_integration.client.renderers.spider.ReplacedCaveSpiderRenderer;
import com.TBK.combat_integration.client.renderers.spider.ReplacedSpiderRenderer;
import com.TBK.combat_integration.client.renderers.boss.ReplacedWardenRenderer;
import com.TBK.combat_integration.client.renderers.golem.ReplacedIronGolemRenderer;
import com.TBK.combat_integration.client.renderers.illager.*;
import com.TBK.combat_integration.client.renderers.piglin.ReplacedPiglinBruteRenderer;
import com.TBK.combat_integration.client.renderers.piglin.ReplacedPiglinRenderer;
import com.TBK.combat_integration.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.combat_integration.client.renderers.skeleton.ReplacedStrayRenderer;
import com.TBK.combat_integration.client.renderers.skeleton.ReplacedWitherSkeletonRenderer;
import com.TBK.combat_integration.client.renderers.wolf.ReplacedWolfRenderer;
import com.TBK.combat_integration.client.renderers.zombie.ReplacedDrownedRenderer;
import com.TBK.combat_integration.client.renderers.zombie.ReplacedHuskRenderer;
import com.TBK.combat_integration.client.renderers.zombie.ReplacedZombieRenderer;
import com.TBK.combat_integration.client.util.Compati;
import com.TBK.combat_integration.config.BKConfig;
import com.TBK.combat_integration.server.modbusevent.cap.Capabilities;
import com.TBK.combat_integration.server.modbusevent.network.PacketHandler;
import com.TBK.combat_integration.server.modbusevent.provider.PatchProvider;
import com.TBK.combat_integration.server.modbusevent.register.BkEffect;
import com.TBK.combat_integration.server.modbusevent.register.BkEntityTypes;
import com.TBK.combat_integration.server.modbusevent.register.BkItems;
import com.google.common.collect.Maps;
import com.izofar.bygonenether.init.ModEntityTypes;
import com.teamabnormals.savage_and_ravage.core.registry.SREntityTypes;
import lykrast.meetyourfight.registry.ModEntities;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;
import tallestegg.guardvillagers.GuardEntityType;
import vazkii.quark.content.mobs.module.ForgottenModule;
import vazkii.quark.content.mobs.module.FoxhoundModule;
import vazkii.quark.content.mobs.module.WraithModule;

import java.util.Map;

@Mod(CombatIntegration.MODID)
public class CombatIntegration {
    public static final String MODID = "combat_integration";
    private static final Map<EntityType<?>, EntityRendererProvider<?>> PROVIDERS = Maps.newHashMap();


    public CombatIntegration() {
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
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BKConfig.SPEC);

    }
    private void Common(final FMLCommonSetupEvent event) {
        event.enqueueWork(PatchProvider::registerEntityPatches);
    }
    public void registerRenderer(final FMLCommonSetupEvent event) {
        if(BKConfig.vanillaMobsAnimations){
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
        }

        if(BKConfig.savageAndRavageAnimations){
            if(isLoaded(Compati.SAVAGE_AND_RAVAGE)){
                register(SREntityTypes.ICEOLOGER.get(), ReplacedIceologerRenderer::new);
                register(SREntityTypes.EXECUTIONER.get(), ReplacedExecutionerRenderer::new);
                register(SREntityTypes.TRICKSTER.get(), ReplacedTricksterRenderer::new);
                register(SREntityTypes.GRIEFER.get(), ReplacedGrieferRenderer::new);
            }
        }

        if(BKConfig.bygoneNetherAnimations){
            if(isLoaded(Compati.BYGONE_NETHER)){
                register(ModEntityTypes.CORPOR.get(), ReplacedCorporRenderer::new);
                register(ModEntityTypes.PIGLIN_HUNTER.get(), ReplacedPiglinHunterRenderer::new);
                register(ModEntityTypes.PIGLIN_PRISONER.get(), ReplacedPiglinPrisonerRenderer::new);
                register(ModEntityTypes.WARPED_ENDERMAN.get(), ReplacedWarpedEnderManRenderer::new);
                register(ModEntityTypes.WEX.get(), ReplacedWexRenderer::new);
                register(ModEntityTypes.WITHER_SKELETON_KNIGHT.get(), ReplacedWitherSkeletonKnightRenderer::new);
                register(ModEntityTypes.WRAITHER.get(), ReplacedWraitherRenderer::new);
            }
        }

        if(BKConfig.dungeonsMobsAnimations){
            if(isLoaded(Compati.DUNGEONS_MOBS)){
                register(com.infamous.dungeons_mobs.mod.ModEntityTypes.FROZEN_ZOMBIE.get(), ReplacedFrozenZombieRenderer::new);
                register(com.infamous.dungeons_mobs.mod.ModEntityTypes.JUNGLE_ZOMBIE.get(), ReplacedJungleZombieRenderer::new);
                register(com.infamous.dungeons_mobs.mod.ModEntityTypes.MOSSY_SKELETON.get(), ReplacedMossySkeletonRenderer::new);
                register(com.infamous.dungeons_mobs.mod.ModEntityTypes.SUNKEN_SKELETON.get(), ReplacedSunkenSkeletonRenderer::new);
                register(com.infamous.dungeons_mobs.mod.ModEntityTypes.ZOMBIFIED_FUNGUS_THROWER.get(), ReplacedZombiefierFungusThrowerRenderer::new);
                register(com.infamous.dungeons_mobs.mod.ModEntityTypes.FUNGUS_THROWER.get(), ReplacedFungusThrowerRenderer::new);
            }
        }

        if(BKConfig.quarkAnimations){
            if(isLoaded(Compati.QUARK)){
                register(FoxhoundModule.foxhoundType, ReplacedFoxhoundRenderer::new);
                register(ForgottenModule.forgottenType, ReplacedForgottenRenderer::new);
                register(WraithModule.wraithType, ReplacedWraithRenderer::new);
            }
        }

        if(BKConfig.guardVillagersAnimations){
            if(isLoaded(Compati.GUARD_VILLAGERS)){
                register(GuardEntityType.GUARD.get(), ReplacedGuardVillagerRenderer::new);
            }
        }

        if(BKConfig.meetYourFightAnimations){
            if(isLoaded(Compati.MEET_YOUR_FIGHT)){
                register(ModEntities.SWAMPJAW.get(), ReplacedSwampJawRenderer::new);
                register(ModEntities.BELLRINGER.get(), ReplacedBellringerRenderer::new);
                register(ModEntities.DAME_FORTUNA.get(), ReplacedFortunaRenderer::new);
                register(ModEntities.ROSALYNE.get(), ReplacedRosalyneRenderer::new);
            }
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
