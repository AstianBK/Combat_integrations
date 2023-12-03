package com.TBK.better_animation_mob.server.modbusevent.entity;

import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.List;

public class ReplacedPiglin extends ReplacedEntity {
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, state -> {
            AbstractPiglin raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;

            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove && raider.getAttackAnim(state.getPartialTick()) == 0) {
                state.getController().setAnimationSpeed(raider.isAggressive()?3.0F : 1.0F);
                state.getController().setAnimation(builder.addAnimation(raider.isAggressive()?"piglin.move2":"piglin.move1", ILoopType.EDefaultLoopTypes.LOOP));
            }else if(raider.getAttackAnim(state.getPartialTick())>0) {
                state.getController().setAnimationSpeed(3F);
                state.getController().setAnimation(builder.playAndHold(((ICombos)raider).getCombo()==1?"piglin.attack1":"piglin.attack2"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("piglin.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
    }


    @Nullable
    private AbstractPiglin getRaiderFromState(AnimationEvent<ReplacedPiglin> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof AbstractPiglin enderman)) return null;
        return enderman;
    }
}
