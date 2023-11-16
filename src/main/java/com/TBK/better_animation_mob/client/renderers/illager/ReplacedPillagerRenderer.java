package com.TBK.better_animation_mob.client.renderers.illager;

import com.TBK.better_animation_mob.client.models.illager.ReplacedPillagerModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedPillager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedPillagerRenderer<T extends Pillager,P extends ReplacedPillager> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedPillagerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedPillagerModel<>(), (P) new ReplacedPillager());
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            boolean trident = item.getItem() instanceof TridentItem;
            if (item == currentEntity.getMainHandItem()) {
                if (trident) {
                    stack.translate(0.0D,0.0D,-0.8D);
                }else {
                    stack.mulPose(Vector3f.XP.rotationDegrees(-180F));
                    stack.mulPose(Vector3f.YP.rotationDegrees(90F));
                    stack.translate(0.0F, 0.125F, 0.0F);
                }
            }
        }
    }
}
