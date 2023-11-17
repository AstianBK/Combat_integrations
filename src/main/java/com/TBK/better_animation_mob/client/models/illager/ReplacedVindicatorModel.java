package com.TBK.better_animation_mob.client.models.illager;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedEntityModel;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedVindicatorModel<T extends IAnimatable> extends ReplacedEntityModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID, "geo/vindicator.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/entity/illager/vindicator.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/vindicator.animation.json");
    }

}
