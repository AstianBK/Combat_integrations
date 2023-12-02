package com.TBK.better_animation_mob.client.renderers.illager;

import com.TBK.better_animation_mob.client.models.illager.ReplacedEvokerModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedEvoker;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedEvokerRenderer<T extends Evoker,P extends ReplacedEvoker> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedEvokerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedEvokerModel<>(), (P) new ReplacedEvoker());
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }
}
