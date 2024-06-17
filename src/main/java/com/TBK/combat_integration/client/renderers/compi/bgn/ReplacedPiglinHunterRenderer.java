package com.TBK.combat_integration.client.renderers.compi.bgn;

import com.TBK.combat_integration.client.models.compi.bgn.ReplacedPiglinHunterModel;
import com.TBK.combat_integration.client.renderers.piglin.ReplacedPiglinRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedPiglin;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.PiglinHunter;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ReplacedPiglinHunterRenderer<T extends PiglinHunter,P extends ReplacedPiglin<T>> extends ReplacedPiglinRenderer<T,P> {
    public ReplacedPiglinHunterRenderer(EntityRendererProvider.Context renderManager){
        super(renderManager,new ReplacedPiglinHunterModel<>(),(P) new ReplacedPiglin(),new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/piglin/piglin_hunter.png"));
    }


}
