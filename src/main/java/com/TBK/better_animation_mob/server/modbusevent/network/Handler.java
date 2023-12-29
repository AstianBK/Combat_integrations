package com.TBK.better_animation_mob.server.modbusevent.network;

import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedPiglin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class Handler {
    @OnlyIn(Dist.CLIENT)
    public static void handlePlayActivateAnimation(Entity entity) {
        ReplacedEntity<?> patch = Capabilities.getEntityPatch(entity, ReplacedEntity.class);
        assert patch!=null;
        patch.playAttack();
    }
    @OnlyIn(Dist.CLIENT)
    public static void handleSyncCombo(Entity entity) {
        ReplacedEntity<?> patch = Capabilities.getEntityPatch(entity, ReplacedEntity.class);
        assert patch!=null;
        patch.init(entity);
    }

    public static void handlerManager(int id , Entity entity){
        switch (id){
            case 0->{
                handlePlayActivateAnimation(entity);
                break;
            }
            case 1->{
                handleSyncCombo(entity);
                break;
            }
        }
    }
}
