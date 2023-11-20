package com.TBK.better_animation_mob.client.models;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import java.util.ArrayList;
import java.util.List;

public class ReplacedHumanoidModel <T extends IAnimatable> extends ReplacedEntityModel<T> {
    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        EntityModelData data = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        if (data!=null ){
            GeoBone head = (GeoBone)this.getBone("Head");
            GeoBone body = (GeoBone)this.getBone("Body");
            GeoBone rightArm = (GeoBone)this.getBone("RightArm");
            GeoBone leftArm = (GeoBone)this.getBone("LeftArm");
            GeoBone rightLeg = (GeoBone)this.getBone("RightLeg");
            GeoBone leftLeg = (GeoBone)this.getBone("LeftLeg");
            GeoBone main = (GeoBone)this.getBone("main");
            if(data.isChild){
                float f = 1.5F/2.0F;
                float f1 = 1.0F/2.0F;
                head.setScale(f,f,f);
                head.addPosition(0.0F,-13.0F,0.0F);
                body.setScale(f1,f1,f1);
                body.addPosition(0.0F,-12.0F,0.0F);
                rightArm.setScale(f1,f1,f1);
                rightArm.addPosition(2.5F,-11.0F,0.0F);
                leftArm.setScale(f1,f1,f1);
                leftArm.addPosition(-2.5F,-11.0F,0.0F);
                rightLeg.setScale(f1,f1,f1);
                rightLeg.addPosition(1.0F,-6.0F,0.0F);
                leftLeg.setScale(f1,f1,f1);
                leftLeg.addPosition(-1.0F,-6.0F,0.0F);
            }else {
                head.setScale(1,1,1);
                body.setScale(1,1,1);
                rightArm.setScale(1,1,1);
                leftArm.setScale(1,1,1);
                rightLeg.setScale(1,1,1);
                leftLeg.setScale(1,1,1);
            }

            if(data.isSitting){
                rightLeg.addRotation(1.25664F, -0.261799F,0.0F);
                leftLeg.addRotation(1.25664F, 0.261799F,0.0F);
                main.addPosition(0.0F,1.0F,0.0F);
            }
        }
    }
}
