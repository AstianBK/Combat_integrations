package com.TBK.better_animation_mob.client.models.dm;

import com.TBK.better_animation_mob.client.models.skeleton.ReplacedSkeletonModel;
import com.infamous.dungeons_mobs.DungeonsMobs;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;

public class ReplacedMossySkeletonModel<T extends IAnimatable> extends ReplacedSkeletonModel<T> {
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(DungeonsMobs.MODID,"textures/entity/skeleton/mossy_skeleton.png");
    }
}
