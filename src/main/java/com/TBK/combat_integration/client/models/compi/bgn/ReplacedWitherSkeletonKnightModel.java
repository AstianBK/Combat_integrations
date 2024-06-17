package com.TBK.combat_integration.client.models.compi.bgn;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.models.skeleton.ReplacedSkeletonModel;
import com.izofar.bygonenether.BygoneNetherMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;

public class ReplacedWitherSkeletonKnightModel<T extends IAnimatable> extends ReplacedSkeletonModel<T> {
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/wither/wither_skeleton_knight.png");
    }

    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(CombatIntegration.MODID,"geo/bgn/wither_knight.geo.json");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(CombatIntegration.MODID,"animations/skeleton.animation.json");
    }
}
