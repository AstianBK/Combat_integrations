package com.TBK.combat_integration.server.modbusevent.entity.replaced_entity;

import com.TBK.combat_integration.server.modbusevent.api.ICombos;
import com.TBK.combat_integration.server.modbusevent.cap.Capabilities;
import com.TBK.combat_integration.server.modbusevent.network.PacketHandler;
import com.TBK.combat_integration.server.modbusevent.network.message.PacketSyncAnimAttack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ReplacedEntity <T extends Mob> implements IAnimatable {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);

    protected int attackTimer;
    protected T replaced;

    protected int[] cooldownAttack=new int[]{
            24,
            24,
            24
    };
    public void onJoinGame(LivingEntity entity, EntityJoinLevelEvent event){
        replacedGoals();
        replacedBehavior();
    }

    public void init(Entity entity){
        this.replaced = (T) entity;
    }
    public void onTick(Level level){
        if(level.isClientSide){
            onClientTick();
        }else {
            onServerTick();
        }
        if(this.attackTimer>0){
            this.attackTimer--;
            if(this.attackTimer==isMomentHurt()){
                LivingEntity target = this.replaced.getTarget();
                if(target!=null){
                    this.replaced.doHurtTarget(target);
                }
            }
        }
    }

    protected void replacedGoals(){

    }

    protected void replacedBehavior(){
    }

    public int isMomentHurt(){
        return 0;
    }

    protected void onServerTick() {

    }

    protected void onClientTick() {

    }

    public int getCombo(LivingEntity livingEntity){
        return livingEntity instanceof ICombos ?((ICombos)livingEntity).getCombo():1;
    }

    public void playAttack(){
        this.plusCombo();
        this.resetCooldownAttack();
        if(!this.replaced.level.isClientSide){
            PacketHandler.sendToAllTracking(new PacketSyncAnimAttack(this.replaced,0),this.replaced);
        }
    }

    public void plusCombo() {
        if(this.replaced instanceof ICombos entity){
            if(entity.getCombo() < getMaxCombo()) {
                entity.setCombo(entity.getCombo()+1);
            }else {
                entity.setCombo(1);
            }
        }
    }

    public int getMaxCombo(){
        return 3;
    }

    public void resetCooldownAttack(){
        this.attackTimer=24;
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }
    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public <P extends ReplacedEntity<T>> P getPatch(LivingEntity replaced,Class<P> pClass){
        return Capabilities.getEntityPatch(replaced,pClass);
    }
}
