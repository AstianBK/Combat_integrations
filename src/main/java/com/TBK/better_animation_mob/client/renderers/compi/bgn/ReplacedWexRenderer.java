package com.TBK.better_animation_mob.client.renderers.compi.bgn;

import com.TBK.better_animation_mob.client.models.compi.bgn.ReplacedWexModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.bgn.ReplacedWex;
import com.izofar.bygonenether.entity.Wex;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedWexRenderer<T extends Wex,P extends ReplacedWex<T>> extends ExtendedGeoReplacedEntityRenderer<T,P> {
    public ReplacedWexRenderer(EntityRendererProvider.Context renderManager){
        super(renderManager,new ReplacedWexModel<>(),(P) new ReplacedWex<>());
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            boolean trident = item.getItem() instanceof BowItem;
            if (item == currentEntity.getMainHandItem()) {
                if (trident) {
                    stack.mulPose(Vector3f.XP.rotationDegrees(-90F));
                    stack.translate(0.15D,-0.0D,0.05D);
                }else {
                    stack.translate(0.05,-0.25D,-0.5D);
                }
            }
        }
    }

    @Override
    public RenderType getRenderType(Object o, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}
