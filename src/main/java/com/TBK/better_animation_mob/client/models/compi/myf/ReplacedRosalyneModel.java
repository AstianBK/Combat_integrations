package com.TBK.better_animation_mob.client.models.compi.myf;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedHumanoidModel;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.RosalyneEntity;
import lykrast.meetyourfight.renderer.RosalyneModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoBone;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ReplacedRosalyneModel<T extends IAnimatable> extends ReplacedHumanoidModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID,"geo/myf/rosalyne.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(MeetYourFight.MODID,"textures/entity/rosalyne.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/myf/rosalyne.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        GeoBone coffin= (GeoBone) getBone("Coffin");
        RosalyneEntity entity= (RosalyneEntity) this.getCurrentEntity().get();
        int phase=entity.getPhase();
        coffin.setHidden(phase != 0 && phase != 1);
    }
}
