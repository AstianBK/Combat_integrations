package com.TBK.combat_integration.server.modbusevent.entity.replaced_entity;

import com.TBK.combat_integration.server.modbusevent.ModBusEvent;
import com.TBK.combat_integration.server.modbusevent.api.ICombos;
import com.TBK.combat_integration.server.modbusevent.entity.goals.AttackAGoal;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
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

public class ReplacedZombie<T extends Zombie> extends ReplacedEntity<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);

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

    @Override
    public int isMomentHurt() {
        return 5;
    }

    @Override
    public void resetCooldownAttack() {
        this.attackTimer=10;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            Zombie zombie = getZombieFromState(state);
            ReplacedZombie<?> replacedZombie = getPatch(zombie,ReplacedZombie.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if(zombie.getUseItem().is(Items.TRIDENT) && zombie.isAggressive()) {
                state.getController().setAnimationSpeed(1.0F);
                builder.addAnimation("zombie.aim");
            }

            if (isMove && replacedZombie.getAttackTimer() == 0) {
                state.getController().setAnimationSpeed(zombie.isAggressive()?1.5F : 1.4F);
                state.getController().setAnimation(builder.loop( zombie.isAggressive() ? "zombie.move2" : "zombie.move"));
            }else if(replacedZombie.getAttackTimer()>0) {
                state.getController().setAnimationSpeed(4F);
                state.getController().setAnimation(builder.playOnce(zombie instanceof ICombos ?( "zombie.attack"+((ICombos) zombie).getCombo()): "zombie.attack1"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("zombie.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    private Zombie getZombieFromState(AnimationEvent<ReplacedZombie<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Zombie enderman)) return null;
        return enderman;
    }

}
