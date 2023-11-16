package com.TBK.better_animation_mob;

import com.TBK.better_animation_mob.client.renderers.illager.ReplacedExecutionerRenderer;
import com.TBK.better_animation_mob.client.renderers.illager.ReplacedPillagerRenderer;
import com.TBK.better_animation_mob.client.renderers.illager.ReplacedVindicatorRenderer;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedSkeletonRenderer;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
    public BetterAnimationMob() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();
        BkEntityTypes.register(modEventBus);
        BkEffect.EFFECT.register(modEventBus);
        BkItems.ITEMS.register(modEventBus);
        modEventBus.addListener(this::registerRenderer);

        modEventBus.register(this);
    }
    @OnlyIn(Dist.CLIENT)
    private void registerRenderer(FMLCommonSetupEvent event){
        EntityRenderers.register(EntityType.ZOMBIE, ReplacedZombieRenderer::new);
        EntityRenderers.register(EntityType.DROWNED, ReplacedDrownedRenderer::new);
        EntityRenderers.register(EntityType.HUSK, ReplacedHuskRenderer::new);
        EntityRenderers.register(EntityType.SKELETON, ReplacedSkeletonRenderer::new);
        EntityRenderers.register(EntityType.STRAY, ReplacedSkeletonRenderer::new);
        EntityRenderers.register(EntityType.VINDICATOR, ReplacedVindicatorRenderer::new);
        EntityRenderers.register(EntityType.PILLAGER, ReplacedPillagerRenderer::new);
        if(this.isLoaded(Compati.SAVAGE_AND_RAVEGER)){
            EntityRenderers.register(SREntityTypes.EXECUTIONER.get(), ReplacedExecutionerRenderer::new);
        }
    }


    public boolean isLoaded(Compati name){
        return ModList.get().isLoaded(name.id);
    }
}
