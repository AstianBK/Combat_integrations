package com.TBK.better_animation_mob.server.modbusevent.entity.goals;

import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedPiglin;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ProjectileWeaponItem;

public class MeleeAttackPatch extends Behavior<Mob> {

    private final int cooldownBetweenAttacks;
    private final int cooldownBetweenHardAttack;
    private final ReplacedEntity<?> replacedPiglin;


    public MeleeAttackPatch(ReplacedEntity<?> replacedPiglin,int cooldownBetweenAttacks,int cooldownBetweenHardAttack) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));
        this.replacedPiglin = replacedPiglin;
        this.cooldownBetweenAttacks = cooldownBetweenAttacks;
        this.cooldownBetweenHardAttack=cooldownBetweenHardAttack;
    }

    protected boolean checkExtraStartConditions(ServerLevel p_23521_, Mob p_23522_) {
        LivingEntity livingentity = this.getAttackTarget(p_23522_);
        return !this.isHoldingUsableProjectileWeapon(p_23522_) && BehaviorUtils.canSee(p_23522_, livingentity) && p_23522_.isWithinMeleeAttackRange(livingentity);
    }

    private boolean isHoldingUsableProjectileWeapon(Mob p_23528_) {
        return p_23528_.isHolding((p_147697_) -> {
            Item item = p_147697_.getItem();
            return item instanceof ProjectileWeaponItem && p_23528_.canFireProjectileWeapon((ProjectileWeaponItem)item);
        });
    }

    @Override
    protected boolean canStillUse(ServerLevel p_22545_, Mob p_22546_, long p_22547_) {
        return super.canStillUse(p_22545_, p_22546_, p_22547_);
    }

    protected void start(ServerLevel p_23524_, Mob p_23525_, long p_23526_) {
        LivingEntity livingentity = this.getAttackTarget(p_23525_);
        BehaviorUtils.lookAtEntity(p_23525_, livingentity);
        if(this.replacedPiglin.getAttackTimer()==0){
            this.replacedPiglin.playAttack();
            long cc=replacedPiglin.getCombo(p_23525_)==2 ? (long)this.cooldownBetweenHardAttack : (long)this.cooldownBetweenAttacks;
            p_23525_.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true,cc);
        }
    }

    @Override
    protected void tick(ServerLevel p_22551_, Mob p_22552_, long p_22553_) {
        super.tick(p_22551_, p_22552_, p_22553_);
    }

    private LivingEntity getAttackTarget(Mob p_23533_) {
        return p_23533_.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
    }
}
