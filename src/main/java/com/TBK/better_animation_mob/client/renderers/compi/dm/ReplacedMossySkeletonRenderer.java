package com.TBK.better_animation_mob.client.renderers.compi.dm;

import com.TBK.better_animation_mob.client.models.dm.ReplacedMossySkeletonModel;
import com.TBK.better_animation_mob.client.models.skeleton.ReplacedSkeletonModel;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import com.infamous.dungeons_mobs.DungeonsMobs;
import com.infamous.dungeons_mobs.entities.undead.MossySkeletonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class ReplacedMossySkeletonRenderer<T extends MossySkeletonEntity,P extends ReplacedSkeleton<T>> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedMossySkeletonRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedMossySkeletonModel<>(), (P) new ReplacedSkeleton<T>(),new ResourceLocation(DungeonsMobs.MODID,"textures/entity/skeleton/mossy_skeleton.png"));
    }
}
