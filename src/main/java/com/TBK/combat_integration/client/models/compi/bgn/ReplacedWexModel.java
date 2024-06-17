package com.TBK.combat_integration.client.models.compi.bgn;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.models.ReplacedEntityModel;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.Wex;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class ReplacedWexModel<T extends IAnimatable> extends ReplacedEntityModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(CombatIntegration.MODID,"geo/bgn/wex.geo.json");
    }

    @Override
    public boolean canMoveHead(LivingEntity entity, AnimationEvent<?> event) {
        return false;
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        if(this.getCurrentEntity() instanceof Wex wex && wex.isCharging()){
            return new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/wex/wex_charging.png");
        }
        return new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/wex/wex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(CombatIntegration.MODID,"animations/wex.animation.json");
    }
}
