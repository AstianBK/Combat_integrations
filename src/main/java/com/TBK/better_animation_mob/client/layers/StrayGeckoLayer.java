package com.TBK.better_animation_mob.client.layers;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class StrayGeckoLayer<T extends Entity & IAnimatable> extends GeoLayerRenderer<T> {

    protected static final HumanoidModel<LivingEntity> OUTER_ARMOR_MODEL = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.STRAY_OUTER_LAYER));

    AnimatedGeoModel<T> model;
    private final ResourceLocation textureBase;
    private final ResourceLocation modelLocation;
    private final ResourceLocation overlayLocation;


    public StrayGeckoLayer(IGeoRenderer<T> entityRendererIn, AnimatedGeoModel<T> model,ResourceLocation textureLocation,ResourceLocation modelLocation,ResourceLocation overlayLocation) {
        super(entityRendererIn);
        this.model=model;
        this.textureBase= textureLocation;
        this.modelLocation=modelLocation;
        this.overlayLocation=overlayLocation;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        renderCopyModel(this.model,this.textureBase,matrixStackIn,bufferIn,packedLightIn,entityLivingBaseIn,partialTicks,1, 1,
                1);
    }

    @Override
    protected void renderCopyModel(GeoModelProvider<T> modelProvider, ResourceLocation texture, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T animatable, float partialTick, float red, float green, float blue) {
        RenderType renderType = getRenderType(texture);
        VertexConsumer glintBuffer = bufferSource.getBuffer(renderType);
        for (GeoBone bone : modelProvider.getModel(this.modelLocation).topLevelBones){
            renderBoneChild(poseStack,animatable,bone,renderType,bufferSource,glintBuffer,partialTick,packedLight);
        }
    }

    protected void renderBoneChild(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,int packedLight){
        poseStack.pushPose();
        bone=copyBoneLayer(bone,this.model.getModel(this.modelLocation));
        RenderUtils.translateMatrixToBone(poseStack, bone);
        RenderUtils.translateToPivotPoint(poseStack, bone);

        boolean rotOverride = bone.rotMat != null;

        if (rotOverride) {
            poseStack.last().pose().multiply(bone.rotMat);
            poseStack.last().normal().mul(new Matrix3f(bone.rotMat));
        }
        else {
            RenderUtils.rotateMatrixAroundBone(poseStack, bone);
        }

        RenderUtils.scaleMatrixForBone(poseStack, bone);

        if (bone.isTrackingXform()) {
            Matrix4f poseState = poseStack.last().pose().copy();
            Matrix4f localMatrix = RenderUtils.invertAndMultiplyMatrices(poseState, ((ExtendedGeoReplacedEntityRenderer<?,?>)this.getRenderer()).getDispatchedMat());

            bone.setModelSpaceXform(RenderUtils.invertAndMultiplyMatrices(poseState, ((ExtendedGeoReplacedEntityRenderer<?,?>)this.getRenderer()).getRenderEarly()));
            localMatrix.translate(new Vector3f(((ExtendedGeoReplacedEntityRenderer<?, ?>) this.getRenderer()).getRenderOffset(((ExtendedGeoReplacedEntityRenderer<?, ?>) this.getRenderer()).getCurrentEntity(), 1)));
            bone.setLocalSpaceXform(localMatrix);

            Matrix4f worldState = localMatrix.copy();

            worldState.translate(new Vector3f((((ExtendedGeoReplacedEntityRenderer<?, ?>) this.getRenderer()).getCurrentEntity()).position()));
            bone.setWorldSpaceXform(worldState);
        }

        RenderUtils.translateAwayFromPivotPoint(poseStack, bone);

        renderForBone(poseStack,animatable,bone,renderType,bufferSource,buffer,partialTick,packedLight,getPackedOverlay((((ExtendedGeoReplacedEntityRenderer<?, ?>) this.getRenderer()).getCurrentEntity()),((((ExtendedGeoReplacedEntityRenderer<?, ?>) this.getRenderer()).getCurrentPartialTicks()))));
        if(!bone.childBones.isEmpty()){
            for (GeoBone bone1 : bone.childBones){
                renderBoneChild(poseStack,animatable,bone1,renderType,bufferSource,buffer,partialTick,packedLight);
            }
        }

        poseStack.popPose();
    }

    public static int getPackedOverlay(LivingEntity entity, float u) {
        return OverlayTexture.pack(OverlayTexture.u(u),
                OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }

    @Nonnull
    protected ModelPart getModelPartForBone(GeoBone bone, T animatable, HumanoidModel<?> armorModel) {
        ModelPart part=null;
        switch (bone.getName()){
            case "HatLayer"->part=armorModel.head;
            case "BodyLayer"->part=armorModel.body;
            case "RightArmLayer"->part=armorModel.rightArm;
            case "LeftArmLayer"->part=armorModel.leftArm;
            case "RightLegLayer","RightBootLayer"->part=armorModel.rightLeg;
            case "LeftLegLayer","LeftBootLayer"->part=armorModel.leftLeg;
        }
        return part;
    }
    public GeoBone copyBoneLayer(GeoBone bone, GeoModel model){
        GeoBone bone1=null;
        switch (bone.getName()){
            case "HatLayer"->bone1=model.getBone("Head").isPresent() ? model.getBone("Head").get() : bone;
            case "BodyLayer"->bone1=model.getBone("Body").isPresent() ? model.getBone("Body").get() : bone;
            case "RightArmLayer"->bone1=model.getBone("RightArm").isPresent() ? model.getBone("RightArm").get() : bone;
            case "LeftArmLayer"->bone1=model.getBone("LeftArm").isPresent() ? model.getBone("LeftArm").get() : bone;
            case "RightLegLayer","RightBootLayer"->bone1=model.getBone("RightLeg").isPresent() ? model.getBone("RightLeg").get() : bone;
            case "LeftLegLayer","LeftBootLayer"->bone1=model.getBone("LeftLeg").isPresent() ? model.getBone("LeftLeg").get() : bone;
        }
        return copyPropeties(bone,bone1,model.getBone("Main"));
    }



    public GeoBone copyPropeties(GeoBone base, GeoBone toCopy, Optional<GeoBone> main){
        if(toCopy==null || main.isEmpty())return base;
        base.setPositionX(toCopy.getPositionX());
        base.setPositionY(toCopy.getPositionY());
        base.setPositionZ(toCopy.getPositionZ());
        base.setRotation(toCopy.getRotation());
        base.setWorldSpaceXform(toCopy.getWorldSpaceXform());
        base.setModelPosition(toCopy.getModelPosition());
        base.setLocalSpaceXform(toCopy.getLocalSpaceXform());
        base.setModelSpaceXform(toCopy.getModelSpaceXform());
        base.setTrackXform(toCopy.isTrackingXform());
        base.setScale(toCopy.getScaleX(),toCopy.getScaleY(),toCopy.getScaleZ());
        base.addRotationOffsetFromBone(main.get());
        return base;
    }

    public void renderForBone(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource,
                              VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        HumanoidModel<?> model = OUTER_ARMOR_MODEL;
        ModelPart modelPart = getModelPartForBone(bone, animatable, model);
        if(modelPart==null)return;
        if (!modelPart.cubes.isEmpty()) {
            poseStack.pushPose();
            poseStack.scale(-1, -1, 1);

            prepModelPartForRender(poseStack,bone,modelPart);
            renderVanillaArmorPiece(poseStack, animatable, bone, modelPart, bufferSource, partialTick, packedLight, packedOverlay);


            poseStack.popPose();
        }
    }

    protected <I extends Item & IGeoRenderer> void renderVanillaArmorPiece(PoseStack poseStack, T animatable, GeoBone bone,
                                                                           ModelPart modelPart, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
        bufferSource=getRenderer().getCurrentRTB();
        ResourceLocation texture = getTextureStray();
        VertexConsumer buffer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));

        modelPart.render(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }


    protected void prepModelPartForRender(PoseStack poseStack, GeoBone bone, ModelPart sourcePart) {
        final GeoCube firstCube = bone.childCubes.get(0);
        final ModelPart.Cube armorCube = sourcePart.cubes.get(0);
        final double armorBoneSizeX = firstCube.size.x();
        final double armorBoneSizeY = firstCube.size.y();
        final double armorBoneSizeZ = firstCube.size.z();
        final double actualArmorSizeX = Math.abs(armorCube.maxX - armorCube.minX);
        final double actualArmorSizeY = Math.abs(armorCube.maxY - armorCube.minY);
        final double actualArmorSizeZ = Math.abs(armorCube.maxZ - armorCube.minZ);
        float scaleX = (float)(armorBoneSizeX / actualArmorSizeX);
        float scaleY = (float)(armorBoneSizeY / actualArmorSizeY);
        float scaleZ = (float)(armorBoneSizeZ / actualArmorSizeZ);

        sourcePart.setPos(-(bone.getPivotX() - ((bone.getPivotX() * scaleX) - bone.getPivotX()) / scaleX),
                -(bone.getPivotY() - ((bone.getPivotY() * scaleY) - bone.getPivotY()) / scaleY),
                (bone.getPivotZ() - ((bone.getPivotZ() * scaleZ) - bone.getPivotZ()) / scaleZ));

        sourcePart.xRot = -bone.getRotationX();
        sourcePart.yRot = -bone.getRotationY();
        sourcePart.zRot = bone.getRotationZ();

        poseStack.scale(scaleX, scaleY, scaleZ);
    }

    public ResourceLocation getTextureStray(){
        return this.overlayLocation;
    }
}

