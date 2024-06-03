package com.TBK.better_animation_mob.client.renderers.compi.bgn;

import com.TBK.better_animation_mob.client.models.compi.bgn.ReplacedCorporModel;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.Corpor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedCorporRenderer<T extends Corpor,P extends ReplacedSkeleton<T>> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedCorporRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedCorporModel<>(), (P) new ReplacedSkeleton<T>(),new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/wither/corpor.png"));
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
                    stack.translate(0.05D,0.1D,-0.1D);
                }
            }
        }
    }
}
