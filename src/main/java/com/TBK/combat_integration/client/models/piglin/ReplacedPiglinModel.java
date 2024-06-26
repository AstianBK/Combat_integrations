package com.TBK.combat_integration.client.models.piglin;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.models.AnimationVanillaG;
import com.TBK.combat_integration.client.models.ReplacedHumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ReplacedPiglinModel<T extends IAnimatable> extends ReplacedHumanoidModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(CombatIntegration.MODID, "geo/piglin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/entity/piglin/piglin.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(CombatIntegration.MODID,"animations/piglin.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        IBone head = this.getAnimationProcessor().getBone("Head");
        IBone rightArm = this.getAnimationProcessor().getBone("RightArm");
        IBone leftArm = this.getAnimationProcessor().getBone("LeftArm");
        AbstractPiglin abstractIllager=((AbstractPiglin)this.getCurrentEntity().get());

        PiglinArmPose abstractillager$illagerarmpose = abstractIllager.getArmPose();
        if (abstractillager$illagerarmpose == PiglinArmPose.CROSSBOW_CHARGE) {
            AnimationVanillaG.animateCrossbowCharge(rightArm, leftArm, this.getCurrentEntity().get(), true);
        }else if (abstractillager$illagerarmpose == PiglinArmPose.ADMIRING_ITEM) {
            head.setRotationX(-0.5F);
            head.setRotationY(0.0F);

            leftArm.setRotationY(-0.5F);
            leftArm.setRotationX(0.9F);
        }
    }

}
