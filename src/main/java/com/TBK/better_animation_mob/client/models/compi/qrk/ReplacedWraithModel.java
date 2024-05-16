package com.TBK.better_animation_mob.client.models.compi.qrk;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedEntityModel;
import com.TBK.better_animation_mob.client.models.ReplacedHumanoidModel;
import com.TBK.better_animation_mob.client.models.zombie.ReplacedZombieModel;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedZombie;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

@OnlyIn(Dist.CLIENT)
public class ReplacedWraithModel<T extends IAnimatable> extends ReplacedEntityModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/qrk/wraith.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/model/entity/wraith.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/qrk/wraith.animation.json");
    }

    @Override
    public boolean canMoveHead(LivingEntity entity, AnimationEvent<?> event) {
        return false;
    }
}
