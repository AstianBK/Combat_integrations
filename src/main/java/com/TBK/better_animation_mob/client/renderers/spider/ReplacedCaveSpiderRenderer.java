package com.TBK.better_animation_mob.client.renderers.spider;

import com.TBK.better_animation_mob.client.models.spider.ReplacedCaveSpiderModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedSpider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class ReplacedCaveSpiderRenderer<T extends CaveSpider,P extends ReplacedSpider<T>> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedCaveSpiderRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ReplacedCaveSpiderModel<>(),(P) new ReplacedSpider<T>());
        this.shadowRadius *= 0.7F;
    }

    @Override
    public void render(GeoModel model, Object o, float partialTick, RenderType type, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.scale(0.7F,0.7F,0.7F);
        super.render(model, o, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public boolean shouldRenderItemStack(T currentEntity) {
        return false;
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }
}
