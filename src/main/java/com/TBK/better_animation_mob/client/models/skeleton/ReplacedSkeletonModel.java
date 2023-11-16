package com.TBK.better_animation_mob.client.models.skeleton;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedEntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Stray;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedSkeletonModel<T extends IAnimatable> extends ReplacedEntityModel<T> {

    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/skeleton.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        if(this.getCurrentEntity() instanceof Stray){
            return new ResourceLocation("textures/entity/skeleton/stray.png");
        }else {
            return new ResourceLocation("textures/entity/skeleton/skeleton.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/skeleton.animation.json");
    }

    @Override
    protected Iterable<GeoBone> headParts() {
        return null;
    }

    @Override
    protected Iterable<GeoBone> bodyParts() {
        return null;
    }
}
