package com.TBK.better_animation_mob.client.models.illager;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.AnimationVanillaG;
import com.TBK.better_animation_mob.client.models.ReplacedHumanoidModel;
import com.teamabnormals.savage_and_ravage.core.SavageAndRavage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.AbstractIllager;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ReplacedGrieferModel<T extends IAnimatable> extends ReplacedHumanoidModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/svr/svrgriefer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(SavageAndRavage.MOD_ID,"textures/entity/griefer/griefer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID, "animations/svr/griefer.animation.json");
    }
}
