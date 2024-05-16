package com.TBK.better_animation_mob.client.models.compi.dm;

import com.TBK.better_animation_mob.client.models.zombie.ReplacedZombieModel;
import com.infamous.dungeons_mobs.DungeonsMobs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;

@OnlyIn(Dist.CLIENT)
public class ReplacedFrozenZombieModel<T extends IAnimatable> extends ReplacedZombieModel<T> {
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation(DungeonsMobs.MODID,"textures/entity/zombie/frozen_zombie.png");
    }
}
