package com.TBK.better_animation_mob.client.models.zombie;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedEntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Husk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

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
    protected Iterable<GeoBone> headParts() {
        return null;
    }

    @Override
    protected Iterable<GeoBone> bodyParts() {
        return null;
    }
}
