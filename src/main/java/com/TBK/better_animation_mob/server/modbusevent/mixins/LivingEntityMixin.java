package com.TBK.better_animation_mob.server.modbusevent.mixins;

import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ICombos {
    @Unique
    int proyectoA$count;
    protected LivingEntityMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
        this.proyectoA$count =0;
    }

    @Inject(at = @At("RETURN"), method = "swing(Lnet/minecraft/world/InteractionHand;Z)V")
    public void swing(InteractionHand p_21012_, boolean p_21013_, CallbackInfo ci) {
        if(this.proyectoA$count < 3){
            this.proyectoA$count++;
        }else {
            this.proyectoA$count =1;
        }
    }

    @Override
    public int getCombo() {
        return this.proyectoA$count;
    }

    @Override
    public void setCombo(int combo) {
        this.proyectoA$count = combo;
    }
}
