package com.TBK.better_animation_mob.server.modbusevent.register;

import com.TBK.better_animation_mob.BetterAnimationMob;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BkEffect {
    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BetterAnimationMob.MODID);

}
