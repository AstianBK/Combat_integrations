package com.TBK.better_animation_mob.client.renderers.compi.bgn;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.layers.EnderManEyeGeckoLayer;
import com.TBK.better_animation_mob.client.models.bgn.ReplacedWarpedEnderManModel;
import com.TBK.better_animation_mob.client.models.enderman.ReplacedEnderManModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedEnderMan;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.WarpedEnderMan;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedWarpedEnderManRenderer<T extends WarpedEnderMan,P extends ReplacedEnderMan<T>> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedWarpedEnderManRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedWarpedEnderManModel<>(), (P) new ReplacedEnderMan<T>());
        this.addLayer(new EnderManEyeGeckoLayer<>(this,new ResourceLocation(BetterAnimationMob.MODID,"geo/bgn/warped_enderman.geo.json"),new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/warped_enderman/warped_enderman_eyes.png")));
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }

    @Override
    public RenderType getRenderType(Object o, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}
