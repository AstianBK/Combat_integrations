package com.TBK.better_animation_mob.client.renderers;

import com.TBK.better_animation_mob.client.models.ReplacedIllagerModel;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedIllagers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedExecutionerRenderer<T extends Vindicator,P extends ReplacedIllagers> extends ExtendedGeoReplacedEntityRenderer<T,P>{
    public ReplacedExecutionerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedIllagerModel<>(), (P) new ReplacedIllagers());
    }

    @Override
    public boolean shouldRenderItemStack(T currentEntity) {
        return currentEntity.getArmPose().equals(AbstractIllager.IllagerArmPose.ATTACKING);
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            boolean shieldFlag = item.getItem() instanceof ShieldItem;
            stack.mulPose(Vector3f.ZP.rotationDegrees(180F));
            if (item == currentEntity.getMainHandItem()) {
                if (shieldFlag) {

                }else {
                    stack.translate(-0.05F,-1.0D,-1.05D);
                }
            }
        }
    }

}
