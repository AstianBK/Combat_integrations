package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.myf;

import com.TBK.better_animation_mob.server.modbusevent.ModBusEvent;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.AttackAGoal;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import lykrast.meetyourfight.entity.DameFortunaEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
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

public class ReplacedFortuna<T extends DameFortunaEntity> extends ReplacedEntity<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    protected void replacedGoals() {
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
            DameFortunaEntity zombie = getZombieFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null || zombie.getAttack()==0) return PlayState.STOP;

            if(zombie.getAttack()>0) {
                state.getController().setAnimationSpeed(1F);
                state.getController().setAnimation(builder.loop( "fortuna.attack"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("fortuna.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_head", 0, state -> {
            DameFortunaEntity zombie = getZombieFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null) return PlayState.STOP;

            state.getController().setAnimationSpeed(1.0F);
            state.getController().setAnimation(builder.addAnimation("fortuna.head1", ILoopType.EDefaultLoopTypes.LOOP));

            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    private DameFortunaEntity getZombieFromState(AnimationEvent<ReplacedFortuna<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof DameFortunaEntity enderman)) return null;
        return enderman;
    }

}
