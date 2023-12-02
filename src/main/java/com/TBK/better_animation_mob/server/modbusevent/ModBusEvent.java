package com.TBK.better_animation_mob.server.modbusevent;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.renderers.illager.*;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedStrayRenderer;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedWitherSkeletonRenderer;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedDrownedRenderer;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedHuskRenderer;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedZombieRenderer;
import com.TBK.better_animation_mob.client.util.Compati;
import com.teamabnormals.savage_and_ravage.core.registry.SREntityTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterAnimationMob.MODID, bus = Mod.EventBusSubscriber.Bus.MOD,value=Dist.CLIENT)
public class ModBusEvent {

}
