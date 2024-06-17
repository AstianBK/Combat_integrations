package com.TBK.combat_integration.client.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PowerableMob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class EnergySwirlGeckoLayer<T extends Entity & IAnimatable> extends GeoLayerRenderer<T> {
    private final ResourceLocation textureLocation;
    private final ResourceLocation modelLocation;

    public EnergySwirlGeckoLayer(IGeoRenderer<T> entityRendererIn, ResourceLocation eyeLocation, ResourceLocation modelLocation) {
        super(entityRendererIn);
        this.textureLocation =eyeLocation;
        this.modelLocation=modelLocation;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entityLivingBaseIn instanceof PowerableMob powerableMob && powerableMob.isPowered()){
            float f=partialTicks+entityLivingBaseIn.tickCount;
            getRenderer().render(
                    getEntityModel().getModel(this.modelLocation),
                    entityLivingBaseIn,partialTicks, RenderType.energySwirl(getTextureEye(),this.xOffset(f) % 1.0F, f * 0.01F % 1.0F),matrixStackIn,
                    bufferIn,bufferIn.getBuffer(RenderType.eyes(getTextureEye())),packedLightIn,
                    LivingEntityRenderer.getOverlayCoords((LivingEntity) entityLivingBaseIn,partialTicks),1.0F,1.0F,1.0F,1.0F);
        }
    }
    protected float xOffset(float ticks) {
        return Mth.cos(ticks * 0.02F) * 2.0F;
    }

    public ResourceLocation getTextureEye(){
        return this.textureLocation;
    }
}

