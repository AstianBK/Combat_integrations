package com.TBK.combat_integration.client.renderers.illager;

import com.TBK.combat_integration.client.models.illager.ReplacedExecutionerModel;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.svr.ReplacedExecutioner;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Executioner;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedExecutionerRenderer<T extends Executioner,P extends ReplacedExecutioner<T>> extends ReplacedVindicatorRenderer<T,P>{
    public ReplacedExecutionerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ReplacedExecutionerModel<>(),(P) new ReplacedExecutioner<T>());
    }

    @Override
    public boolean shouldRenderItemStack(T currentEntity) {
        return true;
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            stack.mulPose(Vector3f.XP.rotationDegrees(-90F));
            if (item == currentEntity.getMainHandItem()) {
                stack.translate(-0.05F,0.3D,-0.15D);
            }
        }
    }

}
