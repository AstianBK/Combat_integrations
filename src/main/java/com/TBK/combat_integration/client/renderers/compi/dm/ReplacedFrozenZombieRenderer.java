package com.TBK.combat_integration.client.renderers.compi.dm;

import com.TBK.combat_integration.client.models.compi.dm.ReplacedFrozenZombieModel;
import com.TBK.combat_integration.client.renderers.zombie.ReplacedZombieRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedZombie;
import com.infamous.dungeons_mobs.DungeonsMobs;
import com.infamous.dungeons_mobs.entities.undead.FrozenZombieEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ReplacedFrozenZombieRenderer<T extends FrozenZombieEntity,P extends ReplacedZombie<T>> extends ReplacedZombieRenderer<T,P> {

    public ReplacedFrozenZombieRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ResourceLocation(DungeonsMobs.MODID,"textures/entity/zombie/frozen_zombie.png"),new ReplacedFrozenZombieModel<>());
    }
}
