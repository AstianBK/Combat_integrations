package com.TBK.better_animation_mob.server.modbusevent.procedures;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.server.modbusevent.ModBusEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Events {

    @SubscribeEvent
    public static void onTickEntity(LivingEvent.LivingTickEvent event){
    }
}
