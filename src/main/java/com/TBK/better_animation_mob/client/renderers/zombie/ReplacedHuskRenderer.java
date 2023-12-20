package com.TBK.better_animation_mob.client.renderers.zombie;

import com.TBK.better_animation_mob.client.models.zombie.ReplacedHuskModel;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedZombie;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Husk;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class ReplacedHuskRenderer<T extends Husk,P extends ReplacedZombie> extends ReplacedZombieRenderer<T,P>{

    public ReplacedHuskRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ResourceLocation("textures/entity/zombie/husk.png"),new ReplacedHuskModel());
    }

    @Override
    public void render(GeoModel model, Object o, float partialTick, RenderType type, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.scale(1.0625F, 1.0625F, 1.0625F);
        super.render(model, o, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
