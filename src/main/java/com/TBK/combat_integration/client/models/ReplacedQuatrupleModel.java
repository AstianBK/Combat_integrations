package com.TBK.combat_integration.client.models;

import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class ReplacedQuatrupleModel <T extends IAnimatable> extends ReplacedEntityModel<T>{

    @Override
    public boolean canMoveHead(LivingEntity entity, AnimationEvent<?> event) {
        return super.canMoveHead(entity, event) && entity.getAttackAnim(event.getPartialTick())==0;
    }
}
