package com.TBK.better_animation_mob.client.renderers.compi.myf;

import com.TBK.better_animation_mob.client.models.compi.myf.ReplacedSwampJawModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.myf.ReplacedSwampJaw;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import lykrast.meetyourfight.entity.SwampjawEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.geo.render.built.GeoBone;

@OnlyIn(Dist.CLIENT)
public class ReplacedSwampJawRenderer<T extends SwampjawEntity,P extends ReplacedSwampJaw> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedSwampJawRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ReplacedSwampJawModel<>(), (P) new ReplacedSwampJaw<>());
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
