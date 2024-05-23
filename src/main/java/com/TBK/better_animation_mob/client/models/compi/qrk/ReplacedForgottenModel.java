package com.TBK.better_animation_mob.client.models.compi.qrk;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedHumanoidModel;
import com.TBK.better_animation_mob.client.models.skeleton.ReplacedSkeletonModel;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Stray;
import software.bernie.geckolib3.core.IAnimatable;
import vazkii.quark.base.Quark;

public class ReplacedForgottenModel<T extends IAnimatable> extends ReplacedSkeletonModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/qrk/forgotten.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(Quark.MOD_ID,"textures/model/entity/forgotten/main.png");
    }
}
