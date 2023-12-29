package com.TBK.better_animation_mob.client.renderers.zombie;

import com.TBK.better_animation_mob.client.layers.DrownedGeckoLayer;
import com.TBK.better_animation_mob.client.models.zombie.ReplacedDrownedModel;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedZombie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Drowned;

public class ReplacedDrownedRenderer<T extends Drowned,P extends ReplacedZombie> extends ReplacedZombieRenderer<T,P>{

    public ReplacedDrownedRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ResourceLocation("textures/entity/zombie/drowned.png"),new ReplacedDrownedModel());
        this.addLayer(new DrownedGeckoLayer<>(this));
    }
}
