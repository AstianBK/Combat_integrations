package com.TBK.better_animation_mob.client.renderers.compi.bgn;

import com.TBK.better_animation_mob.client.models.compi.bgn.ReplacedPiglinHunterModel;
import com.TBK.better_animation_mob.client.renderers.piglin.ReplacedPiglinRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedPiglin;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.PiglinHunter;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ReplacedPiglinHunterRenderer<T extends PiglinHunter,P extends ReplacedPiglin<T>> extends ReplacedPiglinRenderer<T,P> {
    public ReplacedPiglinHunterRenderer(EntityRendererProvider.Context renderManager){
        super(renderManager,new ReplacedPiglinHunterModel<>(),(P) new ReplacedPiglin(),new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/piglin/piglin_hunter.png"));
    }


}
