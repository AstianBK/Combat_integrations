package com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.qrk;

import com.TBK.better_animation_mob.server.modbusevent.ModBusEvent;
import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.AttackAGoal;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedZombie;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;
import vazkii.quark.content.mobs.entity.Wraith;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReplacedWraith<T extends Wraith> extends ReplacedZombie<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            Zombie zombie = getZombieFromState(state);
            ReplacedWraith<?> replacedZombie = getPatch(zombie, ReplacedWraith.class);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null) return PlayState.STOP;
            boolean isMove= !(state.getLimbSwingAmount() > -0.15F && state.getLimbSwingAmount() < 0.15F);

            if (isMove && replacedZombie.getAttackTimer() == 0) {
                state.getController().setAnimationSpeed(zombie.isAggressive()?1.5F : 1.4F);
                state.getController().setAnimation(builder.loop( zombie.isAggressive() ? "zombie.move2" : "zombie.move"));
            }else if(replacedZombie.getAttackTimer()>0) {
                state.getController().setAnimationSpeed(4F);
                state.getController().setAnimation(builder.playOnce(zombie instanceof ICombos ?( "zombie.attack"+((ICombos) zombie).getCombo()): "zombie.attack1"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("zombie.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    private Wraith getZombieFromState(AnimationEvent<ReplacedWraith<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof Wraith enderman)) return null;
        return enderman;
    }

}
