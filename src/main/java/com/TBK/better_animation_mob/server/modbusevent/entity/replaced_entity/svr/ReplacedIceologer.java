package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.svr;

import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Iceologer;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Trickster;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class ReplacedIceologer<T extends Iceologer> extends ReplacedEntity<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            Iceologer raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove) {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.move"));
            }else if(raider.isCastingSpell()) {
                state.getController().setAnimationSpeed(1F);
                state.getController().setAnimation(builder.playOnce("evoker.spellvex"));
            } else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.idle"));
            }

            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_legs", 10, state -> {
            Iceologer raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove) {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.legs1"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("evoker.legs2"));
            }

            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    @Nullable
    private Iceologer getRaiderFromState(AnimationEvent<ReplacedIceologer<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Iceologer enderman)) return null;
        return enderman;
    }
}
