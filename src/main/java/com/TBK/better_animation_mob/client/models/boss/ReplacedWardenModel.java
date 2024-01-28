package com.TBK.better_animation_mob.client.models.boss;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedEntityModel;
import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedWarden;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import software.bernie.geckolib3.geo.render.built.GeoBone;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ReplacedWardenModel<T extends ReplacedWarden<Warden>> extends ReplacedEntityModel<T> {

    private ImmutableList<GeoBone> tendrilsLayerModelParts;
    private  ImmutableList<GeoBone> heartLayerModelParts;
    private ImmutableList<GeoBone> bioluminescentLayerModelParts;
    private ImmutableList<GeoBone> pulsatingSpotsLayerModelParts;

    @Override
    public void setCustomAnimations(T animatable, int instanceId) {
        super.setCustomAnimations(animatable, instanceId);
        GeoBone head=(GeoBone) this.getAnimationProcessor().getBone("Head");
        GeoBone leftArm=(GeoBone) this.getAnimationProcessor().getBone("left_arm");
        GeoBone rightArm= (GeoBone) this.getAnimationProcessor().getBone("right_arm");
        GeoBone leftLeg=(GeoBone) this.getAnimationProcessor().getBone("left_leg");
        GeoBone rightLeg= (GeoBone) this.getAnimationProcessor().getBone("right_leg");
        GeoBone body = (GeoBone)this.getAnimationProcessor().getBone("Body");
        GeoBone leftTendril= (GeoBone) this.getAnimationProcessor().getBone("left_tendril");
        GeoBone rightTendril= (GeoBone) this.getAnimationProcessor().getBone("right_tendril");
        this.tendrilsLayerModelParts = ImmutableList.of(leftTendril, rightTendril);
        this.heartLayerModelParts = ImmutableList.of(body);
        this.bioluminescentLayerModelParts = ImmutableList.of(head, leftArm, rightArm, leftLeg, rightLeg);
        this.pulsatingSpotsLayerModelParts = ImmutableList.of(body, head, leftArm, rightArm, leftLeg, rightLeg);

    }

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
        GeoBone main = (GeoBone) this.getBone("main");
        Warden warden = (Warden) this.getCurrentEntity().get();
        if (main != null) {
            this.resetMain(main);
        }
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        float partialTick = animationEvent.getPartialTick();
        float limbSwing = animationEvent.getLimbSwing();
        float limbSwingAmount = animationEvent.getLimbSwingAmount();
        if(!warden.hasPose(Pose.DIGGING)){
            animateWalk(limbSwing, limbSwingAmount, (GeoBone) body);
            animateIdlePose(partialTick, (GeoBone) body);
            animateTendrils(warden, limbSwing, limbSwingAmount);
        }
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
        GeoBone leftArm=(GeoBone) this.getBone("left_arm");
        GeoBone rightArm=(GeoBone) this.getBone("right_arm");
        float f = Math.min(0.5F, 3.0F * p_233540_);
        float f1 = p_233539_ * 0.8662F;
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Math.min(0.35F, f);
        body.addRotationZ(0.1F * f3 * f);
        body.addRotationX(1.0F * f2 * f4);
        leftArm.addRotationX(-(0.8F * f2 * f));

        /*leftArm.setRotationZ();
        rightArm.setRotationZ();
            */
        rightArm.addRotationX(-(0.8F * f2 * f));
    }

    private void animateTendrils(Warden p_233527_, float p_233528_, float p_233529_) {
        float f = p_233527_.getTendrilAnimation(p_233529_) * (float)(Math.cos((double)p_233528_ * 2.25D) * Math.PI * (double)0.1F);
        IBone leftTendril=this.getBone("left_tendril");
        IBone rightTendril=this.getBone("right_tendril");
        leftTendril.setRotationX(f);
        rightTendril.setRotationX(-f);
    }
    public List<GeoBone> getTendrilsLayerModelParts() {
        return this.tendrilsLayerModelParts;
    }

    public List<GeoBone> getHeartLayerModelParts() {
        return this.heartLayerModelParts;
    }

    public List<GeoBone> getBioluminescentLayerModelParts() {
        return this.bioluminescentLayerModelParts;
    }

    public List<GeoBone> getPulsatingSpotsLayerModelParts() {
        return this.pulsatingSpotsLayerModelParts;
    }
}
