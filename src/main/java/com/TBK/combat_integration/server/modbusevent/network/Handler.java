package com.TBK.combat_integration.server.modbusevent.network;

import com.TBK.combat_integration.server.modbusevent.cap.Capabilities;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedWarden;
import net.minecraft.world.entity.Entity;
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
        ReplacedWarden<?> patch = Capabilities.getEntityPatch(entity, ReplacedWarden.class);
        assert patch!=null;
        patch.playSonicBoom();
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
