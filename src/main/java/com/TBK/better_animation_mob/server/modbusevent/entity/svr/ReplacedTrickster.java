package com.TBK.better_animation_mob.server.modbusevent.entity.svr;

import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedEntity;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Trickster;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.List;

public class ReplacedTrickster extends ReplacedEntity {
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, state -> {
            Trickster raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if(raider.hurtTime>0){
                state.getController().setAnimationSpeed(3.0F);
                state.getController().setAnimation(builder.playOnce("trickster.hurt"));
                return PlayState.CONTINUE;
            }
            if (isMove) {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("trickster.move"));
            } else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("trickster.idle"));
            }

            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_attack", 0, state -> {
            Trickster raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            if(!raider.isCastingSpell()) {
                return PlayState.CONTINUE;
            }
            state.getController().setAnimationSpeed(0.5F);
            state.getController().setAnimation(builder.loop("trickster.spell"));

            return PlayState.CONTINUE;
        }));
    }


    @Nullable
    private Trickster getRaiderFromState(AnimationEvent<ReplacedTrickster> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Trickster enderman)) return null;
        return enderman;
    }
}
