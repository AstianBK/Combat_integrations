package com.TBK.combat_integration.server.modbusevent.register;

import com.TBK.combat_integration.CombatIntegration;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BkEffect {
    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CombatIntegration.MODID);

}
