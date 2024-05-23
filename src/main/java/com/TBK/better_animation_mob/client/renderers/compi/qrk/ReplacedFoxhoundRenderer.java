package com.TBK.better_animation_mob.client.renderers.compi.qrk;

import com.TBK.better_animation_mob.client.layers.CollarGeckoLayer;
import com.TBK.better_animation_mob.client.models.compi.qrk.ReplacedFoxhoundModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.qrk.ReplacedFoxhound;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import vazkii.quark.content.mobs.entity.Foxhound;

public class ReplacedFoxhoundRenderer<T extends Foxhound,P extends ReplacedFoxhound<T>> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedFoxhoundRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedFoxhoundModel(), (P)new ReplacedFoxhound<>());
        this.addLayer(new CollarGeckoLayer<>(this));
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }
}
