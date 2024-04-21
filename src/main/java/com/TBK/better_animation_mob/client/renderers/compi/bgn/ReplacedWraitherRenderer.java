package com.TBK.better_animation_mob.client.renderers.compi.bgn;

import com.TBK.better_animation_mob.client.models.bgn.ReplacedWitherSkeletonKnightModel;
import com.TBK.better_animation_mob.client.models.bgn.ReplacedWraitherModel;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedWitherSkeleton;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.WitherSkeletonKnight;
import com.izofar.bygonenether.entity.Wraither;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class ReplacedWraitherRenderer<T extends Wraither,P extends ReplacedSkeleton<T>> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedWraitherRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedWraitherModel<>(), (P) new ReplacedSkeleton<T>(),new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/wither/wraither.png"));
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            boolean trident = item.getItem() instanceof TridentItem;
            if (item == currentEntity.getMainHandItem()) {
                stack.mulPose(Vector3f.XP.rotationDegrees(-90F));
                if (trident) {
                    stack.translate(0.05D,0.0D,0.0D);
                }else {
                    stack.translate(0.05,0.2D,0.1D);
                }
            }
        }
    }
}
