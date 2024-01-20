package com.TBK.better_animation_mob.client.renderers.illager;

import com.TBK.better_animation_mob.client.models.illager.ReplacedIceologerModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.svr.ReplacedIceologer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Iceologer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedIceologerRenderer<T extends Iceologer,P extends ReplacedIceologer> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedIceologerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedIceologerModel<>(), (P) new ReplacedIceologer());
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }
}
