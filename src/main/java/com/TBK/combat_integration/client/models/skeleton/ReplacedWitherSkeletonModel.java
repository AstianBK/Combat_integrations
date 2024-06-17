package com.TBK.combat_integration.client.models.skeleton;

import com.TBK.combat_integration.CombatIntegration;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;

public class ReplacedWitherSkeletonModel<T extends IAnimatable> extends ReplacedSkeletonModel<T> {
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(CombatIntegration.MODID,"animations/wither_skeleton.animation.json");
    }
}
