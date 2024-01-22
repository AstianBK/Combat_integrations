package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity;

import com.TBK.better_animation_mob.server.modbusevent.ModBusEvent;
import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.AttackAGoal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.EnderMan;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
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

public class ReplacedEnderMan<T extends EnderMan> extends ReplacedEntity<T>{
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    protected void replacedGoals() {
        Set<Goal> goals = new HashSet<>();
        ModBusEvent.removeMeleeGoal(this.replaced,goals);
        goals.forEach(this.replaced.goalSelector::removeGoal);

        this.replaced.goalSelector.addGoal(2,new AttackAGoal<>(this.replaced,1.0D,false,this));
    }

    @Override
    public int isMomentHurt() {
        return 5;
    }

    @Override
    public void resetCooldownAttack() {
        this.attackTimer=15;
    }

    @Override
    public int getMaxCombo() {
        return 2;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10,EasingType.EaseInElastic, state -> {
            EnderMan zombie = getZombieFromState(state);
            ReplacedEntity<?> replaced = Capabilities.getEntityPatch(zombie, ReplacedEnderMan.class);
            if (zombie == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if(isMove && replaced.getAttackTimer()==0){
                state.getController().setAnimationSpeed(zombie.isAggressive()?3.0F : 4.0F);
                state.getController().setAnimation(new AnimationBuilder().loop( zombie.isAggressive() ? "enderman.move2" : "enderman.move"));
            }else if(replaced.getAttackTimer()>0){
                state.getController().setAnimationSpeed(1.5D);
                state.getController().setAnimation(new AnimationBuilder().playAndHold("enderman.attack"+((ICombos)zombie).getCombo()));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(new AnimationBuilder().loop("enderman.idle"));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_legs", 10, state -> {
            EnderMan zombie = getZombieFromState(state);
            ReplacedEntity<?> replaced = Capabilities.getEntityPatch(zombie, ReplacedEnderMan.class);
            if (zombie == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove && replaced.getAttackTimer()==0) {
                state.getController().setAnimationSpeed(zombie.isAggressive()?3.0F : 4.0F);
                state.getController().setAnimation(new AnimationBuilder().loop("enderman.legs1"));
            }else {
                state.getController().setAnimationSpeed(1.0f);
                state.getController().setAnimation(new AnimationBuilder().loop("enderman.legs2"));
            }
            return PlayState.CONTINUE;
        }));
    }
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    private EnderMan getZombieFromState(AnimationEvent<ReplacedEnderMan<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof EnderMan enderman)) return null;
        return enderman;
    }
}
