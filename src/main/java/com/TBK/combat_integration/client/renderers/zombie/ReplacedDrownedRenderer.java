package com.TBK.combat_integration.client.renderers.zombie;

import com.TBK.combat_integration.client.layers.DrownedGeckoLayer;
import com.TBK.combat_integration.client.models.zombie.ReplacedDrownedModel;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedZombie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Drowned;

public class ReplacedDrownedRenderer<T extends Drowned,P extends ReplacedZombie> extends ReplacedZombieRenderer<T,P>{

    public ReplacedDrownedRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ResourceLocation("textures/entity/zombie/drowned.png"),new ReplacedDrownedModel());
        this.addLayer(new DrownedGeckoLayer<>(this));
    }
}
