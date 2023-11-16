package com.TBK.better_animation_mob.client.renderers.illager;

import com.TBK.better_animation_mob.client.models.illager.ReplacedExecutionerModel;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedIllagers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Executioner;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedExecutionerRenderer<T extends Executioner,P extends ReplacedIllagers> extends ReplacedVindicatorRenderer<T,P>{
    public ReplacedExecutionerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ReplacedExecutionerModel<>(),(P) new ReplacedIllagers());
    }

    @Override
    public boolean shouldRenderItemStack(T currentEntity) {
        return currentEntity.getArmPose().equals(AbstractIllager.IllagerArmPose.ATTACKING);
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            stack.mulPose(Vector3f.ZP.rotationDegrees(180F));
            if (item == currentEntity.getMainHandItem()) {
                stack.translate(-0.05F,-1.0D,-1.05D);
            }
        }
    }

}
