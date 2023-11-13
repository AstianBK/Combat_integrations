package com.TBK.better_animation_mob.client.layers;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedZombieModel;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedZombie;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class DrownedGeckoLayer<T extends Entity & IAnimatable> extends GeoLayerRenderer<T> {

    public DrownedGeckoLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entityLivingBaseIn instanceof Drowned){
            getRenderer().render(
                    getEntityModel().getModel(new ResourceLocation(BetterAnimationMob.MODID, "geo/zombie.geo.json")),
                    entityLivingBaseIn,partialTicks, RenderType.armorCutoutNoCull(getTextureDrowned()),matrixStackIn,
                    bufferIn,bufferIn.getBuffer(RenderType.entityCutoutNoCull(getTextureDrowned())),packedLightIn,
                    OverlayTexture.NO_OVERLAY,1.0F,1.0F,1.0F,1.0F);
        }
    }

    public ResourceLocation getTextureDrowned(){
        return new ResourceLocation("textures/entity/zombie/drowned_outer_layer.png");
    }
}

