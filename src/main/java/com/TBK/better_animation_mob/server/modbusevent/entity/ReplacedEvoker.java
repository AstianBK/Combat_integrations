package com.TBK.better_animation_mob.server.modbusevent.entity;

import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.*;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.List;

public class ReplacedEvoker extends ReplacedEntity {
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, state -> {
            Evoker raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if(raider.hurtTime>0){
                state.getController().setAnimationSpeed(3.0F);
                state.getController().setAnimation(builder.playOnce("evoker.hurt"));
                return PlayState.CONTINUE;
            }
            if (isMove) {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.move2"));
            } else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.idle"));
            }

            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_attack", 0, state -> {
            Evoker raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            if(!raider.isCastingSpell()) {
                return PlayState.CONTINUE;
            }
            state.getController().setAnimationSpeed(0.5F);
            state.getController().setAnimation(builder.loop("evoker.spellfang2"));

            return PlayState.CONTINUE;
        }));
    }


    @Nullable
    private Evoker getRaiderFromState(AnimationEvent<ReplacedEvoker> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Evoker enderman)) return null;
        return enderman;
    }
}
