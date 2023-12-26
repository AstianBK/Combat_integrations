package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.svr;

import com.TBK.better_animation_mob.server.modbusevent.ModBusEvent;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.AttackAGoal;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Griefer;
import com.teamabnormals.savage_and_ravage.common.item.CreeperSporesItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Ravager;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReplacedGriefer<T extends Griefer> extends ReplacedEntity<T> {
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
                return !(this.mob.getMainHandItem().getItem() instanceof CreeperSporesItem) && super.canUse();
            }
        });

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            Griefer raider = getRaiderFromState(state);
            ReplacedGriefer replacedGriefer = getPatch(raider, ReplacedGriefer.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove && replacedGriefer.getAttackTimer() == 0 && raider.getKickTicks()==0) {
                state.getController().setAnimationSpeed(raider.isAggressive()?2.0F : 1.0F);
                state.getController().setAnimation(builder.addAnimation("griefer.move", ILoopType.EDefaultLoopTypes.LOOP));
            }else if(replacedGriefer.getAttackTimer()>0) {
                state.getController().setAnimationSpeed(5F);
                state.getController().setAnimation(builder.playAndHold("griefer.attack2"));
            }else if(raider.getKickTicks()>0) {
                state.getController().setAnimationSpeed(5F);
                state.getController().setAnimation(builder.playOnce("griefer.attack"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("griefer.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    private Griefer getRaiderFromState(AnimationEvent<ReplacedGriefer<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Griefer enderman)) return null;
        return enderman;
    }
}
