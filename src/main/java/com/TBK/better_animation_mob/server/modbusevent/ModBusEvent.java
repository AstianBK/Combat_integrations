package com.TBK.better_animation_mob.server.modbusevent;

import com.TBK.better_animation_mob.BetterAnimationMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterAnimationMob.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvent {

    public static final Attribute COMBO = new RangedAttribute("combo",0,-Integer.MAX_VALUE, Integer.MAX_VALUE);

}
