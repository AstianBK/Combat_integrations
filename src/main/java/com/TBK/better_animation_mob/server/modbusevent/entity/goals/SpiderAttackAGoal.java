package com.TBK.better_animation_mob.server.modbusevent.entity.goals;

import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedSpider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Spider;

public class SpiderAttackAGoal<T extends Spider,P extends ReplacedSpider<T>> extends AttackAGoal<T,P>{
    public SpiderAttackAGoal(T p_25552_, P patchMob) {
        super(p_25552_, 1.0D, true, patchMob);
    }

    public boolean canUse() {
        return super.canUse() && !this.mob.isVehicle();
    }

    public boolean canContinueToUse() {
        float f = this.mob.getLightLevelDependentMagicValue();
        if (f >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
            this.mob.setTarget((LivingEntity)null);
            return false;
        } else {
            return super.canContinueToUse();
        }
    }

    protected double getAttackReachSqr(LivingEntity p_33825_) {
        return (double)(4.0F + p_33825_.getBbWidth());
    }
}
