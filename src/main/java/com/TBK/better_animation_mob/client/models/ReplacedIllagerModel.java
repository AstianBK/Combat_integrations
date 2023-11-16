package com.TBK.better_animation_mob.client.models;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Executioner;
import com.teamabnormals.savage_and_ravage.core.SavageAndRavage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;

@OnlyIn(Dist.CLIENT)
public class ReplacedIllagerModel<T extends IAnimatable> extends ReplacedEntityModel<T> {

    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID, "geo/"+getName(this.getCurrentEntity().get())+".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        if(this.getCurrentEntity().get() instanceof Executioner){
            return new ResourceLocation(SavageAndRavage.MOD_ID,"textures/entity/executioner.png");
        }else {
            return new ResourceLocation("textures/entity/illager/vindicator.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        if(this.getCurrentEntity().get() instanceof Executioner){
            return new ResourceLocation(BetterAnimationMob.MODID,"animations/svr/executioner.animation.json");
        }else {
            return new ResourceLocation(BetterAnimationMob.MODID,"animations/"+getName(this.getCurrentEntity().get())+".animation.json");
        }
    }

    public String getName(LivingEntity entity){
        String s1="vindicator";
        if (entity.getEncodeId()!=null && entity!=null){
            s1=entity.getEncodeId().split(":")[1];
        }
        return s1;
    }
}
