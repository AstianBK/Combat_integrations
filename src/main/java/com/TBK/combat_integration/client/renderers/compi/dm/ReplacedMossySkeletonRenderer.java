package com.TBK.combat_integration.client.renderers.compi.dm;

import com.TBK.combat_integration.client.models.compi.dm.ReplacedMossySkeletonModel;
import com.TBK.combat_integration.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import com.infamous.dungeons_mobs.DungeonsMobs;
import com.infamous.dungeons_mobs.entities.undead.MossySkeletonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ReplacedMossySkeletonRenderer<T extends MossySkeletonEntity,P extends ReplacedSkeleton<T>> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedMossySkeletonRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedMossySkeletonModel<>(), (P) new ReplacedSkeleton<T>(),new ResourceLocation(DungeonsMobs.MODID,"textures/entity/skeleton/mossy_skeleton.png"));
    }
}
