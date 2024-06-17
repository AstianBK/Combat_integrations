package com.TBK.combat_integration.client.renderers.spider;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.layers.SpiderEyeGeckoLayer;
import com.TBK.combat_integration.client.models.spider.ReplacedSpiderModel;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedSpider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedSpiderRenderer<T extends Spider,P extends ReplacedSpider> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedSpiderRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ReplacedSpiderModel<>(),(P) new ReplacedSpider());
        this.addLayer(new SpiderEyeGeckoLayer<>(this,new ResourceLocation("textures/entity/spider_eyes.png"),new ResourceLocation(CombatIntegration.MODID, "geo/spider.geo.json")));
    }

    @Override
    public boolean shouldRenderItemStack(T currentEntity) {
        return false;
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }
}
