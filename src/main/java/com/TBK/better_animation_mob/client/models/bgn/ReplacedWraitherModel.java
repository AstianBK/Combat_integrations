package com.TBK.better_animation_mob.client.models.bgn;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.skeleton.ReplacedSkeletonModel;
import com.izofar.bygonenether.BygoneNetherMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;

public class ReplacedWraitherModel<T extends IAnimatable> extends ReplacedSkeletonModel<T> {
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/wither/wraither.png");
    }

    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/bgn/wraither.geo.json");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/skeleton.animation.json");
    }
}
