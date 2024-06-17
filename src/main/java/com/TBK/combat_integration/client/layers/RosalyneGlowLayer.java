package com.TBK.combat_integration.client.layers;

import com.TBK.combat_integration.CombatIntegration;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.RosalyneEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class RosalyneGlowLayer<T extends Entity & IAnimatable> extends GeoLayerRenderer<T> {
    private static final ResourceLocation COFFIN_GLOW = MeetYourFight.rl("textures/entity/rosalyne_coffin_glow.png");
    private static final ResourceLocation BASE_GLOW = MeetYourFight.rl("textures/entity/rosalyne_glow.png");

    public RosalyneGlowLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entityLivingBaseIn instanceof RosalyneEntity rosalyneEntity){
            int phase = rosalyneEntity.getPhase();
            ResourceLocation resourceLocation=phase != 0 && phase != 1 ? BASE_GLOW : COFFIN_GLOW;
            RenderType renderType=RenderType.entityTranslucentEmissive(resourceLocation,false);
            VertexConsumer vertexconsumer = bufferIn.getBuffer(renderType);
            getRenderer().render(
                    getEntityModel().getModel(new ResourceLocation(CombatIntegration.MODID,"geo/myf/rosalyne.geo.json")),
                    entityLivingBaseIn,partialTicks,renderType ,matrixStackIn,
                    bufferIn,vertexconsumer,15728640,
                    OverlayTexture.NO_OVERLAY,1.0F,1.0F,1.0F,1.0F);

        }
    }

    public ResourceLocation getTextureDrowned(){
        return new ResourceLocation("textures/entity/zombie/drowned_outer_layer.png");
    }
}

