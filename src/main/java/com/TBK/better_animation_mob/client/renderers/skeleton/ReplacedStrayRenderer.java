package com.TBK.better_animation_mob.client.renderers.skeleton;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.layers.StrayGeckoLayer;
import com.TBK.better_animation_mob.client.models.skeleton.ReplacedSkeletonModel;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class ReplacedStrayRenderer<T extends AbstractSkeleton,P extends ReplacedSkeleton> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedStrayRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedSkeletonModel<>(), (P) new ReplacedSkeleton(),new ResourceLocation("textures/entity/skeleton/stray.png"));
        this.addLayer(new StrayGeckoLayer<>(this,this.getGeoModelProvider(),new ResourceLocation("textures/entity/skeleton/stray.png"),new ResourceLocation(BetterAnimationMob.MODID,"geo/skeleton.geo.json")));
    }
}
