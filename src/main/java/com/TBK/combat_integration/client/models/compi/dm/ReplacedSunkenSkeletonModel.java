package com.TBK.combat_integration.client.models.compi.dm;

import com.TBK.combat_integration.client.models.skeleton.ReplacedSkeletonModel;
import com.infamous.dungeons_mobs.DungeonsMobs;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;

public class ReplacedSunkenSkeletonModel<T extends IAnimatable> extends ReplacedSkeletonModel<T> {
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(DungeonsMobs.MODID,"textures/entity/ocean/sunken_skeleton.png");
    }
}