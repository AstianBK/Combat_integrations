package com.TBK.better_animation_mob.client.models;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class ReplacedEntityModel <T extends IAnimatable> extends AnimatedGeoModel<T> {

    private Supplier<LivingEntity> currentEntity;

    @Override
    public ResourceLocation getModelResource(T object) {
        return null;
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return null;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return null;
    }

    public void setCurrentEntity(Supplier<LivingEntity> entity) {
        this.currentEntity = entity;
    }

    public Supplier<LivingEntity> getCurrentEntity(){
        return this.currentEntity;
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        List<Object> list = new ArrayList<Object>(animationEvent.getExtraData());
        list.add(this.currentEntity.get());

        AnimationEvent<?> event = new AnimationEvent<>(
                animatable,
                animationEvent.getLimbSwing(),
                animationEvent.getLimbSwingAmount(),
                animationEvent.getPartialTick(),
                animationEvent.isMoving(),
                list);

        EntityModelData data = (EntityModelData) event.getExtraDataOfType(EntityModelData.class).get(0);

        if (data!=null){
            GeoBone head = (GeoBone)this.getBone("Head");
            head.setRotationX(data.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(data.netHeadYaw * ((float) Math.PI / 180F));

        }
        super.setCustomAnimations(animatable, instanceId, event);
    }

    public boolean canMoveHead(LivingEntity entity,AnimationEvent<?> event){
        return entity.hurtTime==0;
    }
}
