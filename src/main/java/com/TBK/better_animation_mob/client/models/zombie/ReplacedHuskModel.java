package com.TBK.better_animation_mob.client.models.zombie;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;

@OnlyIn(Dist.CLIENT)
public class ReplacedHuskModel<T extends IAnimatable> extends ReplacedZombieModel<T> {
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/entity/zombie/husk.png");
    }
}
