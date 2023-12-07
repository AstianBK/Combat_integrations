package com.TBK.better_animation_mob.server.modbusevent.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
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

public class ReplacedVindicator implements IAnimatable {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            AbstractIllager raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;

            if(raider.getArmPose().equals(AbstractIllager.IllagerArmPose.ATTACKING) && raider.getAttackAnim(state.getPartialTick()) == 0){
                builder.addAnimation("vindicator.move2");
            }
            if(raider.hurtTime>0){
                state.getController().setAnimationSpeed(3.0F);
                state.getController().setAnimation(builder.playOnce("vindicator.hurt"));
                return PlayState.CONTINUE;
            }
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove && raider.getAttackAnim(state.getPartialTick()) == 0) {
                state.getController().setAnimationSpeed(raider.isAggressive()?3.0F : 1.0F);
                state.getController().setAnimation(builder.addAnimation("vindicator.move", ILoopType.EDefaultLoopTypes.LOOP));
            }else if(raider.getAttackAnim(state.getPartialTick())>0) {
                state.getController().setAnimationSpeed(8.0F);
                state.getController().setAnimation(builder.playOnce("vindicator.attack"));
            }else {
                state.getController().clearAnimationCache();
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
    private AbstractIllager getRaiderFromState(AnimationEvent<ReplacedVindicator> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof AbstractIllager enderman)) return null;
        return enderman;
    }
}
