package com.TBK.better_animation_mob.server.modbusevent.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.List;

public class ReplacedPillager extends ReplacedEntity {
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            AbstractIllager raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            boolean isCharge = raider.getArmPose().equals(AbstractIllager.IllagerArmPose.CROSSBOW_HOLD);
            if (isMove && raider.getAttackAnim(state.getPartialTick()) == 0) {
                state.getController().setAnimation(builder.addAnimation("pillager.move", ILoopType.EDefaultLoopTypes.LOOP));
            }else if(raider.getArmPose().equals(AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE)) {
                state.getController().setAnimation(builder.playOnce("pillager.attack"));
            }else {
                state.getController().setAnimation(builder.addAnimation(isCharge ? "pillager.idle2" :  "pillager.idle1" , ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
    }


    @Nullable
    private AbstractIllager getRaiderFromState(AnimationEvent<ReplacedPillager> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof AbstractIllager enderman)) return null;
        return enderman;
    }
}