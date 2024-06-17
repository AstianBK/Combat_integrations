package com.TBK.combat_integration.client.models.compi.gv;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.models.AnimationVanillaG;
import com.TBK.combat_integration.client.models.ReplacedHumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import tallestegg.guardvillagers.configuration.GuardConfig;
import tallestegg.guardvillagers.entities.Guard;

public class ReplacedGuardVillagerModel<T extends IAnimatable> extends ReplacedHumanoidModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(CombatIntegration.MODID,"geo/gv/guardvillager.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        Guard entity= (Guard) this.getCurrentEntity().get();
        return !GuardConfig.guardSteve ? new ResourceLocation("guardvillagers", "textures/entity/guard/guard_" + entity.getGuardVariant() + ".png") : new ResourceLocation("guardvillagers", "textures/entity/guard/guard_steve_" + entity.getGuardVariant() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(CombatIntegration.MODID, "animations/gv/guardvillager.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        IBone head = this.getAnimationProcessor().getBone("Head");
        IBone rightArm = this.getAnimationProcessor().getBone("RightArm");
        IBone leftArm = this.getAnimationProcessor().getBone("LeftArm");
        Guard abstractIllager=((Guard)this.getCurrentEntity().get());
        float pLimbSwing = animationEvent.getLimbSwing();
        float pLimbSwingAmount = animationEvent.getLimbSwingAmount();
        float pAgeInTicks = abstractIllager.tickCount;
        float pNetHeadYaw = extraData.netHeadYaw;
        float pHeadPitch = extraData.headPitch;
        float f = Mth.cos(pLimbSwing * 0.261799F) * pLimbSwingAmount * 0.5F;

        head.setRotationY(pNetHeadYaw * ((float)Math.PI / 180F));
        head.setRotationX(pHeadPitch * ((float)Math.PI / 180F));

        ItemStack itemStackMain=abstractIllager.getMainHandItem();
        ItemStack itemStackOff=abstractIllager.getOffhandItem();



        if (abstractIllager.getUseItemRemainingTicks()>0) {
            AnimationVanillaG.animateCrossbowCharge(rightArm, leftArm, this.getCurrentEntity().get(), true);
        }
    }
}
