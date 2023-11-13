package com.TBK.better_animation_mob.server.modbusevent.mixins;

import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ICombos {
    int count;
    protected LivingEntityMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
        this.count=0;
    }

    @Inject(at = @At("RETURN"), method = "swing(Lnet/minecraft/world/InteractionHand;)V")
    public void swing(InteractionHand pHand, CallbackInfo ci) {
        if(this.count<3){
            this.count++;
        }else {
            this.count=1;
        }
    }

    @Override
    public int getCombo() {
        return this.count;
    }

    @Override
    public void setCombo(int combo) {
        this.count=combo;
    }
}
