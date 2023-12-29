package com.TBK.better_animation_mob.client.renderers.illager;

import com.TBK.better_animation_mob.client.models.illager.ReplacedPillagerModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedPillager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedPillagerRenderer<T extends Pillager,P extends ReplacedPillager> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedPillagerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedPillagerModel<>(), (P) new ReplacedPillager());
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            stack.mulPose(Vector3f.XP.rotationDegrees(270F));
            stack.mulPose(Vector3f.YP.rotationDegrees(-90.F));
            boolean shieldFlag = item.getItem() instanceof CrossbowItem;

            if (item == currentEntity.getMainHandItem()) {
                if (shieldFlag) {
                    stack.translate(-0.05F, 0.25F, 0.3F);
                }
            } else {
                if (shieldFlag) {
                    stack.translate(0, 0.0, -15);
                }
            }
        }
    }
}
