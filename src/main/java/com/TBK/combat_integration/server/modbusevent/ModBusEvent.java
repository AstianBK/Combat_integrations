package com.TBK.combat_integration.server.modbusevent;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.cap.Capabilities;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import com.TBK.combat_integration.server.modbusevent.provider.PatchProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib3.core.IAnimatable;

import java.util.Set;

@Mod.EventBusSubscriber()
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

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static <T extends LivingEntity,M extends EntityModel<T>> void renderEvent(RenderLivingEvent.Pre<T,M> event){
        EntityType<?> type = event.getEntity().getType();

        if(CombatIntegration.getProviders().containsKey(type)){
            Minecraft mc = Minecraft.getInstance();
            EntityRendererProvider.Context context = new EntityRendererProvider.Context(mc.getEntityRenderDispatcher(),
                    mc.getItemRenderer(),mc.getBlockRenderer(),mc.gameRenderer.itemInHandRenderer,
                    mc.getResourceManager(),mc.getEntityModels(),mc.font);
            EntityRenderer<?> renderer= CombatIntegration.getProviders().get(type).create(context);
            IAnimatable animatable = Capabilities.getEntityPatch(event.getEntity(),ReplacedEntity.class);
            if(renderer instanceof ExtendedGeoReplacedEntityRenderer<?,?> geoRenderer && PatchProvider.getAnimatables()!=null && animatable!=null){
                event.setCanceled(true);
                geoRenderer.setCurrentEntity(event.getEntity());
                geoRenderer.render(event.getEntity(),animatable,0.0F,event.getPartialTick(),event.getPoseStack(),event.getMultiBufferSource(),event.getPackedLight());
            }
        }
    }

    public static void removeBehavior(Brain<?> brain, Activity activity, int priority, Class targetBehaviorClass) {
        Set<Behavior<?>> set = (Set<Behavior<?>>) brain.availableBehaviorsByPriority.get(priority).get(activity);
        set.removeIf(targetBehaviorClass::isInstance);
    }

    public static void replaceBehavior(Brain<?> brain, Activity activity, int priority, Class targetBehaviorClass, Behavior<?> newBehavior) {
        Set<Behavior<?>> set = (Set<Behavior<?>>) brain.availableBehaviorsByPriority.get(priority).get(activity);

        set.removeIf(targetBehaviorClass::isInstance);
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
                event.addCapability(new ResourceLocation(CombatIntegration.MODID, "entity_cap"), prov);
            }
        }
    }

}
