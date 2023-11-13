package com.TBK.better_animation_mob.server.modbusevent.entity;

import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.raid.Raider;
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

public class ReplacedIllagers extends ReplacedEntity {
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            AbstractIllager raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            if(raider.isPassenger()){
                builder.addAnimation("raider.sit");
            }
            if(raider.getArmPose().equals(AbstractIllager.IllagerArmPose.ATTACKING) && raider.getAttackAnim(state.getPartialTick()) == 0){
                builder.addAnimation("raider.move2");
            }
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove && raider.getAttackAnim(state.getPartialTick()) == 0) {
                state.getController().setAnimationSpeed(raider.isAggressive()?3.0F : 1.0F);
                state.getController().setAnimation(builder.addAnimation("raider.move", ILoopType.EDefaultLoopTypes.LOOP));
            }else if(raider.getAttackAnim(state.getPartialTick())>0) {
                state.getController().setAnimationSpeed(8.0F);
                state.getController().setAnimation(builder.playOnce("raider.attack"));
            }else {
                state.getController().clearAnimationCache();
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("raider.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
    }


    @Nullable
    private AbstractIllager getRaiderFromState(AnimationEvent<ReplacedIllagers> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof AbstractIllager enderman)) return null;
        return enderman;
    }
}
