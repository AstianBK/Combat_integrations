package com.TBK.better_animation_mob.client.renderers.compi.qrk;

import com.TBK.better_animation_mob.client.models.qrk.ReplacedWraithModel;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedZombieRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.qrk.ReplacedWraith;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import vazkii.quark.base.Quark;
import vazkii.quark.content.mobs.entity.Wraith;

public class ReplacedWraithRenderer<T extends Wraith,P extends ReplacedWraith> extends ReplacedZombieRenderer<T,P> {

    public ReplacedWraithRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ResourceLocation(Quark.MOD_ID,"model/entity/wraith.png"),new ReplacedWraithModel<>());
    }
}
