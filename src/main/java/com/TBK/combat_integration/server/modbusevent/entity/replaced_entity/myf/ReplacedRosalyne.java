package com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.myf;

import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import lykrast.meetyourfight.entity.RosalyneEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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

public class ReplacedRosalyne<T extends RosalyneEntity> extends ReplacedEntity<T> {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    protected void replacedGoals() {
    }

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
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            RosalyneEntity zombie = getZombieFromState(state);
            AnimationBuilder builder=new AnimationBuilder();
            if (zombie == null) return PlayState.STOP;

            if(zombie.clientAnim==3 && zombie.prevAnim!=1 && zombie.prevAnim!=4) {
                state.getController().setAnimationSpeed(1F);
                state.getController().setAnimation(builder.loop( "rosalyne.pose_attack2"));
            }else if(zombie.getAnimation()==1 || (zombie.getAnimation()==3 && zombie.prevAnim==4)) {
                state.getController().setAnimationSpeed(1F);
                state.getController().setAnimation(builder.playOnce( "rosalyne.attack3"));
            }else if(zombie.clientAnim!=1 && (zombie.prevAnim==2 || zombie.prevAnim==4)) {
                state.getController().setAnimationSpeed(1F);
                state.getController().setAnimation(builder.loop( "rosalyne.pose_attack3"));
            }else if(zombie.getAnimation()==9) {
                state.getController().setAnimationSpeed(1F);
                state.getController().setAnimation(builder.loop( "rosalyne.pose_attack1"));
            }else if(zombie.clientAnim==2 || zombie.clientAnim==4 || zombie.clientAnim==3) {
                state.getController().setAnimationSpeed(1F);
                state.getController().setAnimation(builder.playOnce( "rosalyne.attack2"));
            }else if(zombie.getAnimation()==10) {
                state.getController().setAnimationSpeed(3F);
                state.getController().setAnimation(builder.playOnce( "rosalyne.attack1"));
            }else if(zombie.getAnimation()==7) {
                state.getController().setAnimationSpeed(1F);
                state.getController().setAnimation(builder.playOnce( "rosalyne.spell1"));
            }else {
                state.getController().setAnimation(builder.addAnimation("rosalyne.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }));

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    private RosalyneEntity getZombieFromState(AnimationEvent<ReplacedRosalyne<T>> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof RosalyneEntity enderman)) return null;
        return enderman;
    }

}
