package com.TBK.better_animation_mob.client.models.piglin;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedHumanoidModel;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;

public class ReplacedPiglinBruteModel<T extends IAnimatable> extends ReplacedPiglinModel<T> {
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/entity/piglin/piglin_brute.png");
    }

}
