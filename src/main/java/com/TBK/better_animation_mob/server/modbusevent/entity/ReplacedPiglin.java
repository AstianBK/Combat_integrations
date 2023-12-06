package com.TBK.better_animation_mob.server.modbusevent.entity;

import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.List;

public class ReplacedPiglin extends ReplacedEntity {
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, state -> {
            AbstractPiglin raider = getRaiderFromState(state);
            if (raider == null) return PlayState.STOP;
            boolean hasCrossbow=raider.getMainHandItem().is(Items.CROSSBOW);
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if(hasCrossbow){
                if (isMove) {
                    state.getController().setAnimationSpeed(raider.isAggressive()?5.0F : 1.0F);
                    state.getController().setAnimation(new AnimationBuilder().addAnimation("piglin.moveAim", ILoopType.EDefaultLoopTypes.LOOP));
                }else {
                    state.getController().setAnimationSpeed(1.0F);
                    state.getController().setAnimation(new AnimationBuilder().addAnimation("piglin.idleAim", ILoopType.EDefaultLoopTypes.LOOP));
                }
            }else {
                if (isMove && raider.getAttackAnim(state.getPartialTick()) == 0) {
                    state.getController().setAnimationSpeed(raider.isAggressive()?5.0F : 2.0F);
                    state.getController().setAnimation(new AnimationBuilder().addAnimation(raider.isAggressive()?"piglin.move2":"piglin.move", ILoopType.EDefaultLoopTypes.LOOP));
                }else if(raider.getAttackAnim(state.getPartialTick())>0) {
                    state.getController().setAnimationSpeed(3F);
                    state.getController().setAnimation(new AnimationBuilder().playAndHold(("piglin.attack"+((ICombos)raider).getCombo())));
                }else {
                    state.getController().setAnimationSpeed(1.0F);
                    state.getController().setAnimation(new AnimationBuilder().addAnimation("piglin.idle", ILoopType.EDefaultLoopTypes.LOOP));
                }
            }

            return PlayState.CONTINUE;
        }));
    }


    @Nullable
    private AbstractPiglin getRaiderFromState(AnimationEvent<ReplacedPiglin> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof AbstractPiglin enderman)) return null;
        return enderman;
    }
}
