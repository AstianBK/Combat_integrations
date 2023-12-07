package com.TBK.better_animation_mob.server.modbusevent.entity.svr;

import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedVindicator;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Griefer;
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

public class ReplacedGriefer implements IAnimatable {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, state -> {
            Griefer raider = getRaiderFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (raider == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if (isMove && raider.getAttackAnim(state.getPartialTick()) == 0 && raider.getKickTicks()==0) {
                state.getController().setAnimationSpeed(raider.isAggressive()?2.0F : 1.0F);
                state.getController().setAnimation(builder.addAnimation("griefer.move", ILoopType.EDefaultLoopTypes.LOOP));
            }else if(raider.getAttackAnim(state.getPartialTick())>0) {
                state.getController().setAnimationSpeed(7F);
                state.getController().setAnimation(builder.loop("griefer.attack2"));
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
    private Griefer getRaiderFromState(AnimationEvent<ReplacedGriefer> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Griefer enderman)) return null;
        return enderman;
    }
}
