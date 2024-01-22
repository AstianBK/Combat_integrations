package com.TBK.better_animation_mob.client.models.illager;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.teamabnormals.savage_and_ravage.core.SavageAndRavage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;

@OnlyIn(Dist.CLIENT)
public class ReplacedIceologerModel<T extends IAnimatable> extends ReplacedVindicatorModel<T> {

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(SavageAndRavage.MOD_ID,"textures/entity/iceloger.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/evoker.animation.json");
    }

}
