package com.TBK.better_animation_mob.server.modbusevent;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import com.TBK.better_animation_mob.server.modbusevent.entity.goals.MeleeAttackPatch;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import com.TBK.better_animation_mob.server.modbusevent.provider.PatchProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = BetterAnimationMob.MODID)
public class ModBusEvent {

    @SubscribeEvent
    public static void onJoinGame(EntityJoinLevelEvent event) {
        if(event.getEntity() instanceof LivingEntity){
            ReplacedEntity<?> piglinPatch = Capabilities.getEntityPatch(event.getEntity(), ReplacedEntity.class);
            if(piglinPatch!=null){
                piglinPatch.onJoinGame((LivingEntity) event.getEntity(),event);
            }
        }

    }

    public static void removeBehavior(Brain<?> brain, Activity activity, int priority, Class targetBehaviorClass) {
        Set<Behavior<?>> set = (Set<Behavior<?>>) brain.availableBehaviorsByPriority.get(priority).get(activity);
        set.removeIf((behavior) -> targetBehaviorClass.isInstance(behavior));
    }

    public static void replaceBehavior(Brain<?> brain, Activity activity, int priority, Class targetBehaviorClass, Behavior<?> newBehavior) {
        Set<Behavior<?>> set = (Set<Behavior<?>>) brain.availableBehaviorsByPriority.get(priority).get(activity);

        set.removeIf((behavior) -> targetBehaviorClass.isInstance(behavior));
        set.add(newBehavior);
    }

    public static void removeMeleeGoal(PathfinderMob entity,Set<Goal> goals){
        for(WrappedGoal wrappedGoal : entity.goalSelector.getAvailableGoals()){
                Goal goal = wrappedGoal.getGoal();
                if(goal instanceof MeleeAttackGoal){
                    goals.add(goal);
                }
            }
    }


    @SubscribeEvent
    public static void hurtEvent(LivingHurtEvent event){
        //event.getEntity().getAttackAnim()
    }

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event){
        ReplacedEntity<?> piglinPatch = Capabilities.getEntityPatch(event.getEntity(), ReplacedEntity.class);
        if(piglinPatch!=null){
            piglinPatch.onTick(event.getEntity().getLevel());
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SubscribeEvent
    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        ReplacedEntity oldEntitypatch = Capabilities.getEntityPatch(event.getObject(), ReplacedEntity.class);

        if (oldEntitypatch == null) {
            PatchProvider prov = new PatchProvider(event.getObject());

            if (prov.hasCapability() && event.getObject() instanceof Mob) {
                ReplacedEntity<?> entitypatch = prov.getCapability(Capabilities.CAPABILITY_ENTITY).orElse(null);

                entitypatch.init(event.getObject());
                event.addCapability(new ResourceLocation(BetterAnimationMob.MODID, "entity_cap"), prov);
            }
        }
    }

}
