package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.dm;

import com.TBK.better_animation_mob.server.modbusevent.ModBusEvent;
import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.MeleeAttackPatch;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedPiglin;
import com.infamous.dungeons_mobs.entities.piglin.FungusThrowerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
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
import java.util.List;

public class ReplacedFungusThrower<T extends FungusThrowerEntity> extends ReplacedPiglin<T> {
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
        return p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isPresent() ? p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get() : null;
    }

    @Override
    protected void replacedBehavior() {
        ModBusEvent.removeBehavior(this.replaced.getBrain(),Activity.FIGHT,12, MeleeAttack.class);
        ModBusEvent.replaceBehavior(this.replaced.getBrain(),Activity.FIGHT, 12,MeleeAttackPatch.class,new MeleeAttackPatch(this,24,24));
    }

    public void resetCooldownAttack(){
        this.attackTimer=10;
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    private PlayState predicate(AnimationEvent<ReplacedFungusThrower<T>> state) {
        FungusThrowerEntity piglin = this.getRaiderFromState(state);
        if(piglin==null){
            return PlayState.STOP;
        }
        boolean isMove=!(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
        if (isMove && piglin.getAttackAnim(state.getPartialTick())==0) {
            state.getController().setAnimationSpeed(piglin.isAggressive()?5.0F : 2.0F);
            state.getController().setAnimation(new AnimationBuilder().loop("piglin.move"));
        }else if(piglin.getAttackAnim(state.getPartialTick())>0) {
            state.getController().setAnimationSpeed(1.0D);
            state.getController().setAnimation(new AnimationBuilder().playAndHold("piglin.throw"));
        }else{
            state.getController().setAnimationSpeed(1F);
            state.getController().setAnimation(new AnimationBuilder().loop("piglin.idle"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    @Nullable
    private FungusThrowerEntity getRaiderFromState(AnimationEvent<ReplacedFungusThrower<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof FungusThrowerEntity enderman)) return null;
        return enderman;
    }
}
