package com.TBK.combat_integration.client.models.compi.dm;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.models.piglin.ReplacedZombiePiglinModel;
import com.infamous.dungeons_mobs.DungeonsMobs;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class ReplacedZombiefierFungusThrowerModel<T extends IAnimatable> extends ReplacedZombiePiglinModel<T> {
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(DungeonsMobs.MODID,"textures/entity/piglin/zombified_fungus_thrower.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(CombatIntegration.MODID,"animations/zombie_piglin.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
    }

}
