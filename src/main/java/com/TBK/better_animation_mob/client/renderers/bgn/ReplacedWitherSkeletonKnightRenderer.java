package com.TBK.better_animation_mob.client.renderers.bgn;

import com.TBK.better_animation_mob.client.models.bgn.ReplacedCorporModel;
import com.TBK.better_animation_mob.client.models.bgn.ReplacedWitherSkeletonKnightModel;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedWitherSkeletonRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedWitherSkeleton;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.WitherSkeletonKnight;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ReplacedWitherSkeletonKnightRenderer<T extends WitherSkeletonKnight,P extends ReplacedWitherSkeleton<T>> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedWitherSkeletonKnightRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedWitherSkeletonKnightModel(), (P) new ReplacedWitherSkeleton<T>(),new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/wither/wither_skeleton_knight.png"));
    }
}
