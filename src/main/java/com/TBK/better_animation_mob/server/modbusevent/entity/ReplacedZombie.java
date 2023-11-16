package com.TBK.better_animation_mob.server.modbusevent.entity;

import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.UseAnim;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;
import java.util.List;

public class ReplacedZombie extends ReplacedEntity {

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            Zombie zombie = getZombieFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);
            if(zombie.getUseItem().is(Items.TRIDENT) && zombie.isAggressive()) {
                state.getController().setAnimationSpeed(1.0F);
                builder.addAnimation("zombie.aim");
            }
            if(zombie.isPassenger()){
                builder.addAnimation("zombie.sit");
            }

            if (isMove && zombie.getAttackAnim(state.getPartialTick()) == 0 && !zombie.isPassenger()) {
                state.getController().setAnimationSpeed(zombie.isAggressive()?1.5F : 1.4F);
                state.getController().setAnimation(builder.loop( zombie.isAggressive() ? "zombie.move2" : "zombie.move"));
            }else if(zombie.getAttackAnim(state.getPartialTick())>0) {
                state.getController().setAnimationSpeed(2.5F);
                state.getController().setAnimation(builder.playOnce(zombie instanceof ICombos ?( "zombie.attack"+((ICombos) zombie).getCombo()): "zombie.attack1"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("zombie.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
    }
    @Nullable
    private Zombie getZombieFromState(AnimationEvent<ReplacedZombie> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Zombie enderman)) return null;
        return enderman;
    }

}
