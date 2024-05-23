package com.TBK.better_animation_mob.client.renderers.compi.dm;

import com.TBK.better_animation_mob.client.models.compi.dm.ReplacedFungusThrowerModel;
import com.TBK.better_animation_mob.client.renderers.piglin.ReplacedPiglinRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.dm.ReplacedFungusThrower;
import com.infamous.dungeons_mobs.DungeonsMobs;
import com.infamous.dungeons_mobs.entities.piglin.FungusThrowerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ReplacedFungusThrowerRenderer<T extends FungusThrowerEntity,P extends ReplacedFungusThrower<T>> extends ReplacedPiglinRenderer<T,P> {
    public ReplacedFungusThrowerRenderer(EntityRendererProvider.Context renderManager){
        super(renderManager,new ReplacedFungusThrowerModel<>(),(P) new ReplacedFungusThrower<>(),new ResourceLocation(DungeonsMobs.MODID,"textures/entity/piglin/fungus_thrower.png"));
    }
}
