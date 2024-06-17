package com.TBK.combat_integration.server.modbusevent.entity.replaced_entity;

import com.TBK.combat_integration.server.modbusevent.ModBusEvent;
import com.TBK.combat_integration.server.modbusevent.cap.Capabilities;
import com.TBK.combat_integration.server.modbusevent.entity.goals.AttackAGoal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Vindicator;
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

public class ReplacedVindicator<T extends Vindicator> extends ReplacedEntity<T> {
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

        this.replaced.goalSelector.addGoal(4,new AttackAGoal<>(this.replaced,1.0D,false,this){
            protected double getAttackReachSqr(LivingEntity p_34125_) {
                if (this.mob.getVehicle() instanceof Ravager) {
                    float f = this.mob.getVehicle().getBbWidth() - 0.1F;
                    return (double)(f * 2.0F * f * 2.0F + p_34125_.getBbWidth());
                } else {
                    return super.getAttackReachSqr(p_34125_);
                }
            }
        });

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            AbstractIllager raider = getRaiderFromState(state);
            ReplacedVindicator<?> replacedVindicator = Capabilities.getEntityPatch(raider,ReplacedVindicator.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;

            if(raider.isAggressive() && replacedVindicator.getAttackTimer() == 0){
                builder.loop("vindicator.move2");
            }
            if(raider.hurtTime>0){
                state.getController().setAnimationSpeed(3.0F);
                state.getController().setAnimation(builder.playOnce("vindicator.hurt"));
                return PlayState.CONTINUE;
            }
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove && replacedVindicator.getAttackTimer() == 0) {
                state.getController().setAnimationSpeed(raider.isAggressive()?3.0F : 1.0F);
                state.getController().setAnimation(builder.addAnimation(!raider.isAggressive() ? "vindicator.move" : "vindicator.move2", ILoopType.EDefaultLoopTypes.LOOP));
            }else if(replacedVindicator.getAttackTimer()>0) {
                state.getController().setAnimationSpeed(8.0F);
                state.getController().setAnimation(builder.playAndHold("vindicator.attack"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("vindicator.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    @Nullable
    private AbstractIllager getRaiderFromState(AnimationEvent<ReplacedVindicator<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof AbstractIllager enderman)) return null;
        return enderman;
    }
}
