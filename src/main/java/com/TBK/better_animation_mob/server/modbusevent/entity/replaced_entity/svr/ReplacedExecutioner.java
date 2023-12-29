package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.svr;

import com.TBK.better_animation_mob.server.modbusevent.ModBusEvent;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.AttackAGoal;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedVindicator;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Executioner;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Ravager;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReplacedExecutioner<T extends Executioner> extends ReplacedVindicator<T> {

    @Override
    public void resetCooldownAttack() {
        this.attackTimer=10;
    }

    @Override
    public int isMomentHurt() {
        return 5;
    }

    @Override
    protected void replacedGoals() {
        Set<Goal> goals = new HashSet<>();
        ModBusEvent.removeMeleeGoal(this.replaced,goals);
        goals.forEach(this.replaced.goalSelector::removeGoal);

        this.replaced.goalSelector.addGoal(4,new AttackAGoal<T,ReplacedExecutioner<T>>(this.replaced,1.0D,false,this){
            protected double getAttackReachSqr(LivingEntity p_179512_1_) {
                if (this.mob.getVehicle() instanceof Ravager) {
                    float f = this.mob.getVehicle().getBbWidth() - 0.1F;
                    return (double)(f * 2.0F * f * 2.0F + p_179512_1_.getBbWidth());
                } else {
                    return super.getAttackReachSqr(p_179512_1_);
                }
            }

            protected void resetAttackCooldown() {
                this.ticksUntilNextAttack = this.getAttackInterval();
            }

            public int getAttackInterval() {
                return 50;
            }
        });

    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            AbstractIllager raider = getRaiderFromState(state);
            ReplacedExecutioner<?> replacedExecutioner = getPatch(raider,ReplacedExecutioner.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            if(raider.isAggressive() && replacedExecutioner.getAttackTimer() == 0){
                builder.loop("raider.move2");
            }
            if(raider.hurtTime>0){
                state.getController().setAnimationSpeed(3.0F);
                state.getController().setAnimation(builder.playOnce("raider.hurt"));
                return PlayState.CONTINUE;
            }
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove && replacedExecutioner.getAttackTimer() == 0) {
                state.getController().setAnimationSpeed(raider.isAggressive()?3.0F : 1.0F);
                state.getController().setAnimation(builder.addAnimation("raider.move", ILoopType.EDefaultLoopTypes.LOOP));
            }else if(replacedExecutioner.getAttackTimer()>0) {
                state.getController().setAnimationSpeed(5.0F);
                state.getController().setAnimation(builder.playAndHold("raider.attack"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("raider.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
    }


    @Nullable
    private AbstractIllager getRaiderFromState(AnimationEvent<ReplacedExecutioner<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof AbstractIllager enderman)) return null;
        return enderman;
    }
}
