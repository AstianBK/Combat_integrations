package com.TBK.better_animation_mob.client.renderers;

import com.TBK.better_animation_mob.client.models.ReplacedEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;
import software.bernie.geckolib3.util.EModelRenderCycle;
import software.bernie.geckolib3.util.RenderUtils;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public abstract class ExtendedGeoReplacedEntityRenderer<T extends LivingEntity,P extends IAnimatable> extends GeoReplacedEntityRenderer<P> {
    private float currentPartialTicks;

    protected T currentEntity;

    public ExtendedGeoReplacedEntityRenderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<IAnimatable> model , P animatable) {
        super(renderManager, model, animatable);
        ((ReplacedEntityModel<?>) getGeoModelProvider()).setCurrentEntity(this::getCurrentEntity);
    }

    protected abstract void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone,float currentPartialTicks);

    protected void renderItemStack(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ItemStack stack,
                                   String boneName) {
        Minecraft.getInstance().getItemRenderer().renderStatic(this.currentEntity, stack,
                ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, false, poseStack, bufferSource, null, packedLight,
                LivingEntityRenderer.getOverlayCoords(this.currentEntity, 0.0F),
                currentEntity.getId());
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
                if(bone.getName().equals("ItemSlotRight") || bone.getName().equals("leftItem")){
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


    public boolean shouldRenderItemStack(T currentEntity){
        return true;
    }

    public T getCurrentEntity() {
        return currentEntity;
    }

    @NotNull
    @Override
    public Vec3 getRenderOffset(Entity pEntity, float pPartialTicks) {
        this.currentEntity = (T) pEntity;
        return super.getRenderOffset(pEntity, pPartialTicks);
    }
}
