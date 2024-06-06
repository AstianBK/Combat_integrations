package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity;

import com.TBK.better_animation_mob.server.modbusevent.ModBusEvent;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.AttackAGoal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;
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

public class ReplacedIronGolem<T extends IronGolem> extends ReplacedEntity<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public int sonicBoomAnimTimer;

    @Override
    public void init(Entity entity) {
        this.cooldownAttack=new int[]{
                15,
                15,
                35
        };
        super.init(entity);
    }

    @Override
    protected void replacedGoals() {
        Set<Goal> goals = new HashSet<>();
        ModBusEvent.removeMeleeGoal(this.replaced,goals);
        goals.forEach(this.replaced.goalSelector::removeGoal);

        this.replaced.goalSelector.addGoal(1,new AttackAGoal<>(this.replaced,1.0D,false,this));
    }
    public void onTick(Level level){
        if(level.isClientSide){
            onClientTick();
        }else {
            onServerTick();
        }
        if(this.attackTimer>0){
            this.attackTimer--;
            if(this.attackTimer==isMomentHurt()){
                LivingEntity target = getAttackTarget(this.replaced);
                if(target!=null){
                    this.replaced.doHurtTarget(target);
                }
            }
        }
    }

    private LivingEntity getAttackTarget(T replaced) {
        return replaced.getTarget();
    }

    @Override
    public int isMomentHurt() {
        return getCombo(this.replaced) == 3 ? 10 :5;
    }

    @Override
    public void resetCooldownAttack() {
        this.attackTimer = this.cooldownAttack[this.getCombo(this.replaced)-1];
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, state -> {
            IronGolem golem = getWardenFromState(state);
            ReplacedIronGolem<?> replacedWarden = getPatch(golem, ReplacedIronGolem.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (golem == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if(replacedWarden.getAttackTimer()>0){
                state.getController().setAnimationSpeed(1.5D);
                state.getController().setAnimation(builder.playOnce("irongolem.attack"+getCombo(golem)));
            }else{
                state.getController().setAnimationSpeed(1D);
                state.getController().setAnimation(builder.loop(isMove ? "irongolem.move" :"irongolem.idle"));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_legs", 10, state -> {
            IronGolem golem = getWardenFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (golem == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if(!state.isMoving() || isMove){
                state.getController().setAnimationSpeed(3D);
                state.getController().setAnimation(builder.loop( "irongolem.legs1"));
            }else{
                state.getController().setAnimationSpeed(0.5D);
                state.getController().setAnimation(builder.loop("irongolem.legs2"));
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    public IronGolem getWardenFromState(AnimationEvent<ReplacedIronGolem<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof IronGolem enderman)) return null;
        return enderman;
    }

}
