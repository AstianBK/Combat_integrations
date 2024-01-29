package com.TBK.better_animation_mob.server.modbusevent.mixins;

import com.TBK.better_animation_mob.BetterAnimationMob;
import net.mehvahdjukaar.supplementaries.client.renderers.entities.layers.QuiverLayer;
import net.mehvahdjukaar.supplementaries.common.events.forge.ClientEventsForge;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.EntityRenderersEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(ClientEventsForge.class)
public abstract class SupplementariesMixins {
    @Inject(method = "onAddLayers", cancellable = true,at = @At("HEAD"),remap = false)
    private static void onAddLayers(EntityRenderersEvent.AddLayers event, CallbackInfo ci) {
        Iterator var1 = event.getSkins().iterator();

        while(var1.hasNext()) {
            String skinType = (String)var1.next();
            LivingEntityRenderer<? extends Player, ? extends EntityModel<? extends Player>> renderer = event.getSkin(skinType);
            if (renderer != null) {
                renderer.addLayer(new QuiverLayer(renderer, false));
            }
        }
        ci.cancel();
    }
}
