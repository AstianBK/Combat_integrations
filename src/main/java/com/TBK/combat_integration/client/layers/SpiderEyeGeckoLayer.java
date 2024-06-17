package com.TBK.combat_integration.client.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class SpiderEyeGeckoLayer<T extends Entity & IAnimatable> extends GeoLayerRenderer<T> {
    private final ResourceLocation eyeLocation;
    private final ResourceLocation modelLocation;

    public SpiderEyeGeckoLayer(IGeoRenderer<T> entityRendererIn,ResourceLocation eyeLocation,ResourceLocation modelLocation) {
        super(entityRendererIn);
        this.eyeLocation=eyeLocation;
        this.modelLocation=modelLocation;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        getRenderer().render(
                getEntityModel().getModel(this.modelLocation),
                entityLivingBaseIn,partialTicks, RenderType.eyes(getTextureEye()),matrixStackIn,
                bufferIn,bufferIn.getBuffer(RenderType.eyes(getTextureEye())),packedLightIn,
                LivingEntityRenderer.getOverlayCoords((LivingEntity) entityLivingBaseIn,partialTicks),1.0F,1.0F,1.0F,1.0F);
    }

    public ResourceLocation getTextureEye(){
        return this.eyeLocation;
    }
}

