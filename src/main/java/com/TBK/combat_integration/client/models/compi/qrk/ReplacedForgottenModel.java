package com.TBK.combat_integration.client.models.compi.qrk;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.models.skeleton.ReplacedSkeletonModel;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import vazkii.quark.base.Quark;

public class ReplacedForgottenModel<T extends IAnimatable> extends ReplacedSkeletonModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(CombatIntegration.MODID,"geo/qrk/forgotten.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(Quark.MOD_ID,"textures/model/entity/forgotten/main.png");
    }
}
