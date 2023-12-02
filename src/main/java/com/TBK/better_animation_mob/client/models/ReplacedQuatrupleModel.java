package com.TBK.better_animation_mob.client.models;

import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import java.util.ArrayList;
import java.util.List;

public class ReplacedQuatrupleModel <T extends IAnimatable> extends ReplacedEntityModel<T>{

    @Override
    public boolean canMoveHead(LivingEntity entity, AnimationEvent<?> event) {
        return super.canMoveHead(entity, event) && entity.getAttackAnim(event.getPartialTick())==0;
    }
}
