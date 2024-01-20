package com.TBK.better_animation_mob.client.renderers.wolf;

import com.TBK.better_animation_mob.client.models.illager.ReplacedRavagerModel;
import com.TBK.better_animation_mob.client.models.wolf.ReplacedWolfModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedRavager;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedWolf;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedWolfRenderer<T extends Wolf,P extends ReplacedWolf<T>> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedWolfRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedWolfModel(), (P)new ReplacedWolf<>());
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }

    @Override
    public RenderType getRenderType(Object o, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}
