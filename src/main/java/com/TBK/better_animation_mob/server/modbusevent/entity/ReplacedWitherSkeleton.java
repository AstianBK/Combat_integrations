package com.TBK.better_animation_mob.server.modbusevent.entity;

import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.List;

public class ReplacedWitherSkeleton extends ReplacedSkeleton{
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            AbstractSkeleton zombie = getZombieFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            boolean isAim = this.isWieldingTwoHandedWeapon(zombie) && zombie.isAggressive();

            if(zombie.hurtTime>0){
                state.getController().setAnimationSpeed(4.0F);
                state.getController().setAnimation(builder.playOnce("skeleton.hurt"));
                return PlayState.CONTINUE;
            }
            if (isMove && zombie.getAttackAnim(state.getPartialTick())==0) {
                state.getController().setAnimationSpeed(zombie.isAggressive()?3.0F : 4.0F);
                state.getController().setAnimation(builder.loop("skeleton.movemelee"));
            }else if(zombie.getAttackAnim(state.getPartialTick())>0) {
                state.getController().setAnimationSpeed(6.5F);
                state.getController().setAnimation(builder.playOnce(zombie instanceof ICombos ?( "skeleton.attack"+((ICombos) zombie).getCombo()): "zombie.attack1"));
            }else{
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("skeleton.idle"));
            }
            return PlayState.CONTINUE;
        }));
    }

    @Nullable
    private AbstractSkeleton getZombieFromState(AnimationEvent<ReplacedWitherSkeleton> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof AbstractSkeleton enderman)) return null;
        return enderman;
    }
}
