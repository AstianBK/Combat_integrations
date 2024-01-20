package com.TBK.better_animation_mob.client.models.spider;

import com.TBK.better_animation_mob.BetterAnimationMob;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;

public class ReplacedCaveSpiderModel<T extends IAnimatable>extends ReplacedSpiderModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/spider.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/entity/spider/cave_spider.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/spider.animation.json");
    }
}
