package com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.qrk;

import com.TBK.combat_integration.server.modbusevent.cap.Capabilities;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import com.izofar.bygonenether.entity.WitherSkeletonKnight;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.BowItem;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.easing.EasingType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;
import vazkii.quark.content.mobs.entity.Forgotten;

import javax.annotation.Nullable;
import java.util.List;

public class ReplacedForgotten<T extends Forgotten> extends ReplacedSkeleton<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, EasingType.EaseInElastic, state -> {
            AbstractSkeleton zombie = getZombieFromState(state);
            ReplacedEntity<?> replacedPiglin = Capabilities.getEntityPatch(zombie, ReplacedForgotten.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null ||( zombie instanceof WitherSkeletonKnight knight && knight.isUsingShield())){
                state.getController().clearAnimationCache();
                return PlayState.STOP;
            }
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            boolean isAim = this.isWieldingTwoHandedWeapon(zombie) && zombie.isAggressive();

            if(zombie.getMainHandItem().getItem() instanceof BowItem){
                if (isMove) {
                    state.getController().setAnimationSpeed(zombie.isAggressive()?3.0F : 4.0F);
                    state.getController().setAnimation(builder.loop("skeleton.move"+(!isAim ? "" : "2")));
                }else {
                    state.getController().setAnimationSpeed(1.0F);
                    state.getController().setAnimation(builder.loop(isAim ?"skeleton.aim" :"skeleton.idle"));
                }
            }else {
                if (isMove && replacedPiglin.getAttackTimer()==0) {
                    state.getController().setAnimationSpeed(2.0F);
                    state.getController().setAnimation(new AnimationBuilder().loop("skeleton.movemelee"));
                }else if(replacedPiglin.getAttackTimer()>0) {
                    state.getController().setAnimationSpeed(2F);
                    state.getController().setAnimation(new AnimationBuilder().playAndHold("skeleton.attack"+replacedPiglin.getCombo(zombie)));
                }else{
                    state.getController().setAnimationSpeed(1F);
                    state.getController().setAnimation(new AnimationBuilder().loop("skeleton.idle"));
                }
            }

            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new AnimationController<>(this, "controller_block", 0, state -> {
            AbstractSkeleton zombie = getZombieFromState(state);
            AnimationBuilder builder=new AnimationBuilder();

            if (zombie == null || (zombie instanceof WitherSkeletonKnight knight && !knight.isUsingShield())){
                state.getController().clearAnimationCache();
                return PlayState.STOP;
            }

            if(zombie instanceof WitherSkeletonKnight knight && knight.isUsingShield()){
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.loop("skeleton.block"));
            }

            return PlayState.CONTINUE;
        }));

        data.addAnimationController(new AnimationController<>(this, "controller_legs", 10, state -> {
            AbstractSkeleton zombie = getZombieFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null) return PlayState.STOP;
            boolean isAim = this.isWieldingTwoHandedWeapon(zombie) && zombie.isAggressive();
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);

            if (isMove) {
                state.getController().setAnimationSpeed(zombie.isAggressive()?4F : 2F);
                state.getController().setAnimation(builder.loop(!isAim ? "skeleton.legsmelee1" :  "skeleton.legsbow1"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("skeleton.legs0", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
    }
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
    @Nullable
    private AbstractSkeleton getZombieFromState(AnimationEvent<ReplacedForgotten<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof AbstractSkeleton enderman)) return null;
        return enderman;
    }
}
