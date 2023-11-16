package com.TBK.better_animation_mob.client.layers;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Stray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class StrayGeckoLayer<T extends Entity & IAnimatable> extends GeoLayerRenderer<T> {

    public StrayGeckoLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entityLivingBaseIn instanceof Stray stray){
            getRenderer().render(
                    getEntityModel().getModel(new ResourceLocation(BetterAnimationMob.MODID, "geo/skeleton.geo.json")),
                    entityLivingBaseIn,partialTicks, RenderType.armorCutoutNoCull(getTextureStray()),matrixStackIn,
                    bufferIn,bufferIn.getBuffer(RenderType.entityCutoutNoCull(getTextureStray())),packedLightIn,
                    LivingEntityRenderer.getOverlayCoords(stray,partialTicks),1.0F,1.0F,1.0F,1.0F);
        }
    }

    public ResourceLocation getTextureStray(){
        return new ResourceLocation("textures/entity/skeleton/stray_overlay.png");
    }
}

