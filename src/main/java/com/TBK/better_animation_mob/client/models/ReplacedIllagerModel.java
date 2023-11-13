package com.TBK.better_animation_mob.client.models;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Executioner;
import com.teamabnormals.savage_and_ravage.core.SavageAndRavage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;

@OnlyIn(Dist.CLIENT)
public class ReplacedIllagerModel<T extends IAnimatable> extends ReplacedEntityModel<T> {

    @Override
    public ResourceLocation getModelResource(T object) {
        if(this.getCurrentEntity().get() instanceof Executioner){
            return new ResourceLocation(BetterAnimationMob.MODID,"geo/svr/executioner.geo.json");
        }else if(this.getCurrentEntity().get() instanceof Pillager){
            return new ResourceLocation(BetterAnimationMob.MODID,"geo/pillager.geo.json");
        }else {
            return new ResourceLocation(BetterAnimationMob.MODID,"geo/vindicator.geo.json");

        }
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        if(this.getCurrentEntity().get() instanceof Executioner){
            return new ResourceLocation(SavageAndRavage.MOD_ID,"textures/entity/executioner.png");
        }else if(this.getCurrentEntity().get() instanceof Pillager){
            return new ResourceLocation("textures/entity/illager/pillager.png");
        }else {
            return new ResourceLocation("textures/entity/illager/vindicator.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        if(this.getCurrentEntity().get() instanceof Executioner){
            return new ResourceLocation(BetterAnimationMob.MODID,"animations/svr/executioner.animation.json");
        }else if(this.getCurrentEntity().get() instanceof Pillager){
            return new ResourceLocation(BetterAnimationMob.MODID,"animations/pillager.animation.json");
        }else {
            return new ResourceLocation(BetterAnimationMob.MODID,"animations/vindicator.animation.json");
        }
    }
}
