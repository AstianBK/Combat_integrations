package com.TBK.better_animation_mob.client.models.piglin;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedHumanoidModel;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;

public class ReplacedZombiePiglinModel<T extends IAnimatable> extends ReplacedHumanoidModel<T> {

    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID, "geo/piglin.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/entity/piglin/zombified_piglin.png");
    }
    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/zombie_piglin.animation.json");
    }


}
