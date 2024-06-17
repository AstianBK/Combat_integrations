package com.TBK.combat_integration.client.models.compi.myf;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.models.ReplacedHumanoidModel;
import lykrast.meetyourfight.MeetYourFight;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

@OnlyIn(Dist.CLIENT)
public class ReplacedFortunaModel<T extends IAnimatable> extends ReplacedHumanoidModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(CombatIntegration.MODID,"geo/myf/fortuna.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(MeetYourFight.MODID,"textures/entity/dame_fortuna.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(CombatIntegration.MODID,"animations/myf/fortuna.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
    }
}
