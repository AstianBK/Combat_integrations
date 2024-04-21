package com.TBK.better_animation_mob.client.models.dm;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.AnimationVanillaG;
import com.TBK.better_animation_mob.client.models.piglin.ReplacedPiglinModel;
import com.infamous.dungeons_mobs.DungeonsMobs;
import com.izofar.bygonenether.BygoneNetherMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ReplacedFungusThrowerModel<T extends IAnimatable> extends ReplacedPiglinModel<T> {
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(DungeonsMobs.MODID,"textures/entity/piglin/fungus_thrower.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/piglin.animation.json");
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
