package com.TBK.better_animation_mob.server.modbusevent.entity;

import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ravager;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class ReplacedRavager implements IAnimatable {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 15, state -> {
            Ravager raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            if(raider.hurtTime>0){
                state.getController().setAnimationSpeed(3.0F);
                state.getController().setAnimation(builder.playOnce("ravager.hurt"));
                return PlayState.CONTINUE;
            }
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if ((state.isMoving() || isMove) && raider.getAttackAnim(state.getPartialTick()) == 0) {
                state.getController().setAnimationSpeed(raider.isAggressive()?3.0F : 0.5F);
                state.getController().setAnimation(builder.loop(raider.isAggressive() ? "ravager.move2" : "ravager.move"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("ravager.idle"));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_attack", 0, state -> {
            Ravager raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            if(raider.getAttackAnim(state.getPartialTick())==0) {
                return PlayState.STOP;
            }
            state.getController().setAnimationSpeed(2F);
            state.getController().setAnimation(builder.loop("ravager.attack"+((ICombos)raider).getCombo()));

            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    @Nullable
    private Ravager getRaiderFromState(AnimationEvent<ReplacedRavager> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Ravager enderman)) return null;
        return enderman;
    }
}
