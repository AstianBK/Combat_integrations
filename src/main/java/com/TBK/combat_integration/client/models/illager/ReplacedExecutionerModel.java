package com.TBK.combat_integration.client.models.illager;

import com.TBK.combat_integration.CombatIntegration;
import com.teamabnormals.savage_and_ravage.core.SavageAndRavage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;

@OnlyIn(Dist.CLIENT)
public class ReplacedExecutionerModel<T extends IAnimatable> extends ReplacedVindicatorModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(CombatIntegration.MODID, "geo/svr/executioner.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(SavageAndRavage.MOD_ID,"textures/entity/executioner.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(CombatIntegration.MODID,"animations/svr/executioner.animation.json");
    }

}
