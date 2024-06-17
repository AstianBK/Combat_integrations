package com.TBK.combat_integration.client.renderers.compi.myf;

import com.TBK.combat_integration.client.models.compi.myf.ReplacedBellringerModel;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.myf.ReplacedBellringer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import lykrast.meetyourfight.entity.BellringerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.geo.render.built.GeoBone;

@OnlyIn(Dist.CLIENT)
public class ReplacedBellringerRenderer<T extends BellringerEntity,P extends ReplacedBellringer> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedBellringerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ReplacedBellringerModel<>(), (P) new ReplacedBellringer<>());
        this.shadowRadius=0.5F;
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
                    stack.translate(0.05,0.2D,0.0D);
                }
            }
        }
    }
}
