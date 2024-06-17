package com.TBK.combat_integration.server.modbusevent.entity.replaced_entity;

import com.TBK.combat_integration.server.modbusevent.ModBusEvent;
import com.TBK.combat_integration.server.modbusevent.api.ICombos;
import com.TBK.combat_integration.server.modbusevent.cap.Capabilities;
import com.TBK.combat_integration.server.modbusevent.entity.goals.AttackAGoal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Ravager;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReplacedRavager<T extends Ravager> extends ReplacedEntity<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);

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

        this.replaced.goalSelector.addGoal(4,new AttackAGoal<>(this.replaced,1.0D,true,this){
            protected double getAttackReachSqr(LivingEntity p_33377_) {
                float f = this.mob.getBbWidth() - 0.1F;
                return (double)(f * 2.0F * f * 2.0F + p_33377_.getBbWidth());
            }
        });

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 15, state -> {
            Ravager raider = getRaiderFromState(state);
            ReplacedRavager<?> replacedRavager= Capabilities.getEntityPatch(raider, ReplacedRavager.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;

            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove && replacedRavager.getAttackTimer() == 0) {
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
            ReplacedRavager<?> replacedRavager= Capabilities.getEntityPatch(raider, ReplacedRavager.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            if(replacedRavager.getAttackTimer()==0) {
                return PlayState.STOP;
            }
            state.getController().setAnimationSpeed(2F);
            state.getController().setAnimation(builder.playAndHold("ravager.attack"+((ICombos)raider).getCombo()));

            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    @Nullable
    private Ravager getRaiderFromState(AnimationEvent<ReplacedRavager<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Ravager enderman)) return null;
        return enderman;
    }
}
