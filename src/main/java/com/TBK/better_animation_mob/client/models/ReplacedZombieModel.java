package com.TBK.better_animation_mob.client.models;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedZombie;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

@OnlyIn(Dist.CLIENT)
public class ReplacedZombieModel<T extends IAnimatable> extends ReplacedEntityModel<T> {

    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/zombie.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        if(this.getCurrentEntity() instanceof Drowned){
            return new ResourceLocation("textures/entity/zombie/drowned.png");
        }else if (this.getCurrentEntity() instanceof Husk){
            return new ResourceLocation("textures/entity/zombie/husk.png");
        }else {
            return new ResourceLocation("textures/entity/zombie/zombie.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/zombie.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
    }
}
