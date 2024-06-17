package com.TBK.combat_integration.client.renderers.skeleton;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.layers.StrayGeckoLayer;
import com.TBK.combat_integration.client.models.skeleton.ReplacedSkeletonModel;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class ReplacedStrayRenderer<T extends AbstractSkeleton,P extends ReplacedSkeleton> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedStrayRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedSkeletonModel<>(), (P) new ReplacedSkeleton(),new ResourceLocation("textures/entity/skeleton/stray.png"));
        this.addLayer(new StrayGeckoLayer<>(this,this.getGeoModelProvider(),new ResourceLocation("textures/entity/skeleton/stray.png"),new ResourceLocation(CombatIntegration.MODID,"geo/skeleton.geo.json"),new ResourceLocation("textures/entity/skeleton/stray_overlay.png")));
    }
}
