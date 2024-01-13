package com.TBK.better_animation_mob.client.models.boss;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedEntityModel;
import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedWarden;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import software.bernie.geckolib3.geo.render.built.GeoBone;

@OnlyIn(Dist.CLIENT)
public class ReplacedWardenModel<T extends ReplacedWarden<Warden>> extends ReplacedEntityModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/warden.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/entity/warden/warden.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/warden.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        GeoBone body = (GeoBone) this.getBone("Body");
        GeoBone rightArm = (GeoBone) this.getBone("right_arm");
        IBone leftArm = this.getBone("left_arm");
        GeoBone main = this.getModel(this.getModelResource(animatable)).getBone("main").isPresent() ? this.getModel(this.getModelResource(animatable)).getBone("main").get() : null;
        if(main!=null){
            this.resetMain(main);
        }
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        float partialTick=animationEvent.getPartialTick();
        float limbSwing=animationEvent.getLimbSwing();
        float limbSwingAmount=animationEvent.getLimbSwingAmount();
        Warden warden = (Warden) this.getCurrentEntity().get();

        animateWalk(limbSwing,limbSwingAmount, (GeoBone) body);
        animateIdlePose(partialTick, (GeoBone) body);
        animateTendrils(warden,limbSwing,limbSwingAmount);
    }

    @Override
    public boolean canMoveHead(LivingEntity entity, AnimationEvent<?> event) {
        ReplacedWarden<?> replacedWarden= Capabilities.getEntityPatch(entity, ReplacedWarden.class);
        return super.canMoveHead(entity, event) && replacedWarden.getSonicBoomAnimTimer()==0;
    }

    private void animateIdlePose(float p_233515_, GeoBone body) {
        float f = p_233515_ * 0.1F;
        float f1 = Mth.cos(f);
        float f2 = Mth.sin(f);
        body.addRotationZ(0.025F * f2);
        body.addRotationX(0.025F * f1);
    }

    private void animateWalk(float p_233539_, float p_233540_,GeoBone body) {
        GeoBone leftLeg=(GeoBone) this.getBone("left_leg");
        GeoBone rightLeg= (GeoBone) this.getBone("right_leg");
        float f = Math.min(0.5F, 3.0F * p_233540_);
        float f1 = p_233539_ * 0.8662F;
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Math.min(0.35F, f);
        body.addRotationZ(0.1F * f3 * f);
        body.addRotationX(1.0F * f2 * f4);
        /*leftLeg.setRotationX(1.0F * f2 * f);
        rightLeg.setRotationX(1.0F * Mth.cos(f1 + (float)Math.PI) * f);
        /*
        this.leftLeg.xRot = 1.0F * f2 * f;
        this.rightLeg.xRot = 1.0F * Mth.cos(f1 + (float)Math.PI) * f;
        this.leftArm.xRot = -(0.8F * f2 * f);
        this.leftArm.zRot = 0.0F;
        this.rightArm.xRot = -(0.8F * f3 * f);
        this.rightArm.zRot = 0.0F;
        this.resetArmPoses();*/
    }

    private void animateTendrils(Warden p_233527_, float p_233528_, float p_233529_) {
        float f = p_233527_.getTendrilAnimation(p_233529_) * (float)(Math.cos((double)p_233528_ * 2.25D) * Math.PI * (double)0.1F);
        IBone leftTendril=this.getBone("left_tendril");
        IBone rightTendril=this.getBone("right_tendril");
        leftTendril.setRotationX(f);
        rightTendril.setRotationX(-f);
    }
}
