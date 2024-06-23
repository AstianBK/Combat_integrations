package com.TBK.combat_integration.client.renderers.compi.qrk;

import com.TBK.combat_integration.client.models.compi.qrk.ReplacedWraithModel;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.qrk.ReplacedWraith;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import vazkii.quark.content.mobs.entity.Wraith;

public class ReplacedWraithRenderer<T extends Wraith,P extends ReplacedWraith> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedWraithRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ReplacedWraithModel<>(), (P) new ReplacedWraith<>());
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }

    @Override
    public RenderType getRenderType(Object o, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}
