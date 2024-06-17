package com.TBK.combat_integration.server.modbusevent.cap;

import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

@SuppressWarnings("rawtypes")
public class Capabilities {
    public static final Capability<ReplacedEntity> CAPABILITY_ENTITY = CapabilityManager.get(new CapabilityToken<>(){});


    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ReplacedEntity.class);
    }

    @SuppressWarnings("unchecked")
    public static <T extends ReplacedEntity> T getEntityPatch(Entity entity, Class<T> type) {
        if (entity != null) {
            ReplacedEntity<?> entitypatch = entity.getCapability(Capabilities.CAPABILITY_ENTITY).orElse(null);

            if (entitypatch != null && type.isAssignableFrom(entitypatch.getClass())) {
                return (T)entitypatch;
            }
        }

        return null;
    }
}
