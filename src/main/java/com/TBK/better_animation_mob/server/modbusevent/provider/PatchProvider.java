package com.TBK.better_animation_mob.server.modbusevent.provider;

import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import com.TBK.better_animation_mob.server.modbusevent.entity.parch.PiglinPatch;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEntity;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedPiglin;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedSpider;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedWitherSkeleton;
import com.google.common.collect.Maps;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class PatchProvider implements ICapabilityProvider, NonNullSupplier<ReplacedEntity<?>> {
    private static final Map<EntityType<?>, Function<Entity, Supplier<ReplacedEntity<?>>>> CAPABILITIES = Maps.newHashMap();
    private static final Map<EntityType<?>, Function<Entity, Supplier<ReplacedEntity<?>>>> CUSTOM_CAPABILITIES = Maps.newHashMap();

    public static void registerEntityPatches() {
        Map<EntityType<?>, Function<Entity, Supplier<ReplacedEntity<?>>>> registry = Maps.newHashMap();
        registry.put(EntityType.PIGLIN, (entityIn) -> ReplacedPiglin::new);
        registry.put(EntityType.PIGLIN_BRUTE, (entityIn) -> ReplacedPiglin::new);
        registry.put(EntityType.WITHER_SKELETON,(entity -> ReplacedWitherSkeleton::new));
        registry.put(EntityType.SPIDER,(entity -> ReplacedSpider::new));


        registry.forEach(CAPABILITIES::put);
    }

    public static Function<Entity, Supplier<ReplacedEntity<?>>> get(String registryName) {
        ResourceLocation rl = new ResourceLocation(registryName);
        EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(rl);

        return CAPABILITIES.get(entityType);
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
