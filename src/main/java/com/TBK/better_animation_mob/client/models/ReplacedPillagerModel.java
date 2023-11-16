package com.TBK.better_animation_mob.client.models;

import com.TBK.better_animation_mob.BetterAnimationMob;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;

public class ReplacedPillagerModel <T extends IAnimatable> extends ReplacedEntityModel<T>{
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/pillager.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/entity/illager/pillager.png");

    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID, "animations/pillager.animation.json");
    }
}
