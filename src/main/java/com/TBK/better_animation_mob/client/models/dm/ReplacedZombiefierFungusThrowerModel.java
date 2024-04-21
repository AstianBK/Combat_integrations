package com.TBK.better_animation_mob.client.models.dm;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.AnimationVanillaG;
import com.TBK.better_animation_mob.client.models.piglin.ReplacedPiglinModel;
import com.TBK.better_animation_mob.client.models.piglin.ReplacedZombiePiglinModel;
import com.infamous.dungeons_mobs.DungeonsMobs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ReplacedZombiefierFungusThrowerModel<T extends IAnimatable> extends ReplacedZombiePiglinModel<T> {
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(DungeonsMobs.MODID,"textures/entity/piglin/zombified_fungus_thrower.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/zombie_piglin.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
    }

}
