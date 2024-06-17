package com.TBK.combat_integration.server.modbusevent.entity.replaced_entity;

import com.TBK.combat_integration.server.modbusevent.ModBusEvent;
import com.TBK.combat_integration.server.modbusevent.entity.goals.AttackAGoal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.easing.EasingType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReplacedWolf<T extends Wolf> extends ReplacedEntity<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    protected void replacedGoals() {
        Set<Goal> goals = new HashSet<>();
        ModBusEvent.removeMeleeGoal(this.replaced,goals);
        goals.forEach(this.replaced.goalSelector::removeGoal);

        this.replaced.goalSelector.addGoal(5,new AttackAGoal<>(this.replaced,1.0D,true,this));
    }

    @Override
    public int isMomentHurt() {
        return 5;
    }

    @Override
    public void resetCooldownAttack() {
        this.attackTimer=18;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10 ,EasingType.EaseInElastic , state -> {
            Wolf zombie = getZombieFromState(state);
            ReplacedWolf<?> replaced = getPatch(zombie, ReplacedWolf.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);

            if (isMove && replaced.getAttackTimer() == 0 && !zombie.isInSittingPose()) {
                state.getController().setAnimationSpeed(zombie.isAggressive()?3.5F : 1.4F);
                state.getController().setAnimation(builder.loop( zombie.isAggressive() ? "wolf.move2" : "wolf.move1"));
            }else if(replaced.getAttackTimer()>0) {
                state.getController().setAnimationSpeed(1.5F);
                state.getController().setAnimation(builder.playOnce("wolf.attack1"));
            }else if(!zombie.isInSittingPose()){
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("wolf.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_legs", 10, state -> {
            Wolf zombie = getZombieFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null|| zombie.isInSittingPose()) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);

            if (isMove) {
                state.getController().setAnimationSpeed(zombie.isAggressive()?4F : 2F);
                state.getController().setAnimation(builder.loop( "wolf.legs1"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("wolf.legs2", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_pose", 5, state -> {
            Wolf zombie = getZombieFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null || !zombie.isInSittingPose()) return PlayState.STOP;
            state.getController().setAnimation(builder.loop("wolf.sit"));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    private Wolf getZombieFromState(AnimationEvent<ReplacedWolf<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Wolf enderman)) return null;
        return enderman;
    }

}
