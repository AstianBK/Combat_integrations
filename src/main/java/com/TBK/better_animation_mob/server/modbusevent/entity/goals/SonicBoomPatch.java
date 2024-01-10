package com.TBK.better_animation_mob.server.modbusevent.entity.goals;

import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedWarden;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.warden.Warden;

public class SonicBoomPatch extends SonicBoom {

    private static final int TICKS_BEFORE_PLAYING_SOUND = Mth.ceil(34.0D);
    private static final int DURATION = Mth.ceil(60.0F);
    ReplacedWarden<?> replacedWarden;
    public SonicBoomPatch(ReplacedWarden<?> replacedWarden){
        super();
        this.replacedWarden=replacedWarden;
    }

    @Override
    protected void start(ServerLevel p_217713_, Warden p_217714_, long p_217715_) {
        p_217714_.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, (long)DURATION);
        p_217714_.getBrain().setMemoryWithExpiry(MemoryModuleType.SONIC_BOOM_SOUND_DELAY, Unit.INSTANCE, (long)TICKS_BEFORE_PLAYING_SOUND);
        this.replacedWarden.playSonicBoom();
        p_217714_.playSound(SoundEvents.WARDEN_SONIC_CHARGE, 3.0F, 1.0F);
    }
}
