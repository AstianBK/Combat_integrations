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
public class EnderManEyeGeckoLayer<T extends Entity & IAnimatable> extends GeoLayerRenderer<T> {
    public final ResourceLocation EYE;
    public final ResourceLocation GEO_LOCATION;

    public EnderManEyeGeckoLayer(IGeoRenderer<T> entityRendererIn,ResourceLocation geoLocation,ResourceLocation eyeLocation) {
        super(entityRendererIn);
        EYE=eyeLocation;
        GEO_LOCATION=geoLocation;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(EYE!=null && GEO_LOCATION!=null){
            getRenderer().render(
                    getEntityModel().getModel(GEO_LOCATION),
                    entityLivingBaseIn,partialTicks, RenderType.eyes(getTextureEye()),matrixStackIn,
                    bufferIn,bufferIn.getBuffer(RenderType.eyes(getTextureEye())),packedLightIn,
                    LivingEntityRenderer.getOverlayCoords((LivingEntity) entityLivingBaseIn,partialTicks),1.0F,1.0F,1.0F,1.0F);
        }
    }

    public ResourceLocation getTextureEye(){
        return EYE;
    }
}

