package com.TBK.combat_integration.client.renderers.compi.dm;

import com.TBK.combat_integration.client.models.compi.dm.ReplacedFungusThrowerModel;
import com.TBK.combat_integration.client.renderers.piglin.ReplacedPiglinRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.dm.ReplacedFungusThrower;
import com.infamous.dungeons_mobs.DungeonsMobs;
import com.infamous.dungeons_mobs.entities.piglin.FungusThrowerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ReplacedFungusThrowerRenderer<T extends FungusThrowerEntity,P extends ReplacedFungusThrower<T>> extends ReplacedPiglinRenderer<T,P> {
    public ReplacedFungusThrowerRenderer(EntityRendererProvider.Context renderManager){
        super(renderManager,new ReplacedFungusThrowerModel<>(),(P) new ReplacedFungusThrower<>(),new ResourceLocation(DungeonsMobs.MODID,"textures/entity/piglin/fungus_thrower.png"));
    }
}
