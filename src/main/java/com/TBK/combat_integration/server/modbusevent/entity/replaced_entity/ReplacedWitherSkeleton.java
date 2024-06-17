package com.TBK.combat_integration.server.modbusevent.entity.replaced_entity;

import com.TBK.combat_integration.server.modbusevent.api.ICombos;
import com.TBK.combat_integration.server.modbusevent.cap.Capabilities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.BowItem;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class ReplacedWitherSkeleton<T extends WitherSkeleton> extends ReplacedSkeleton<T>{
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

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
        data.addAnimationController(new AnimationController<>(this, "controller", 10, state -> {
            AbstractSkeleton zombie = getZombieFromState(state);
            ReplacedEntity<?> replaced = Capabilities.getEntityPatch(zombie,ReplacedWitherSkeleton.class);
            if (zombie == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);

            if(zombie.getMainHandItem().getItem() instanceof BowItem){
                if (isMove) {
                    state.getController().setAnimationSpeed(zombie.isAggressive()?3.0F : 4.0F);
                    state.getController().setAnimation(new AnimationBuilder().loop("skeleton.moveAim"));
                }else {
                    state.getController().setAnimationSpeed(1.0F);
                    state.getController().setAnimation(new AnimationBuilder().loop(zombie.isAggressive() ? "skeleton.idleAim" : "skeleton.idleBow"));
                }
            }else {
                if (isMove && replaced.getAttackTimer()==0) {
                    state.getController().setAnimationSpeed(zombie.isAggressive()?3.0F : 4.0F);
                    state.getController().setAnimation(new AnimationBuilder().loop("skeleton.moveAlt"));
                }else if(replaced.getAttackTimer()>0){
                    state.getController().setAnimationSpeed(2.5D);
                    state.getController().setAnimation(new AnimationBuilder().playAndHold("skeleton.attackAlt"+((ICombos)zombie).getCombo()));
                }else {
                    state.getController().setAnimationSpeed(1.0F);
                    state.getController().setAnimation(new AnimationBuilder().loop("skeleton.idleAlt"));
                }
            }

            return PlayState.CONTINUE;
        }));
    }
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    private AbstractSkeleton getZombieFromState(AnimationEvent<ReplacedWitherSkeleton<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof AbstractSkeleton enderman)) return null;
        return enderman;
    }
}
