package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity;

import com.TBK.better_animation_mob.server.modbusevent.ModBusEvent;
import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.AttackAGoal;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.MeleeAttackPatch;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.SonicBoomPatch;
import com.TBK.better_animation_mob.server.modbusevent.network.PacketHandler;
import com.TBK.better_animation_mob.server.modbusevent.network.message.PacketSyncAnimAttack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
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
import java.util.List;

public class ReplacedWarden<T extends Warden> extends ReplacedEntity<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public int sonicBoomAnimTimer;

    @Override
    public void init(Entity entity) {
        this.cooldownAttack=new int[]{
                24,
                24,
                15
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


    private LivingEntity getAttackTarget(Mob p_23533_) {
        return p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isPresent() ? p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get() : null;
    }

    @Override
    protected void replacedBehavior() {
        ModBusEvent.removeBehavior(this.replaced.getBrain(), Activity.FIGHT,12, MeleeAttack.class);
        ModBusEvent.removeBehavior(this.replaced.getBrain(),Activity.FIGHT,12, SonicBoom.class);
        ModBusEvent.replaceBehavior(this.replaced.getBrain(),Activity.FIGHT, 12, MeleeAttackPatch.class,new MeleeAttackPatch(this,24,40));
        ModBusEvent.replaceBehavior(this.replaced.getBrain(),Activity.FIGHT,12, SonicBoomPatch.class,new SonicBoomPatch(this));
    }

    @Override
    public int isMomentHurt() {
        return this.getCombo(this.replaced) == 2 ? 5 : 10;
    }

    @Override
    public void playAttack() {
        super.playAttack();
        SonicBoomPatch.setCooldown(this.replaced,40);
    }

    @Override
    public int getMaxCombo() {
        return 2;
    }

    @Override
    public void resetCooldownAttack() {
        this.attackTimer = this.cooldownAttack[this.getCombo(this.replaced)-1];
    }

    public void playSonicBoom(){
        this.sonicBoomAnimTimer=60;
        if(!this.replaced.level.isClientSide){
            PacketHandler.sendToAllTracking(new PacketSyncAnimAttack(this.replaced,1),this.replaced);
        }
    }

    public int getSonicBoomAnimTimer() {
        return this.sonicBoomAnimTimer;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, EasingType.EaseInElastic, state -> {
            Warden warden = getWardenFromState(state);
            ReplacedWarden<?> replacedWarden = getPatch(warden, ReplacedWarden.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (warden == null || warden.hasPose(Pose.DIGGING) || warden.hasPose(Pose.EMERGING)) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            boolean isAgressive=warden.getClientAngerLevel()>40;
            if(replacedWarden.getAttackTimer()>0){
                state.getController().setAnimationSpeed(replacedWarden.getCombo(warden)==2 ? 1.5D : 1.0D);
                state.getController().setAnimation(builder.playOnce(replacedWarden.getCombo(warden)==2 ?"warden.attack2":"warden.attack1"));
            }else if(replacedWarden.getSonicBoomAnimTimer()>0){
                state.getController().setAnimationSpeed(1.5D);
                state.getController().setAnimation(builder.playOnce("warden.boom"));
            }else{
                state.getController().setAnimationSpeed(0.5D);
                state.getController().setAnimation(builder.loop(isAgressive ? "warden.move2" :"warden.idle"));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_legs", 0, state -> {
            Warden warden = getWardenFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (warden == null) return PlayState.STOP;
            if(!state.isMoving() ){
                state.getController().setAnimationSpeed(2.0D);
                state.getController().setAnimation(builder.loop( "warden.wardenlegs2"));
            }else if(!warden.hasPose(Pose.DIGGING) && !warden.hasPose(Pose.EMERGING)){
                state.getController().setAnimationSpeed(0.5D);
                state.getController().setAnimation(builder.loop("warden.wardenlegs1"));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_pose", 0, EasingType.EaseInOutQuad, state -> {
            Warden warden = getWardenFromState(state);
            ReplacedWarden<?> replacedWarden = getPatch(warden, ReplacedWarden.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (warden == null || replacedWarden.getAttackTimer()>0 || replacedWarden.getSonicBoomAnimTimer()>0) return PlayState.STOP;
            if(warden.getPose().equals(Pose.SNIFFING)){
                state.getController().setAnimationSpeed(1D);
                state.getController().setAnimation(builder.loop("warden.sniff"));
            }else if (warden.getPose().equals(Pose.DIGGING)){
                state.getController().setAnimationSpeed(0.43D);
                state.getController().setAnimation(builder.playOnce("warden.dig"));
            }else if (warden.getPose().equals(Pose.EMERGING)){
                state.getController().setAnimationSpeed(0.45D);
                state.getController().setAnimation(builder.playOnce("warden.emerge"));
            }else if (warden.getPose().equals(Pose.ROARING)){
                state.getController().setAnimationSpeed(1.0D);
                state.getController().setAnimation(builder.playOnce("warden.roar"));
            }

            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    public Warden getWardenFromState(AnimationEvent<ReplacedWarden<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Warden enderman)) return null;
        return enderman;
    }

}
