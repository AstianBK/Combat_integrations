package com.TBK.combat_integration.client.layers;

import com.TBK.combat_integration.CombatIntegration;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class IronGolemCrackingGeckoLayer<T extends Entity & IAnimatable> extends GeoLayerRenderer<T> {
    private static final Map<IronGolem.Crackiness, ResourceLocation> resourceLocations = ImmutableMap.of(IronGolem.Crackiness.LOW, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_low.png"), IronGolem.Crackiness.MEDIUM, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_medium.png"), IronGolem.Crackiness.HIGH, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_high.png"));


    public IronGolemCrackingGeckoLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entityLivingBaseIn instanceof IronGolem golem && golem.getCrackiness() != IronGolem.Crackiness.NONE){
            RenderType renderType=RenderType.entityCutoutNoCull(getTextureDrowned(golem));
            getRenderer().render(
                    getEntityModel().getModel(new ResourceLocation(CombatIntegration.MODID, "geo/irongolem.geo.json")),
                    entityLivingBaseIn,partialTicks, renderType,matrixStackIn,
                    bufferIn,bufferIn.getBuffer(renderType),packedLightIn,
                    LivingEntityRenderer.getOverlayCoords(golem,0.0F),1.0F,1.0F,1.0F,1.0F);
        }
    }

    public ResourceLocation getTextureDrowned(IronGolem golem){
        return resourceLocations.get(golem.getCrackiness());
    }
}

