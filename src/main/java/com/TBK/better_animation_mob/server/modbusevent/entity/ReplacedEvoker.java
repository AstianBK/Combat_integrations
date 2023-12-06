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
            if (isMove && !raider.isCastingSpell()) {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.movebody"));
            } else if(raider.isCastingSpell()) {
                state.getController().setAnimationSpeed(0.5F);
                state.getController().setAnimation(builder.loop("evoker.spellfangbody"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.idlebody"));
            }

            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_legs", 0, state -> {
            Evoker raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove && !raider.isCastingSpell()) {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.movelegs"));
            } else if(raider.isCastingSpell()) {
                state.getController().setAnimationSpeed(0.5F);
                state.getController().setAnimation(builder.loop("evoker.spellfanglegs"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.idlelegs"));
            }

            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_arms", 0, state -> {
            Evoker raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove && !raider.isCastingSpell()) {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.movearms"));
            } else if(raider.isCastingSpell()) {
                state.getController().setAnimationSpeed(1.5F);
                state.getController().setAnimation(builder.loop("evoker.spellfangarms"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.idlearms"));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_main", 0, state -> {
            Evoker raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove && !raider.isCastingSpell()) {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.movemain"));
            } else if(raider.isCastingSpell()) {
                state.getController().setAnimationSpeed(0.5F);
                state.getController().setAnimation(builder.loop("evoker.spellfangmain"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.idlemain"));
            }
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
