package com.TBK.better_animation_mob.server.modbusevent.mixins;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.teamabnormals.savage_and_ravage.core.SavageAndRavage;
import net.minecraftforge.client.event.EntityRenderersEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SavageAndRavage.class)
public abstract class SavagerAndRavagerMixins {

    @Inject(method = "registerLayers", cancellable = true,at = @At("HEAD"),remap = false)
    public void registerLayerFix(EntityRenderersEvent.AddLayers event, CallbackInfo ci) {
        if(BetterAnimationMob.evokerIsPresent){
            ci.cancel();
        }
    }
}
