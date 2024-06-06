package com.TBK.better_animation_mob.client.models.compi.myf;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedEntityModel;
import lykrast.meetyourfight.MeetYourFight;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

@OnlyIn(Dist.CLIENT)
public class ReplacedSwampJawModel<T extends IAnimatable> extends ReplacedEntityModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/myf/swampjaw.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(MeetYourFight.MODID,"textures/entity/swampjaw.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/myf/swampjaw.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
    }
}
