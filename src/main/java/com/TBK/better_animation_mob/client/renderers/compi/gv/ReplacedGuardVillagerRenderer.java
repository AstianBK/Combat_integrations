package com.TBK.better_animation_mob.client.renderers.compi.gv;

import com.TBK.better_animation_mob.client.models.compi.gv.ReplacedGuardVillagerModel;
import com.TBK.better_animation_mob.client.models.illager.ReplacedPillagerModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.gv.ReplacedGuardVillager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import tallestegg.guardvillagers.entities.Guard;

public class ReplacedGuardVillagerRenderer<T extends Guard,P extends ReplacedGuardVillager> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedGuardVillagerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedGuardVillagerModel<>(), (P) new ReplacedGuardVillager<>());
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            stack.mulPose(Vector3f.XP.rotationDegrees(-90F));
            boolean shieldFlag = item.getItem() instanceof CrossbowItem;
            if (item == currentEntity.getMainHandItem()) {
                if (shieldFlag) {
                    stack.translate(-0.05F,0.15D,0.0D);
                }
            } else {
                if (shieldFlag) {
                    stack.translate(0, 0.0, -15);
                }
            }
        }
    }
}
