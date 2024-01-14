package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
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
import java.util.List;

public class ReplacedIronGolem<T extends IronGolem> extends ReplacedEntity<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public int sonicBoomAnimTimer;

    @Override
    public void init(Entity entity) {
        this.cooldownAttack=new int[]{
                24,
                24,
                0
        };
        super.init(entity);
    }

    @Override
    protected void replacedGoals() {
        replacedBehavior();
    }
    public void onTick(Level level){
        if(level.isClientSide){
            onClientTick();
        }else {
            onServerTick();
        }
        if(this.sonicBoomAnimTimer>0){
            this.sonicBoomAnimTimer--;
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
    protected void replacedBehavior() {

    }

    @Override
    public int isMomentHurt() {
        return this.getCombo(this.replaced) == 2 ? 2 : 5;
    }

    @Override
    public void playAttack() {
        super.playAttack();
    }

    @Override
    public int getMaxCombo() {
        return 2;
    }

    @Override
    public void resetCooldownAttack() {
        this.attackTimer = this.cooldownAttack[this.getCombo(this.replaced)-1];
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, EasingType.EaseInElastic, state -> {
            IronGolem golem = getWardenFromState(state);
            ReplacedIronGolem<?> replacedWarden = getPatch(golem, ReplacedIronGolem.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (golem == null || golem.hasPose(Pose.DIGGING) || golem.hasPose(Pose.EMERGING)|| golem.hasPose(Pose.ROARING) || golem.hasPose(Pose.SNIFFING)) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            boolean isAgressive= golem.isAggressive();
            if(replacedWarden.getAttackTimer()>0){
                state.getController().setAnimationSpeed(replacedWarden.getCombo(golem)==2 ? 1.5D : 1.0D);
                state.getController().setAnimation(builder.playOnce(replacedWarden.getCombo(golem)==2 ?"golem.attack2":"golem.attack1"));
            }else{
                state.getController().setAnimationSpeed(0.5D);
                state.getController().setAnimation(builder.loop(isAgressive ? "golem.move2" :"golem.idle"));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_legs", 0, state -> {
            IronGolem golem = getWardenFromState(state);
            ReplacedIronGolem<?> replacedWarden = getPatch(golem, ReplacedIronGolem.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (golem == null) return PlayState.STOP;
            if(!state.isMoving() ){
                state.getController().setAnimationSpeed(2.0D);
                state.getController().setAnimation(builder.loop( "golem.wardenlegs2"));
            }else if(!golem.hasPose(Pose.DIGGING) && !golem.hasPose(Pose.EMERGING)){
                state.getController().setAnimationSpeed(0.5D);
                state.getController().setAnimation(builder.loop("golem.wardenlegs1"));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_pose", 0, EasingType.EaseInOutQuad, state -> {
            IronGolem golem = getWardenFromState(state);
            ReplacedIronGolem<?> replacedWarden = getPatch(golem, ReplacedIronGolem.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (golem == null || replacedWarden.getAttackTimer()>0) return PlayState.STOP;
            if(golem.getPose().equals(Pose.SNIFFING)){
                state.getController().setAnimationSpeed(1.5D);
                state.getController().setAnimation(builder.loop("golem.sniff"));
            }else if (golem.getPose().equals(Pose.DIGGING)){
                state.getController().setAnimationSpeed(1D);
                state.getController().setAnimation(builder.playOnce("golem.dig"));
            }else if (golem.getPose().equals(Pose.EMERGING)){
                state.getController().setAnimationSpeed(1D);
                state.getController().setAnimation(builder.playOnce("golem.emerge"));
            }else if (golem.getPose().equals(Pose.ROARING)){
                state.getController().setAnimationSpeed(0.7D);
                state.getController().setAnimation(builder.playOnce("golem.roar"));
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
