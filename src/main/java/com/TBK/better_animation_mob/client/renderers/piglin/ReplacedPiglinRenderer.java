package com.TBK.better_animation_mob.client.renderers.piglin;

import com.TBK.better_animation_mob.client.models.illager.ReplacedVindicatorModel;
import com.TBK.better_animation_mob.client.models.piglin.ReplacedPiglinModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.api.ICombos;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedPiglin;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedVindicator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedPiglinRenderer<T extends PiglinBrute,P extends ReplacedPiglin> extends ExtendedGeoReplacedEntityRenderer<T,P> {
    public ReplacedPiglinRenderer(EntityRendererProvider.Context renderManager){
        this(renderManager,new ReplacedPiglinModel<>(),(P) new ReplacedPiglin());
    }
    public ReplacedPiglinRenderer(EntityRendererProvider.Context renderManager, ReplacedPiglinModel<IAnimatable> model, P replaced) {
        super(renderManager, model, replaced);
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            boolean flag = currentEntity.getAttackAnim(currentPartialTicks)>0 && ((ICombos)currentEntity).getCombo()!=1;
            stack.mulPose(Vector3f.ZP.rotationDegrees(flag ? 60F : 200F));
            if (item == currentEntity.getMainHandItem()) {
                stack.translate(-0.05F,-0.25D,-0.45D);
            }
        }
    }

}
