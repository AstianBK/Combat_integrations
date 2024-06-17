package com.TBK.combat_integration.client.renderers.illager;

import com.TBK.combat_integration.client.models.illager.ReplacedVindicatorModel;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedVindicator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedVindicatorRenderer<T extends Vindicator,P extends ReplacedVindicator<T>> extends ExtendedGeoReplacedEntityRenderer<T,P> {
    public ReplacedVindicatorRenderer(EntityRendererProvider.Context renderManager){
        this(renderManager,new ReplacedVindicatorModel<>(),(P) new ReplacedVindicator<T>());
    }
    public ReplacedVindicatorRenderer(EntityRendererProvider.Context renderManager,ReplacedVindicatorModel<IAnimatable> model,P replaced) {
        super(renderManager, model, replaced);
    }

    @Override
    public boolean shouldRenderItemStack(T currentEntity) {
        return currentEntity.getArmPose().equals(AbstractIllager.IllagerArmPose.ATTACKING);
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            stack.mulPose(Vector3f.XP.rotationDegrees(-90F));
            if (item == currentEntity.getMainHandItem()) {
                stack.translate(-0.0F,0.0D,0.0D);
            }
        }
    }

}
