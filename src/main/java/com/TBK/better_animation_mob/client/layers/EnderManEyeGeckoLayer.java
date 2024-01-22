package com.TBK.better_animation_mob.client.layers;

import com.TBK.better_animation_mob.BetterAnimationMob;
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

    public EnderManEyeGeckoLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        getRenderer().render(
                getEntityModel().getModel(new ResourceLocation(BetterAnimationMob.MODID, "geo/enderman.geo.json")),
                entityLivingBaseIn,partialTicks, RenderType.eyes(getTextureEye()),matrixStackIn,
                bufferIn,bufferIn.getBuffer(RenderType.eyes(getTextureEye())),packedLightIn,
                LivingEntityRenderer.getOverlayCoords((LivingEntity) entityLivingBaseIn,partialTicks),1.0F,1.0F,1.0F,1.0F);
    }

    public ResourceLocation getTextureEye(){
        return new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
    }
}

