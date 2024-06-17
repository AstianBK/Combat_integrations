package com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.svr;

import com.TBK.combat_integration.server.modbusevent.ModBusEvent;
import com.TBK.combat_integration.server.modbusevent.entity.goals.AttackAGoal;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedIronGolem;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedVindicator;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Executioner;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Ravager;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.easing.EasingType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReplacedExecutioner<T extends Executioner> extends ReplacedVindicator<T> {

    @Override
    public void resetCooldownAttack() {
        this.attackTimer=15;
    }

    @Override
    public int isMomentHurt() {
        return 6;
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
        data.addAnimationController(new AnimationController<>(this, "controller", 10, state -> {
            AbstractIllager raider = getRaiderFromState(state);
            ReplacedExecutioner<?> replacedExecutioner = getPatch(raider,ReplacedExecutioner.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if(replacedExecutioner.getAttackTimer()>0) {
                state.getController().setAnimationSpeed(6.0F);
                state.getController().setAnimation(builder.playAndHold("raider.attack"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("raider.idle"));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_legs", 0, EasingType.EaseInElastic, state -> {
            AbstractIllager golem = getRaiderFromState(state);
            ReplacedIronGolem<?> replacedWarden = getPatch(golem, ReplacedIronGolem.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (golem == null) return PlayState.STOP;
            if(!state.isMoving() ){
                state.getController().setAnimationSpeed(1.5D);
                state.getController().setAnimation(builder.loop( "raider.legs1"));
            }else{
                state.getController().setAnimationSpeed(0.5D);
                state.getController().setAnimation(builder.loop("raider.legs2"));
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
