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
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedWolfModel<T extends ReplacedWolf<Wolf>> extends ReplacedQuatrupleModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID, "geo/wolf.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        Wolf wolf =((Wolf)getCurrentEntity().get());
        if(wolf.isTame()){
            return new ResourceLocation("textures/entity/wolf/wolf_tame.png");
        }
        return wolf.isAngry() ? new ResourceLocation("textures/entity/wolf/wolf_angry.png") :  new ResourceLocation("textures/entity/wolf/wolf.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/wolf.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        GeoBone main = (GeoBone)this.getBone("main");
        GeoBone realHead = (GeoBone)this.getBone("Head");
        GeoBone tail= (GeoBone) getAnimationProcessor().getBone("tail");
        GeoBone body = (GeoBone)this.getBone("body");
        GeoBone upperBody= (GeoBone) getAnimationProcessor().getBone("mane");
        Wolf wolf = (Wolf) getCurrentEntity().get();
        float partialTick=animationEvent.getPartialTick();
        float limbSwing=animationEvent.getLimbSwing();
        float limbSwingAmount=animationEvent.getLimbSwingAmount();
        if(!wolf.isInSittingPose()){
            this.resetMain(main);
        }
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        if(!wolf.isInSittingPose()){
            if (wolf.isAngry()) {
                tail.setRotationY(0.0F);
            } else {
                tail.setRotationY(Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
            }

            realHead.addRotationZ((wolf.getHeadRollAngle(partialTick) + wolf.getBodyRollAngle(partialTick, 0.0F)));
            upperBody.addRotationZ(wolf.getBodyRollAngle(partialTick, -0.08F));
            body.addRotationZ(wolf.getBodyRollAngle(partialTick, -0.16F));
            tail.addRotationZ(wolf.getBodyRollAngle(partialTick, -0.2F));

            tail.addRotationX(-wolf.getTailAngle());
        }
    }
}
