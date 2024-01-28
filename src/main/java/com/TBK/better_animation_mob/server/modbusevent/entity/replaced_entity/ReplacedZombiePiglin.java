package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity;

import com.TBK.better_animation_mob.server.modbusevent.ModBusEvent;
import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.AttackAGoal;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.MeleeAttackPatch;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReplacedZombiePiglin<T extends ZombifiedPiglin> extends ReplacedEntity<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);

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

    public int isMomentHurt(){
        return 5;
    }

    private LivingEntity getAttackTarget(Mob p_23533_) {
        return p_23533_.getTarget();
    }

    @Override
    protected void replacedGoals() {
        Set<Goal> goals = new HashSet<>();
        ModBusEvent.removeMeleeGoal(this.replaced,goals);
        goals.forEach(this.replaced.goalSelector::removeGoal);

        this.replaced.goalSelector.addGoal(2,new AttackAGoal<>(this.replaced,1.0D,false,this){
            private int raiseArmTicks;
            public void start() {
                super.start();
                this.raiseArmTicks = 0;
            }

            public void stop() {
                super.stop();
                this.mob.setAggressive(false);
            }

            public void tick() {
                super.tick();
                ++this.raiseArmTicks;
                if (this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
                    this.mob.setAggressive(true);
                } else {
                    this.mob.setAggressive(false);
                }

            }
        });
    }

    public void resetCooldownAttack(){
        this.attackTimer=10;
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    private PlayState predicate(AnimationEvent<ReplacedZombiePiglin<T>> state) {
        ZombifiedPiglin piglin = this.getRaiderFromState(state);
        ReplacedEntity<?> replacedPiglin = Capabilities.getEntityPatch(piglin, ReplacedZombiePiglin.class);
        if(piglin==null){
            return PlayState.STOP;
        }
        boolean hasCrossbow=piglin.getMainHandItem().is(Items.CROSSBOW);
        boolean isMove=!(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
        if(hasCrossbow){
            if (isMove) {
                state.getController().setAnimationSpeed(piglin.isAggressive()?5.0F : 1.0F);
                state.getController().setAnimation(new AnimationBuilder().addAnimation("piglin.moveAim", ILoopType.EDefaultLoopTypes.LOOP));
            }else {
                state.getController().setAnimationSpeed(1F);
                state.getController().setAnimation(new AnimationBuilder().addAnimation("piglin.idleAim", ILoopType.EDefaultLoopTypes.LOOP));
            }
        }else {
            if (isMove && replacedPiglin.getAttackTimer()==0) {
                state.getController().setAnimationSpeed(piglin.isAggressive()?5.0F : 2.0F);
                state.getController().setAnimation(new AnimationBuilder().loop(piglin.isAggressive()?"piglin.move2":"piglin.move"));
            }else if(replacedPiglin.getAttackTimer()>0) {
                state.getController().setAnimationSpeed(2.5D);
                state.getController().setAnimation(new AnimationBuilder().playAndHold("piglin.attack"+((ICombos)piglin).getCombo()));
            }else{
                state.getController().setAnimationSpeed(1F);
                state.getController().setAnimation(new AnimationBuilder().loop("piglin.idle"));
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    @Nullable
    private ZombifiedPiglin getRaiderFromState(AnimationEvent<ReplacedZombiePiglin<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof ZombifiedPiglin enderman)) return null;
        return enderman;
    }
}
