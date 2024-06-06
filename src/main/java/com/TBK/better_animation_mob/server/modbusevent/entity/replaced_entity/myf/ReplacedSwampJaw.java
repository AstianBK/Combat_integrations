package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.myf;

import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import lykrast.meetyourfight.entity.SwampjawEntity;
import lykrast.meetyourfight.entity.SwampjawEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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

public class ReplacedSwampJaw<T extends SwampjawEntity> extends ReplacedEntity<T> {
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
            SwampjawEntity zombie = getZombieFromState(state);
            ReplacedSwampJaw<?> replacedZombie = getPatch(zombie, ReplacedSwampJaw.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);

            if (isMove) {
                state.getController().setAnimationSpeed(zombie.isAggressive()?1.5F : 1.4F);
                state.getController().setAnimation(builder.loop( "swampjaw.move"));
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    private SwampjawEntity getZombieFromState(AnimationEvent<ReplacedSwampJaw<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof SwampjawEntity enderman)) return null;
        return enderman;
    }

}
