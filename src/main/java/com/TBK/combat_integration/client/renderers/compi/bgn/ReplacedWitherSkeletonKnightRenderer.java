package com.TBK.combat_integration.client.renderers.compi.bgn;

import com.TBK.combat_integration.client.models.compi.bgn.ReplacedWitherSkeletonKnightModel;
import com.TBK.combat_integration.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.WitherSkeletonKnight;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class ReplacedWitherSkeletonKnightRenderer<T extends WitherSkeletonKnight,P extends ReplacedSkeleton<T>> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedWitherSkeletonKnightRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedWitherSkeletonKnightModel(), (P) new ReplacedSkeleton<T>(),new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/wither/wither_skeleton_knight.png"));
    }
    @Override
    public void render(GeoModel model, Object o, float partialTick, RenderType type, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
        super.render(model, o, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            boolean trident = item.getItem() instanceof BowItem;
            boolean shield = item.getItem() instanceof ShieldItem;
            if (item == currentEntity.getMainHandItem()) {
                if (trident) {
                    stack.mulPose(Vector3f.XP.rotationDegrees(-90F));
                    stack.translate(0.0D,0.15D,0.0D);
                }else if(!shield){
                    stack.mulPose(Vector3f.YP.rotationDegrees(180F));
                    stack.mulPose(Vector3f.XP.rotationDegrees(-225F));
                    stack.translate(-0.07D,0.1D,-0.1D);
                }
            }
            if(item == currentEntity.getOffhandItem()){
                if(shield){
                    if(currentEntity.isUsingShield()){
                        stack.mulPose(Vector3f.XP.rotationDegrees(-90F));
                        stack.mulPose(Vector3f.YP.rotationDegrees(90F));
                        stack.mulPose(Vector3f.ZP.rotationDegrees(-15F));
                        stack.translate(-0.9D,0.5D,-0.85D);
                    }else {
                        stack.mulPose(Vector3f.XP.rotationDegrees(-90F));
                        stack.mulPose(Vector3f.YP.rotationDegrees(180F));
                        stack.translate(0.05D,0.25D,-1.5D);
                    }
                }
            }
        }
    }

}
