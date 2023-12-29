package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity;

import com.TBK.better_animation_mob.server.modbusevent.ModBusEvent;
import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.MeleeAttackPatch;
import com.TBK.better_animation_mob.server.modbusevent.network.PacketHandler;
import com.TBK.better_animation_mob.server.modbusevent.network.message.PacketSyncAnimAttack;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
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

public class ReplacedPiglin<T extends AbstractPiglin> extends ReplacedEntity<T> {
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
        ModBusEvent.replaceBehavior(this.replaced.getBrain(),Activity.FIGHT, 12,MeleeAttackPatch.class,new MeleeAttackPatch(this));
    }

    public void resetCooldownAttack(){
        this.attackTimer=10;
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    private PlayState predicate(AnimationEvent<ReplacedPiglin<T>> state) {
        AbstractPiglin piglin = this.getRaiderFromState(state);
        ReplacedEntity<?> replacedPiglin = Capabilities.getEntityPatch(piglin,ReplacedPiglin.class);
        if(piglin==null){
            System.out.print("\n----STOP----\n");
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
    private AbstractPiglin getRaiderFromState(AnimationEvent<ReplacedPiglin<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof AbstractPiglin enderman)) return null;
        return enderman;
    }
}
