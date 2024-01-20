package com.TBK.better_animation_mob.client.models.wolf;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedQuatrupleModel;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedWolf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Ravager;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class ReplacedWolfModel<T extends ReplacedWolf<Wolf>> extends ReplacedQuatrupleModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID, "geo/wolf.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return ((Wolf)this.getCurrentEntity().get()).isAngry() ? new ResourceLocation("textures/entity/wolf/wolf_angry.png") :  new ResourceLocation("textures/entity/wolf/wolf.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/wolf.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        IBone tail=this.getAnimationProcessor().getBone("tail");
        Wolf wolf = (Wolf) this.getCurrentEntity().get();
        tail.setRotationX(wolf.getTailAngle());
    }
}
