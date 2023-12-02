package com.TBK.better_animation_mob;

import com.TBK.better_animation_mob.client.util.Compati;
import com.TBK.better_animation_mob.server.modbusevent.register.BkEffect;
import com.TBK.better_animation_mob.server.modbusevent.register.BkEntityTypes;
import com.TBK.better_animation_mob.server.modbusevent.register.BkItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(BetterAnimationMob.MODID)
public class BetterAnimationMob {
    public static final String MODID = "better_animation_mob";
    public static boolean evokerIsPresent = true;
    public BetterAnimationMob() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();
        BkEntityTypes.register(modEventBus);
        BkEffect.EFFECT.register(modEventBus);
        BkItems.ITEMS.register(modEventBus);
        modEventBus.register(this);
    }



    public static boolean isLoaded(Compati name){
        return ModList.get().isLoaded(name.id);
    }
}
