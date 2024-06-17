package com.TBK.combat_integration.client.renderers.compi.bgn;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.layers.EnderManEyeGeckoLayer;
import com.TBK.combat_integration.client.models.compi.bgn.ReplacedWarpedEnderManModel;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedEnderMan;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.WarpedEnderMan;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedWarpedEnderManRenderer<T extends WarpedEnderMan,P extends ReplacedEnderMan<T>> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedWarpedEnderManRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedWarpedEnderManModel<>(), (P) new ReplacedEnderMan<T>());
        this.addLayer(new EnderManEyeGeckoLayer<>(this,new ResourceLocation(CombatIntegration.MODID,"geo/bgn/warped_enderman.geo.json"),new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/warped_enderman/warped_enderman_eyes.png")));
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }

    @Override
    public RenderType getRenderType(Object o, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}
