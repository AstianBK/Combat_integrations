package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity;

import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
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

public class ReplacedSkeleton<T extends AbstractSkeleton> extends ReplacedEntity<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            AbstractSkeleton zombie = getZombieFromState(state);
            ReplacedEntity<?> replacedPiglin = Capabilities.getEntityPatch(zombie,ReplacedSkeleton.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null) return PlayState.STOP;
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
                    state.getController().setAnimationSpeed(2.5D);
                    state.getController().setAnimation(new AnimationBuilder().playAndHold("skeleton.attack"+replacedPiglin.getCombo(zombie)));
                }else{
                    state.getController().setAnimationSpeed(1F);
                    state.getController().setAnimation(new AnimationBuilder().loop("skeleton.idle"));
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
    private AbstractSkeleton getZombieFromState(AnimationEvent<ReplacedSkeleton<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof AbstractSkeleton enderman)) return null;
        return enderman;
    }

    public boolean isWieldingTwoHandedWeapon(LivingEntity living) {
        return (living.getMainHandItem().getItem() instanceof ProjectileWeaponItem
                || living.getOffhandItem().getItem() instanceof ProjectileWeaponItem
                || living.getMainHandItem().getUseAnimation() == UseAnim.BOW
                || living.getOffhandItem().getUseAnimation() == UseAnim.BOW);
    }
}
