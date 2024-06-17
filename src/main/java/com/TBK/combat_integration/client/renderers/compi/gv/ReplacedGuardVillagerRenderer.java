package com.TBK.combat_integration.client.renderers.compi.gv;

import com.TBK.combat_integration.client.models.compi.gv.ReplacedGuardVillagerModel;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.gv.ReplacedGuardVillager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.*;
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
            boolean shield = item.getItem() instanceof ShieldItem;
            if (item == currentEntity.getMainHandItem()) {
                if (shieldFlag) {
                    stack.translate(-0.05F,0.15D,0.0D);
                }else if(!shield){
                    stack.translate(0.0D,-0.02D,-0.05D);
                }
            }
            if(item == currentEntity.getOffhandItem()){
                if(shield){
                    if(currentEntity.getUseItem().getUseAnimation()==UseAnim.BLOCK){
                        stack.mulPose(Vector3f.XP.rotationDegrees(-30F));
                        stack.mulPose(Vector3f.YP.rotationDegrees(60F));
                        stack.mulPose(Vector3f.ZP.rotationDegrees(15F));
                        stack.translate(-0.6D,0.6D,-0.8D);
                    }else {
                        stack.mulPose(Vector3f.YP.rotationDegrees(180F));
                        stack.translate(0.01D,0.0D,-1.5D);
                    }
                }
            }
        }
    }
}
