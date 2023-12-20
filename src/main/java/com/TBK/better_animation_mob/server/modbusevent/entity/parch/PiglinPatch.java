package com.TBK.better_animation_mob.server.modbusevent.entity.parch;

import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import com.TBK.better_animation_mob.server.modbusevent.network.PacketHandler;
import com.TBK.better_animation_mob.server.modbusevent.network.message.PacketSyncAnimAttack;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

public class PiglinPatch<T extends Piglin> {
    private int attackTimer;
    private T replaced;

    public void onJoinGame(LivingEntity entity, EntityJoinLevelEvent event){

    }

    public void init(T entity){
        this.replaced=entity;
    }
    public void onTick(Level level){
        if(level.isClientSide){
            onClientTick();
        }else {
            onServerTick();
        }
        if(this.attackTimer>0){
            this.attackTimer--;
        }
    }

    private void onServerTick() {

    }

    private void onClientTick() {

    }

    public void playAttack(){
        this.resetCooldownAttack();
    }

    public void plusCombo() {
        if(this.replaced instanceof ICombos entity){
            if(entity.getCombo() < 3) {
                entity.setCombo(entity.getCombo()+1);
            }else {
                entity.setCombo(1);
            }
        }
    }

    public void resetCooldownAttack(){
        this.attackTimer=20;
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }
}
