package com.TBK.better_animation_mob.client.models.compi.gv;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.AnimationVanillaG;
import com.TBK.better_animation_mob.client.models.ReplacedHumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.AbstractIllager;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import tallestegg.guardvillagers.configuration.GuardConfig;
import tallestegg.guardvillagers.entities.Guard;

public class ReplacedGuardVillagerModel<T extends IAnimatable> extends ReplacedHumanoidModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/gv/guardvillager.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        Guard entity= (Guard) this.getCurrentEntity().get();
        return !GuardConfig.guardSteve ? new ResourceLocation("guardvillagers", "textures/entity/guard/guard_" + entity.getGuardVariant() + ".png") : new ResourceLocation("guardvillagers", "textures/entity/guard/guard_steve_" + entity.getGuardVariant() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID, "animations/gv/guardvillager.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        IBone head = this.getAnimationProcessor().getBone("Head");
        IBone rightArm = this.getAnimationProcessor().getBone("RightArm");
        IBone leftArm = this.getAnimationProcessor().getBone("LeftArm");
        AbstractIllager abstractIllager=((AbstractIllager)this.getCurrentEntity().get());
        float pLimbSwing = animationEvent.getLimbSwing();
        float pLimbSwingAmount = animationEvent.getLimbSwingAmount();
        float pAgeInTicks = abstractIllager.tickCount;
        float pNetHeadYaw = extraData.netHeadYaw;
        float pHeadPitch = extraData.headPitch;
        float f = Mth.cos(pLimbSwing * 0.261799F) * pLimbSwingAmount * 0.5F;

        head.setRotationY(pNetHeadYaw * ((float)Math.PI / 180F));
        head.setRotationX(pHeadPitch * ((float)Math.PI / 180F));

        AbstractIllager.IllagerArmPose abstractillager$illagerarmpose = abstractIllager.getArmPose();
        if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE) {
            AnimationVanillaG.animateCrossbowCharge(rightArm, leftArm, this.getCurrentEntity().get(), true);
        } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CELEBRATING) {
            rightArm.setPositionZ(0.0F);
            rightArm.setPositionX(-5.0F);
            rightArm.setRotationX(Mth.cos(pAgeInTicks * 0.6662F) * 0.05F);
            rightArm.setRotationZ(2.670354F);
            rightArm.setRotationY(0.0F);
            leftArm.setPositionZ(0.0F);
            leftArm.setPositionX(5.0F);
            leftArm.setRotationX( Mth.cos(pAgeInTicks * 0.6662F) * 0.05F);
            leftArm.setRotationY(-2.3561945F);
            leftArm.setRotationZ(0.0F);
        }
    }
}
