package com.TBK.better_animation_mob.client.renderers.enderman;

import com.TBK.better_animation_mob.client.layers.EnderManEyeGeckoLayer;
import com.TBK.better_animation_mob.client.models.enderman.ReplacedEnderManModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEnderMan;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedEnderManRenderer<T extends EnderMan,P extends ReplacedEnderMan<T>> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedEnderManRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedEnderManModel<>(), (P) new ReplacedEnderMan<T>());
        this.addLayer(new EnderManEyeGeckoLayer<>(this));
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }
}
