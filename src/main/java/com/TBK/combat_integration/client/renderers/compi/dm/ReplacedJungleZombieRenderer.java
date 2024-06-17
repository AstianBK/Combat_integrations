package com.TBK.combat_integration.client.renderers.compi.dm;

import com.TBK.combat_integration.client.models.compi.dm.ReplacedJungleZombieModel;
import com.TBK.combat_integration.client.renderers.zombie.ReplacedZombieRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedZombie;
import com.infamous.dungeons_mobs.DungeonsMobs;
import com.infamous.dungeons_mobs.entities.undead.JungleZombieEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ReplacedJungleZombieRenderer<T extends JungleZombieEntity,P extends ReplacedZombie<T>> extends ReplacedZombieRenderer<T,P> {

    public ReplacedJungleZombieRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ResourceLocation(DungeonsMobs.MODID,"textures/entity/zombie/jungle_zombie.png"),new ReplacedJungleZombieModel<>());
    }
}
