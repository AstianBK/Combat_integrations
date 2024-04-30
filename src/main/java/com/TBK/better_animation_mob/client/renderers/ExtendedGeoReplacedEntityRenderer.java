package com.TBK.better_animation_mob.client.renderers;

import com.TBK.better_animation_mob.client.models.ReplacedEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;
import software.bernie.geckolib3.util.EModelRenderCycle;
import software.bernie.geckolib3.util.RenderUtils;

import javax.annotation.Nullable;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public abstract class ExtendedGeoReplacedEntityRenderer<T extends LivingEntity,P extends IAnimatable> extends GeoReplacedEntityRenderer<P> {
    private float currentPartialTicks;

    protected T currentEntity;
    protected EntityModel<?> modelBase;

    public ExtendedGeoReplacedEntityRenderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<IAnimatable> model , P animatable) {
        super(renderManager, model, animatable);
        ((ReplacedEntityModel<?>) getGeoModelProvider()).setCurrentEntity(this::getCurrentEntity);
    }

    protected abstract void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone,float currentPartialTicks);

    protected void renderItemStack(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ItemStack stack,
                                   String boneName) {
        Minecraft.getInstance().getItemRenderer().renderStatic(this.currentEntity, stack,
                boneName.equals("leftItem") || boneName.equals("ItemSlotLeft") ? ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND : ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false, poseStack, bufferSource, null, packedLight,
                LivingEntityRenderer.getOverlayCoords(this.currentEntity, 0.0F),
                currentEntity.getId());
    }

    public void render(Entity entity, IAnimatable animatable, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, EntityModel<?> model) {
        this.modelBase=model;
        super.render(entity,animatable, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public void renderLate(Object animatable, PoseStack poseStack, float partialTick, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderLate(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.currentPartialTicks=partialTick;
    }

    public Matrix4f getRenderEarly(){
        return this.renderEarlyMat;
    }
    public Matrix4f getDispatchedMat(){
        return this.dispatchedMat;
    }

    public float getCurrentPartialTicks(){
        return this.currentPartialTicks;
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        MultiBufferSource bufferSource = getCurrentRTB();
        if (getCurrentModelRenderCycle() != EModelRenderCycle.INITIAL) {
            poseStack.pushPose();
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
                Matrix4f localMatrix = RenderUtils.invertAndMultiplyMatrices(poseState, this.dispatchedMat);

                bone.setModelSpaceXform(RenderUtils.invertAndMultiplyMatrices(poseState, this.renderEarlyMat));
                localMatrix.translate(new Vector3f(getRenderOffset((Entity)this.currentEntity, 1)));
                bone.setLocalSpaceXform(localMatrix);

                Matrix4f worldState = localMatrix.copy();

                worldState.translate(new Vector3f(((Entity)this.currentEntity).position()));
                bone.setWorldSpaceXform(worldState);
            }

            RenderUtils.translateAwayFromPivotPoint(poseStack, bone);

            if (!bone.isHidden) {
                if (!bone.cubesAreHidden()) {
                    for (GeoCube geoCube : bone.childCubes) {
                        poseStack.pushPose();
                        renderCube(geoCube, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                        poseStack.popPose();
                    }
                }

                for (GeoBone childBone : bone.childBones) {
                    renderRecursively(childBone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                }

            }

            poseStack.popPose();
            return;
        }
        buffer=bufferSource.getBuffer(getRenderType(this.currentEntity, this.currentPartialTicks, poseStack,
                bufferSource, buffer, packedLight, this.getTextureLocation(this.currentEntity)));
        if (getCurrentModelRenderCycle() == EModelRenderCycle.INITIAL) {
            poseStack.pushPose();
            if (shouldRenderItemStack(this.currentEntity)){
                if(bone.getName().equals("ItemSlotRight") || bone.getName().equals("ItemSlotLeft") || bone.getName().equals("leftItem")){
                    ItemStack boneItem;
                    if(bone.getName().equals("ItemSlotRight")){
                        boneItem=this.currentEntity.getMainHandItem();
                    }else {
                        boneItem=this.currentEntity.getOffhandItem();
                    }

                    if (!boneItem.isEmpty()) {
                        handleItemAndBlockBoneRendering(poseStack, bone, boneItem, packedLight, packedOverlay);
                        buffer = bufferSource.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(this.currentEntity)));
                    }
                }
            }
            poseStack.popPose();
        }
        poseStack.pushPose();
        RenderUtils.prepMatrixForBone(poseStack, bone);
        super.renderCubesOfBone(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        super.renderChildBones(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
    protected void handleItemAndBlockBoneRendering(PoseStack poseStack, GeoBone bone, @Nullable ItemStack boneItem, int packedLight, int packedOverlay) {
        RenderUtils.prepMatrixForBone(poseStack, bone);
        RenderUtils.translateAndRotateMatrixForBone(poseStack, bone);

        if (boneItem != null) {
            preRenderItem(poseStack, boneItem, bone.getName(), this.currentEntity, bone,this.currentPartialTicks);
            renderItemStack(poseStack, getCurrentRTB(), packedLight, boneItem, bone.getName());
            //postRenderItem(poseStack, boneItem, bone.getName(), this.currentEntity, bone);
        }
    }

    @Override
    public void renderChildBones(GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderChildBones(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @NotNull
    protected ModelPart getModelPartForBone(GeoBone bone, IllagerModel<?> armorModel) {
        String name="head";
        switch (bone.getName()){
            case "Head"->name="head";
            case "Body"->name="body";
            case "RightArm"->name="right_arm";
            case "LeftArm"->name="left_arm";
            case "arms"->name="arms";
            case "RightLeg","RightBoot"->name="right_leg";
            case "LeftLeg","LeftBoot"->name="left_leg";
        }
        return armorModel.root().getChild(name);
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

    public boolean shouldRenderItemStack(T currentEntity){
        return true;
    }

    public T getCurrentEntity() {
        return currentEntity;
    }
    public void setCurrentEntity(LivingEntity animatable){
        this.currentEntity= (T) animatable;
    }

}
