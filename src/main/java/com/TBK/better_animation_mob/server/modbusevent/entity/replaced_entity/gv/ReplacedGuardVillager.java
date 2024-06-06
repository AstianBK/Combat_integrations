package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.gv;

import com.TBK.better_animation_mob.server.modbusevent.ModBusEvent;
import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.AttackAGoal;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;
import tallestegg.guardvillagers.entities.Guard;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReplacedGuardVillager<T extends Guard> extends ReplacedEntity<T> {
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
            public boolean canUse() {
                return !this.mob.isHolding((is) -> {
                    return is.getItem() instanceof CrossbowItem;
                }) && !this.mob.isHolding((is) -> {
                    return is.getItem() instanceof BowItem;
                }) && this.mob.getTarget() != null && !((Guard)this.mob).isEating() && super.canUse();
            }

            public boolean canContinueToUse() {
                return super.canContinueToUse() && this.mob.getTarget() != null;
            }

            public void tick() {
                LivingEntity target = this.mob.getTarget();
                if (target != null) {
                    if ((double)target.distanceTo(this.mob) <= 3.0 && !this.mob.isBlocking()) {
                        this.mob.getMoveControl().strafe(-2.0F, 0.0F);
                        this.mob.lookAt(target, 30.0F, 30.0F);
                    }

                    if (this.path != null && (double)target.distanceTo(this.mob) <= 2.0) {
                        this.mob.getNavigation().stop();
                    }

                    super.tick();
                }

            }

            protected double getAttackReachSqr(LivingEntity attackTarget) {
                return super.getAttackReachSqr(attackTarget) * 3.55;
            }

            protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
                double d0 = this.getAttackReachSqr(enemy);
                if (distToEnemySqr <= d0 && this.ticksUntilNextAttack <= 0) {
                    this.resetAttackCooldown();
                    this.mob.stopUsingItem();
                    if (this.mob.shieldCoolDown == 0) {
                        this.mob.shieldCoolDown = 8;
                    }
                    this.patchMob.playAttack();
                }

            }
        });

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            Guard raider = getRaiderFromState(state);
            ReplacedGuardVillager<?> replacedVindicator = Capabilities.getEntityPatch(raider, ReplacedGuardVillager.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            boolean isAim = this.isWieldingTwoHandedWeapon(raider);
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if(isAim){
                if(isMove){
                    state.getController().setAnimationSpeed(raider.isAggressive()?3.0F : 1.0F);
                    state.getController().setAnimation(builder.addAnimation("guardvillager.walkrangednoshield", ILoopType.EDefaultLoopTypes.LOOP));
                }else {
                    state.getController().setAnimationSpeed(1.0F);
                    state.getController().setAnimation(builder.addAnimation("guardvillager.aim", ILoopType.EDefaultLoopTypes.LOOP));
                }
            }else {
                if (isMove && replacedVindicator.getAttackTimer() == 0) {
                    state.getController().setAnimationSpeed(raider.isAggressive()?3.0F : 1.0F);
                    state.getController().setAnimation(builder.addAnimation("guardvillager.walkmelee", ILoopType.EDefaultLoopTypes.LOOP));
                }else if(replacedVindicator.getAttackTimer()>0) {
                    state.getController().setAnimationSpeed(1.0F);
                    state.getController().setAnimation(builder.playAndHold("guardvillager.attackmelee"));
                }
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_legs", 0, state -> {
            Guard warden = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if(warden==null){
                return PlayState.STOP;
            }
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            state.getController().transitionLengthTicks=0.0f;
            if(isMove){
                state.getController().setAnimationSpeed(warden.isAggressive() ?4.0D :2.0D);
                state.getController().setAnimation(builder.loop( warden.isAggressive() ? "guardvillager.legs2": "guardvillager.legs3"));
            }else{
                state.getController().setAnimationSpeed(0.5D);
                state.getController().setAnimation(builder.loop("guardvillager.legs1"));
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    @Nullable
    private Guard getRaiderFromState(AnimationEvent<ReplacedGuardVillager<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Guard enderman)) return null;
        return enderman;
    }

    public boolean isWieldingTwoHandedWeapon(LivingEntity living) {
        return (living.getMainHandItem().getItem() instanceof ProjectileWeaponItem
                || living.getOffhandItem().getItem() instanceof ProjectileWeaponItem
                || living.getMainHandItem().getUseAnimation() == UseAnim.BOW
                || living.getOffhandItem().getUseAnimation() == UseAnim.BOW);
    }
}
