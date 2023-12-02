package com.TBK.better_animation_mob.client.renderers.illager;

import com.TBK.better_animation_mob.client.models.illager.ReplacedTricksterModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.svr.ReplacedTrickster;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Trickster;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedTricksterRenderer<T extends Trickster,P extends ReplacedTrickster> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedTricksterRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedTricksterModel<>(), (P) new ReplacedTrickster());
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }
}
