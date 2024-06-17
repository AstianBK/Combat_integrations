package com.TBK.combat_integration.client.layers;

import com.TBK.combat_integration.CombatIntegration;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class CollarGeckoLayer<T extends Entity & IAnimatable> extends GeoLayerRenderer<T> {

    public CollarGeckoLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entityLivingBaseIn instanceof Wolf wolf && wolf.isTame()){
            float f[] = wolf.getCollarColor().getTextureDiffuseColors();
            getRenderer().render(
                    getEntityModel().getModel(new ResourceLocation(CombatIntegration.MODID, "geo/wolf.geo.json")),
                    entityLivingBaseIn,partialTicks, RenderType.armorCutoutNoCull(getTextureDrowned()),matrixStackIn,
                    bufferIn,bufferIn.getBuffer(RenderType.entityCutoutNoCull(getTextureDrowned())),packedLightIn,
                    LivingEntityRenderer.getOverlayCoords(wolf,partialTicks),f[0],f[1],f[2],1.0F);
        }
    }

    public ResourceLocation getTextureDrowned(){
        return new ResourceLocation("textures/entity/wolf/wolf_collar.png");
    }
}

