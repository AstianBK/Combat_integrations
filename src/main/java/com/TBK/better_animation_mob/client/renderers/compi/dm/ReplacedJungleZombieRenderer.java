package com.TBK.better_animation_mob.client.renderers.compi.dm;

import com.TBK.better_animation_mob.client.models.dm.ReplacedFrozenZombieModel;
import com.TBK.better_animation_mob.client.models.dm.ReplacedJungleZombieModel;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedZombieRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedZombie;
import com.infamous.dungeons_mobs.DungeonsMobs;
import com.infamous.dungeons_mobs.entities.undead.FrozenZombieEntity;
import com.infamous.dungeons_mobs.entities.undead.JungleZombieEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ReplacedJungleZombieRenderer<T extends JungleZombieEntity,P extends ReplacedZombie<T>> extends ReplacedZombieRenderer<T,P> {

    public ReplacedJungleZombieRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ResourceLocation(DungeonsMobs.MODID,"textures/entity/zombie/jungle_zombie.png"),new ReplacedJungleZombieModel<>());
    }
}
