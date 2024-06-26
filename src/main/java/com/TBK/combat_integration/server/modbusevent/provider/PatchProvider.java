package com.TBK.combat_integration.server.modbusevent.provider;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.util.Compati;
import com.TBK.combat_integration.config.BKConfig;
import com.TBK.combat_integration.server.modbusevent.cap.Capabilities;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.*;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.bgn.ReplacedWex;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.dm.ReplacedFungusThrower;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.dm.ReplacedZombieFungusThrower;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.gv.ReplacedGuardVillager;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.myf.ReplacedBellringer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.myf.ReplacedFortuna;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.myf.ReplacedRosalyne;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.myf.ReplacedSwampJaw;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.qrk.ReplacedForgotten;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.qrk.ReplacedFoxhound;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.qrk.ReplacedWraith;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.svr.ReplacedExecutioner;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.svr.ReplacedGriefer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.svr.ReplacedIceologer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.svr.ReplacedTrickster;
import com.google.common.collect.Maps;
import com.izofar.bygonenether.init.ModEntityTypes;
import com.teamabnormals.savage_and_ravage.core.registry.SREntityTypes;
import lykrast.meetyourfight.registry.ModEntities;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.registries.ForgeRegistries;
import tallestegg.guardvillagers.GuardEntityType;
import vazkii.quark.content.mobs.module.ForgottenModule;
import vazkii.quark.content.mobs.module.FoxhoundModule;
import vazkii.quark.content.mobs.module.WraithModule;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class PatchProvider implements ICapabilityProvider, NonNullSupplier<ReplacedEntity<?>> {
    private static final Map<EntityType<?>, Function<Entity, Supplier<ReplacedEntity<?>>>> CAPABILITIES = Maps.newHashMap();
    private static final Map<EntityType<?>, Function<Entity, Supplier<ReplacedEntity<?>>>> CUSTOM_CAPABILITIES = Maps.newHashMap();

    public static void registerEntityPatches() {
        Map<EntityType<?>, Function<Entity, Supplier<ReplacedEntity<?>>>> registry = Maps.newHashMap();
        if(BKConfig.vanillaMobsAnimations){
            registry.put(EntityType.PIGLIN, (entityIn) -> ReplacedPiglin::new);
            registry.put(EntityType.PIGLIN_BRUTE, (entityIn) -> ReplacedPiglin::new);
            registry.put(EntityType.ZOMBIFIED_PIGLIN,entity -> ReplacedZombiePiglin::new);
            registry.put(EntityType.WITHER_SKELETON,(entity -> ReplacedWitherSkeleton::new));
            registry.put(EntityType.SPIDER,(entity -> ReplacedSpider::new));
            registry.put(EntityType.CAVE_SPIDER,entity -> ReplacedSpider::new);
            registry.put(EntityType.RAVAGER,(entity -> ReplacedRavager::new));
            registry.put(EntityType.VINDICATOR,(entity -> ReplacedVindicator::new));
            registry.put(EntityType.ZOMBIE,(entity -> ReplacedZombie::new));
            registry.put(EntityType.HUSK,(entity -> ReplacedZombie::new));
            registry.put(EntityType.DROWNED,(entity -> ReplacedZombie::new));
            registry.put(EntityType.IRON_GOLEM,entity -> ReplacedIronGolem::new);

            registry.put(EntityType.SKELETON, entity -> ReplacedSkeleton::new);
            registry.put(EntityType.STRAY, entity -> ReplacedSkeleton::new);

            registry.put(EntityType.ENDERMAN,entity -> ReplacedEnderMan::new);
            registry.put(EntityType.WOLF,entity -> ReplacedWolf::new);

            registry.put(EntityType.EVOKER,entity -> ReplacedEvoker::new);

            registry.put(EntityType.WARDEN,entity -> ReplacedWarden::new);
        }

        if(BKConfig.savageAndRavageAnimations){
            if(CombatIntegration.isLoaded(Compati.SAVAGE_AND_RAVAGE)){
                registry.put(SREntityTypes.EXECUTIONER.get(),entity -> ReplacedExecutioner::new);
                registry.put(SREntityTypes.GRIEFER.get(),entity -> ReplacedGriefer::new);
                registry.put(SREntityTypes.ICEOLOGER.get(),entity -> ReplacedIceologer::new);
                registry.put(SREntityTypes.TRICKSTER.get(),entity -> ReplacedTrickster::new);
            }
        }


        if(BKConfig.bygoneNetherAnimations){
            if(CombatIntegration.isLoaded(Compati.BYGONE_NETHER)){
                registry.put(ModEntityTypes.CORPOR.get(), entity -> ReplacedSkeleton::new);
                registry.put(ModEntityTypes.PIGLIN_PRISONER.get(),entity -> ReplacedPiglin::new);
                registry.put(ModEntityTypes.PIGLIN_HUNTER.get(),entity -> ReplacedPiglin::new);
                registry.put(ModEntityTypes.WITHER_SKELETON_KNIGHT.get(),entity -> ReplacedSkeleton::new);
                registry.put(ModEntityTypes.WARPED_ENDERMAN.get(),entity -> ReplacedEnderMan::new);
                registry.put(ModEntityTypes.WEX.get(),entity -> ReplacedWex::new);
                registry.put(ModEntityTypes.WRAITHER.get(),entity -> ReplacedSkeleton::new);
            }
        }

        if(BKConfig.dungeonsMobsAnimations){
            if(CombatIntegration.isLoaded(Compati.DUNGEONS_MOBS)){
                registry.put(com.infamous.dungeons_mobs.mod.ModEntityTypes.FROZEN_ZOMBIE.get(), entity -> ReplacedZombie::new);
                registry.put(com.infamous.dungeons_mobs.mod.ModEntityTypes.JUNGLE_ZOMBIE.get(),entity -> ReplacedZombie::new);
                registry.put(com.infamous.dungeons_mobs.mod.ModEntityTypes.MOSSY_SKELETON.get(),entity -> ReplacedSkeleton::new);
                registry.put(com.infamous.dungeons_mobs.mod.ModEntityTypes.SUNKEN_SKELETON.get(),entity -> ReplacedSkeleton::new);
                registry.put(com.infamous.dungeons_mobs.mod.ModEntityTypes.ZOMBIFIED_FUNGUS_THROWER.get(),entity -> ReplacedZombieFungusThrower::new);
                registry.put(com.infamous.dungeons_mobs.mod.ModEntityTypes.FUNGUS_THROWER.get(),entity -> ReplacedFungusThrower::new);
            }
        }

        if(BKConfig.quarkAnimations){
            if(CombatIntegration.isLoaded(Compati.QUARK)){
                registry.put(FoxhoundModule.foxhoundType, entity-> ReplacedFoxhound::new);
                registry.put(ForgottenModule.forgottenType, entity -> ReplacedForgotten::new);
                registry.put(WraithModule.wraithType,entity ->  ReplacedWraith::new);
            }
        }
        if(BKConfig.guardVillagersAnimations){
            if (CombatIntegration.isLoaded(Compati.GUARD_VILLAGERS)){
                registry.put(GuardEntityType.GUARD.get(),entity -> ReplacedGuardVillager::new);
            }
        }

        if(BKConfig.meetYourFightAnimations){
            if(CombatIntegration.isLoaded(Compati.MEET_YOUR_FIGHT)){
                registry.put(ModEntities.SWAMPJAW.get(),entity -> ReplacedSwampJaw::new);
                registry.put(ModEntities.BELLRINGER.get(),entity -> ReplacedBellringer::new);
                registry.put(ModEntities.DAME_FORTUNA.get(),entity -> ReplacedFortuna::new);
                registry.put(ModEntities.ROSALYNE.get(),entity -> ReplacedRosalyne::new);
            }
        }

        CAPABILITIES.putAll(registry);
    }

    public static Function<Entity, Supplier<ReplacedEntity<?>>> get(String registryName) {
        ResourceLocation rl = new ResourceLocation(registryName);
        EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(rl);

        return CAPABILITIES.get(entityType);
    }
    public static Map<EntityType<?>, Function<Entity, Supplier<ReplacedEntity<?>>>> getAnimatables(){
        return CAPABILITIES;
    }

    private ReplacedEntity<?> capability;
    private final LazyOptional<ReplacedEntity<?>> optional = LazyOptional.of(this);

    public PatchProvider(Entity entity) {
        Function<Entity, Supplier<ReplacedEntity<?>>> provider = CUSTOM_CAPABILITIES.getOrDefault(entity.getType(), CAPABILITIES.get(entity.getType()));

        if (provider != null) {
            this.capability = provider.apply(entity).get();
        }
    }

    public boolean hasCapability() {
        return capability != null;
    }

    @Override
    public ReplacedEntity<?> get() {
        return this.capability;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == Capabilities.CAPABILITY_ENTITY ? this.optional.cast() :  LazyOptional.empty();
    }

}
