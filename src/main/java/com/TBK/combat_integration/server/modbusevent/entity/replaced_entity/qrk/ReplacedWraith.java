package com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.qrk;

import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedZombie;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
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
import java.util.List;

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
                state.getController().setAnimation(builder.loop( "wraith.move"));
            }else if(replacedZombie.getAttackTimer()>0) {
                state.getController().setAnimationSpeed(1F);
                state.getController().setAnimation(builder.playOnce("wraith.attack"));
            }else {
                state.getController().setAnimationSpeed(1.0F);
                state.getController().setAnimation(builder.addAnimation("wraith.idle", ILoopType.EDefaultLoopTypes.LOOP));
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
