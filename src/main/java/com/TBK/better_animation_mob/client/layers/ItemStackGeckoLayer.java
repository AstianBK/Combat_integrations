package com.TBK.better_animation_mob.client.layers;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

@OnlyIn(Dist.CLIENT)
public class ItemStackGeckoLayer<T extends LivingEntity & IAnimatable> extends GeoLayerRenderer<T> {
    AnimatedGeoModel<T> model;
    private final ResourceLocation textureBase;
    private final ResourceLocation modelLocation;
    private ItemStack itemStack;

    public ItemStackGeckoLayer(IGeoRenderer<T> geoRenderer, AnimatedGeoModel<T> model, ResourceLocation textureLocation, ResourceLocation modelLocation) {
        super(geoRenderer);
        this.model=model;
        this.textureBase= textureLocation;
        this.modelLocation=modelLocation;

    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Color renderColor = Color.WHITE;
        renderCopyModel(this.model,this.textureBase,matrixStackIn,bufferIn,packedLightIn,entityLivingBaseIn,partialTicks,renderColor.getRed() / 255f, renderColor.getGreen() / 255f,
                renderColor.getBlue() / 255f);
    }

    @Override
    protected void renderCopyModel(GeoModelProvider<T> modelProvider, ResourceLocation texture, PoseStack matrixStackIn, MultiBufferSource bufferSource, int packedLight, T animatable, float partialTick, float red, float green, float blue) {
        MultiBufferSource bufferSource1 = getRenderer().getCurrentRTB();
        RenderType renderType = getRenderType(texture);
        VertexConsumer glintBuffer = bufferSource1.getBuffer(renderType);
        preRender(matrixStackIn,animatable,modelProvider,renderType,bufferSource1,glintBuffer,partialTick,packedLight,OverlayTexture.NO_OVERLAY);
        for (GeoBone bone : modelProvider.getModel(this.modelLocation).topLevelBones){
            renderBoneChild(matrixStackIn,animatable,bone,bufferSource1,partialTick,packedLight);
        }
    }

    public void preRender(PoseStack poseStack, T animatable, GeoModelProvider<T> bakedModel, RenderType renderType, MultiBufferSource bufferSource,
                          VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        this.itemStack=animatable.getItemBySlot(EquipmentSlot.MAINHAND);
    }

    protected void renderBoneChild(PoseStack poseStack, T animatable, GeoBone bone, MultiBufferSource bufferSource, float partialTick,int packedLight){
        renderItemInHand(bone,animatable,poseStack,bufferSource,partialTick,packedLight);
        if(!bone.childBones.isEmpty()){
            for (GeoBone bone1 : bone.childBones){
                renderBoneChild(poseStack,animatable,bone1,bufferSource,partialTick,packedLight);
            }
        }
    }

    public void renderItemInHand(GeoBone bone,T animatable,PoseStack poseStack,MultiBufferSource bufferSource,float partialTick,int packedLight){
        //if (!(animatable instanceof Zombie enderman)) return;
        poseStack.pushPose();
        if(!bone.getName().equals("RightArm") )return;
        try (var pose = new CloseablePoseStack(poseStack)) {
            /*float lerped = Mth.rotLerp(partialTick, enderman.yBodyRotO, enderman.yBodyRot);
            pose.mulPose(Vector3f.YP.rotationDegrees(lerped));
            pose.translate(0, enderman.getType().getDimensions().height - 2.9, 0);

            pose.translate(0, animatable.getType().getDimensions().height - 2.9, 0);*/

            poseStack.mulPoseMatrix(bone.getLocalSpaceXform());
            pose.translate(0, 0, -0.1);

            pose.translate(0, 0.6875f, -0.75f);
            pose.mulPose(Vector3f.XP.rotationDegrees(20f));
            pose.mulPose(Vector3f.YP.rotationDegrees(45f));

            pose.translate(-1.35, 0.4, 1.35);
            pose.scale(0.5f, 0.5f, 0.5f);
            pose.mulPose(Vector3f.YP.rotationDegrees(90));
            Minecraft.getInstance().getItemRenderer().renderStatic(animatable.getMainHandItem(), ItemTransforms.TransformType.GROUND,packedLight, OverlayTexture.NO_OVERLAY,poseStack,bufferSource,animatable.getId());

        }
        poseStack.popPose();

    }
    protected void renderModel(GeoModelProvider<T> modelProvider,
                               ResourceLocation texture, PoseStack poseStack, MultiBufferSource bufferSource,
                               int packedLight, T animatable, float partialTick, float red, float green, float blue) {

    }
}

