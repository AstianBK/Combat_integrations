package com.TBK.better_animation_mob.client.models;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class ReplacedHumanoidModel <T extends IAnimatable> extends ReplacedEntityModel<T> {
    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        EntityModelData data = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        GeoBone head = (GeoBone)this.getBone("Head");
        GeoBone body = (GeoBone)this.getBone("Body");
        GeoBone rightArm = (GeoBone)this.getBone("RightArm");
        GeoBone leftArm = (GeoBone)this.getBone("LeftArm");
        GeoBone rightLeg = (GeoBone)this.getBone("RightLeg");
        GeoBone leftLeg = (GeoBone)this.getBone("LeftLeg");
        GeoBone main = this.getModel(this.getModelResource(animatable)).getBone("main").isPresent() ? this.getModel(this.getModelResource(animatable)).getBone("main").get() : null;
        if(main!=null){
            this.resetMain(main);
        }
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        if (data!=null ){
            if(data.isChild){
                float f = 1.5F/2.0F;
                float f1 = 1.0F/2.0F;
                head.setScale(f,f,f);
                head.addPosition(0.0F,-12,0.0F);
                body.setScale(f1,f1,f1);
                body.addPosition(0.0F,-12,0.0F);
                rightArm.setScale(f1,f1,f1);
                rightArm.addPosition(2.3F,-11f,0.0F);
                leftArm.setScale(f1,f1,f1);
                leftArm.addPosition(-2.3F,-11f,0.0F);
                rightLeg.setScale(f1,f1,f1);
                rightLeg.addPosition(1F,-6f,0.0F);
                leftLeg.setScale(f1,f1,f1);
                leftLeg.setPosition(-1F,-6.0F,0.0F);
            }else {
                head.setScale(1,1,1);
                body.setScale(1,1,1);
                rightArm.setScale(rightArm.getScale());
                leftArm.setScale(leftArm.getScale());
                rightLeg.setScale(1,1,1);
                leftLeg.setScale(1,1,1);
            }

            if(data.isSitting){
                rightLeg.setRotation(1.25664F, -0.261799F,0.0F);
                leftLeg.setRotation(1.25664F, 0.261799F,0.0F);
                if(main!=null){
                    main.setPosition(0.0F,1.0F,0.0F);
                }
            }else {
                rightLeg.setRotation(rightLeg.getRotation());
                leftLeg.setRotation(leftLeg.getRotation());
                if(main!=null){
                    main.setPositionY(main.getPositionY());
                }
            }
        }
    }
}
