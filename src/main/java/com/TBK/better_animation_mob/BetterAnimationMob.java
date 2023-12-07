package com.TBK.better_animation_mob;

import com.TBK.better_animation_mob.client.renderers.illager.*;
import com.TBK.better_animation_mob.client.renderers.piglin.ReplacedPiglinBruteRenderer;
import com.TBK.better_animation_mob.client.renderers.piglin.ReplacedPiglinRenderer;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedStrayRenderer;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedWitherSkeletonRenderer;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedDrownedRenderer;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedHuskRenderer;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedZombieRenderer;
import com.TBK.better_animation_mob.client.util.Compati;
import com.TBK.better_animation_mob.server.modbusevent.register.BkEffect;
import com.TBK.better_animation_mob.server.modbusevent.register.BkEntityTypes;
import com.TBK.better_animation_mob.server.modbusevent.register.BkItems;
import com.teamabnormals.savage_and_ravage.core.registry.SREntityTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(BetterAnimationMob.MODID)
public class BetterAnimationMob {
    public static final String MODID = "better_animation_mob";
    public static boolean evokerIsPresent = true;
    public BetterAnimationMob() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();
        BkEntityTypes.register(modEventBus);
        BkEffect.EFFECT.register(modEventBus);
        BkItems.ITEMS.register(modEventBus);
        modEventBus.register(this);
        modEventBus.addListener(this::registerRenderer);
    }
    public void registerRenderer(FMLCommonSetupEvent event) {
        //Zombies
        EntityRenderers.register(EntityType.ZOMBIE, ReplacedZombieRenderer::new);
        EntityRenderers.register(EntityType.DROWNED, ReplacedDrownedRenderer::new);
        EntityRenderers.register(EntityType.HUSK, ReplacedHuskRenderer::new);

        //Skeletons
        EntityRenderers.register(EntityType.SKELETON, ReplacedSkeletonRenderer::new);
        EntityRenderers.register(EntityType.STRAY, ReplacedStrayRenderer::new);
        EntityRenderers.register(EntityType.WITHER_SKELETON, ReplacedWitherSkeletonRenderer::new);

        //Raiders
        EntityRenderers.register(EntityType.VINDICATOR, ReplacedVindicatorRenderer::new);
        EntityRenderers.register(EntityType.PILLAGER, ReplacedPillagerRenderer::new);
        EntityRenderers.register(EntityType.RAVAGER, ReplacedRavagerRenderer::new);
        EntityRenderers.register(EntityType.EVOKER, ReplacedEvokerRenderer::new);

        //Piglins
        EntityRenderers.register(EntityType.PIGLIN_BRUTE, ReplacedPiglinBruteRenderer::new);
        EntityRenderers.register(EntityType.PIGLIN, ReplacedPiglinRenderer::new);

        if(isLoaded(Compati.SAVAGE_AND_RAVEGER)){
            EntityRenderers.register(SREntityTypes.EXECUTIONER.get(), ReplacedExecutionerRenderer::new);
            EntityRenderers.register(SREntityTypes.TRICKSTER.get(), ReplacedTricksterRenderer::new);
            EntityRenderers.register(SREntityTypes.GRIEFER.get(), ReplacedGrieferRenderer::new);
        }
    }



    public static boolean isLoaded(Compati name){
        return ModList.get().isLoaded(name.id);
    }
}
