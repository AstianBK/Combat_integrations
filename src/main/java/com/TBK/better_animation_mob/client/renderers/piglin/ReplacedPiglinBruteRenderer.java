package com.TBK.better_animation_mob.client.renderers.piglin;

import com.TBK.better_animation_mob.client.models.piglin.ReplacedPiglinBruteModel;
import com.TBK.better_animation_mob.client.models.piglin.ReplacedPiglinModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedPiglin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedPiglinBruteRenderer<T extends PiglinBrute,P extends ReplacedPiglin> extends ReplacedPiglinRenderer<T,P> {
    public ReplacedPiglinBruteRenderer(EntityRendererProvider.Context renderManager){
        super(renderManager,new ReplacedPiglinBruteModel<>(),(P) new ReplacedPiglin(),new ResourceLocation("textures/entity/piglin/piglin_brute.png"));
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            stack.mulPose(Vector3f.ZP.rotationDegrees(200F));
            if (item == currentEntity.getMainHandItem()) {
                stack.translate(-0.05F,-0.25D,-0.45D);
            }
        }
    }

}
