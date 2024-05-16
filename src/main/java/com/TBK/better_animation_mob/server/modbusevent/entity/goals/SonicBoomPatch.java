package com.TBK.better_animation_mob.server.modbusevent.entity.goals;

import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedWarden;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.phys.Vec3;

public class SonicBoomPatch extends SonicBoom {
    private static final int DISTANCE_XZ = 15;
    private static final int DISTANCE_Y = 20;
    private static final double KNOCKBACK_VERTICAL = 0.5D;
    private static final double KNOCKBACK_HORIZONTAL = 2.5D;
    public static final int COOLDOWN = 40;
    private static final int TICKS_BEFORE_PLAYING_SOUND = Mth.ceil(34.0D);
    private static final int DURATION = Mth.ceil(60.0F);
    private final ReplacedWarden<?> replaced;

    public SonicBoomPatch(ReplacedWarden<?> replacedWarden) {
        super();
        this.replaced=replacedWarden;
    }

    protected void start(ServerLevel p_217713_, Warden p_217714_, long p_217715_) {
        p_217714_.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, (long)DURATION);
        p_217714_.getBrain().setMemoryWithExpiry(MemoryModuleType.SONIC_BOOM_SOUND_DELAY, Unit.INSTANCE, (long)TICKS_BEFORE_PLAYING_SOUND);
        this.replaced.playSonicBoom();
        p_217714_.playSound(SoundEvents.WARDEN_SONIC_CHARGE, 3.0F, 1.0F);
    }

}
