package com.TBK.combat_integration.client.renderers.compi.qrk;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.layers.SpiderEyeGeckoLayer;
import com.TBK.combat_integration.client.layers.StrayGeckoLayer;
import com.TBK.combat_integration.client.models.compi.qrk.ReplacedForgottenModel;
import com.TBK.combat_integration.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.qrk.ReplacedForgotten;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import vazkii.quark.base.Quark;
import vazkii.quark.content.mobs.entity.Forgotten;

public class ReplacedForgottenRenderer<T extends Forgotten,P extends ReplacedForgotten> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedForgottenRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedForgottenModel<>(), (P) new ReplacedForgotten(),new ResourceLocation(Quark.MOD_ID,"textures/model/entity/forgotten/main.png"));
        this.addLayer(new SpiderEyeGeckoLayer<>(this,new ResourceLocation(Quark.MOD_ID,"textures/model/entity/forgotten/eye.png"),new ResourceLocation(CombatIntegration.MODID,"geo/qrk/forgotten.geo.json")));
        this.addLayer(new StrayGeckoLayer<>(this,this.getGeoModelProvider(),new ResourceLocation(Quark.MOD_ID,"textures/model/entity/forgotten/main.png"),new ResourceLocation(CombatIntegration.MODID,"geo/qrk/forgotten.geo.json"),new ResourceLocation(Quark.MOD_ID,"textures/model/entity/forgotten/overlay.png")));
    }
}
