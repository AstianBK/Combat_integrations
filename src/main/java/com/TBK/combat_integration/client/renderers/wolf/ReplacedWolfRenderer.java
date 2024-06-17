package com.TBK.combat_integration.client.renderers.wolf;

import com.TBK.combat_integration.client.layers.CollarGeckoLayer;
import com.TBK.combat_integration.client.models.wolf.ReplacedWolfModel;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedWolf;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedWolfRenderer<T extends Wolf,P extends ReplacedWolf<T>> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedWolfRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedWolfModel(), (P)new ReplacedWolf<>());
        this.addLayer(new CollarGeckoLayer<>(this));
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }
}
