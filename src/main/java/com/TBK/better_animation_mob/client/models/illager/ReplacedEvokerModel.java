package com.TBK.better_animation_mob.client.models.illager;

import com.TBK.better_animation_mob.BetterAnimationMob;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;

@OnlyIn(Dist.CLIENT)
public class ReplacedEvokerModel<T extends IAnimatable> extends ReplacedVindicatorModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID, "geo/vindicator.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/entity/illager/evoker.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/evoker.animation.json");
    }

}
