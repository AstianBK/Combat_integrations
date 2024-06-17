package com.TBK.combat_integration.client.renderers.compi.dm;

import com.TBK.combat_integration.client.models.compi.dm.ReplacedSunkenSkeletonModel;
import com.TBK.combat_integration.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import com.infamous.dungeons_mobs.DungeonsMobs;
import com.infamous.dungeons_mobs.entities.water.SunkenSkeletonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ReplacedSunkenSkeletonRenderer<T extends SunkenSkeletonEntity,P extends ReplacedSkeleton<T>> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedSunkenSkeletonRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedSunkenSkeletonModel<>(), (P) new ReplacedSkeleton<T>(),new ResourceLocation(DungeonsMobs.MODID,"textures/entity/ocean/sunken_skeleton.png"));
    }
}
