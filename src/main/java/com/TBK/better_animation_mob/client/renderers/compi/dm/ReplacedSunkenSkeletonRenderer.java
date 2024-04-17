package com.TBK.better_animation_mob.client.renderers.compi.dm;

import com.TBK.better_animation_mob.client.models.dm.ReplacedSunkenSkeletonModel;
import com.TBK.better_animation_mob.client.models.skeleton.ReplacedSkeletonModel;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import com.infamous.dungeons_mobs.DungeonsMobs;
import com.infamous.dungeons_mobs.entities.water.SunkenSkeletonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class ReplacedSunkenSkeletonRenderer<T extends SunkenSkeletonEntity,P extends ReplacedSkeleton<T>> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedSunkenSkeletonRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedSunkenSkeletonModel<>(), (P) new ReplacedSkeleton<T>(),new ResourceLocation(DungeonsMobs.MODID,"textures/entity/ocean/sunken_skeleton.png"));
    }
}
