package com.TBK.better_animation_mob.client.models.compi.bgn;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedEntityModel;
import com.TBK.better_animation_mob.client.models.ReplacedHumanoidModel;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.Wex;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Stray;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class ReplacedWexModel<T extends IAnimatable> extends ReplacedEntityModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/bgn/wex.geo.json");
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
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/wex.animation.json");
    }
}
