package com.TBK.combat_integration.client.layers;

import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.google.common.collect.Maps;
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
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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
import java.util.Map;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class ArmorGeckoLayer<T extends LivingEntity & IAnimatable> extends GeoLayerRenderer<T> {
    protected static final Map<String, ResourceLocation> ARMOR_PATH_CACHE = Maps.newHashMap();
    protected static final HumanoidModel<LivingEntity> INNER_ARMOR_MODEL = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
    protected static final HumanoidModel<LivingEntity> OUTER_ARMOR_MODEL = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));

    @Nullable protected ItemStack mainHandStack;
    @Nullable protected ItemStack offhandStack;
    @Nullable protected ItemStack helmetStack;
    @Nullable protected ItemStack chestplateStack;
    @Nullable protected ItemStack leggingsStack;
    @Nullable protected ItemStack bootsStack;
    AnimatedGeoModel<T> model;
    private final ResourceLocation textureBase;
    private final ResourceLocation modelLocation;

    public ArmorGeckoLayer(IGeoRenderer<T> geoRenderer, AnimatedGeoModel<T> model,ResourceLocation textureLocation,ResourceLocation modelLocation) {
        super(geoRenderer);
        this.model=model;
        this.textureBase= textureLocation;
        this.modelLocation=modelLocation;
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
        preRender(poseStack,animatable,modelProvider,renderType,bufferSource,glintBuffer,partialTick,packedLight,OverlayTexture.NO_OVERLAY);
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

    /**
     * Return an EquipmentSlot for a given {@link ItemStack} and animatable instance.<br>
     * This is what determines the base model to use for rendering a particular stack
     */
    @Nonnull
    protected EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, T animatable) {
        for(EquipmentSlot slot : EquipmentSlot.values()) {
            if(slot.getType() == EquipmentSlot.Type.ARMOR) {
                if(stack == animatable.getItemBySlot(slot))
                    return slot;
            }
        }

        return EquipmentSlot.CHEST;
    }

    /**
     * Return a ModelPart for a given {@link GeoBone}.<br>
     * This is then transformed into position for the final render
     */
    @Nonnull
    protected ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack, T animatable, HumanoidModel<?> baseModel) {
        return baseModel.body;
    }

    /**
     * Get the {@link ItemStack} relevant to the bone being rendered.<br>
     * Return null if this bone should be ignored
     */
    @Nullable
    protected ItemStack getArmorItemForBone(GeoBone bone, T animatable) {
        return null;
    }

    public void preRender(PoseStack poseStack, T animatable, GeoModelProvider<T> bakedModel, RenderType renderType, MultiBufferSource bufferSource,
                          VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        this.mainHandStack = animatable.getItemBySlot(EquipmentSlot.MAINHAND);
        this.offhandStack = animatable.getItemBySlot(EquipmentSlot.OFFHAND);
        this.helmetStack = animatable.getItemBySlot(EquipmentSlot.HEAD);
        this.chestplateStack = animatable.getItemBySlot(EquipmentSlot.CHEST);
        this.leggingsStack = animatable.getItemBySlot(EquipmentSlot.LEGS);
        this.bootsStack = animatable.getItemBySlot(EquipmentSlot.FEET);
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
        ItemStack armorStack = getArmorItemForBone(bone, animatable);
        if (armorStack == null) {
            return;
        }

        if (armorStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractSkullBlock skullBlock) {
            renderSkullAsArmor(poseStack, bone, armorStack, skullBlock, bufferSource, packedLight);
        }
        else {
            EquipmentSlot slot = getEquipmentSlotForBone(bone, armorStack, animatable);
            HumanoidModel<?> model = getModelForItem(bone, slot, armorStack, animatable);
            ModelPart modelPart = getModelPartForBone(bone, slot, armorStack, animatable, model);
            if(slot==null || modelPart==null)return;
            if (!modelPart.cubes.isEmpty()) {
                poseStack.pushPose();
                poseStack.scale(-1, -1, 1);
                if (model instanceof GeoArmorRenderer<?>) {
                    GeoArmorRenderer<?> geoArmorRenderer = (GeoArmorRenderer<?>) model;
                    prepModelPartForRender(poseStack, bone, modelPart);
                    //geoArmorRenderer.prepForRender(animatable, armorStack, slot, model);
                    geoArmorRenderer.setAllVisible(false);
                    //geoArmorRenderer.applyBoneVisibilityByPart(slot, modelPart, model);
                    geoArmorRenderer.renderToBuffer(poseStack, null, packedLight, packedOverlay, 1, 1, 1, 1);
                }
                else if (armorStack.getItem() instanceof ArmorItem) {
                    prepModelPartForRender(poseStack,bone,modelPart);
                    renderVanillaArmorPiece(poseStack, animatable, bone, slot, armorStack, modelPart, bufferSource, partialTick, packedLight, packedOverlay);
                }

                poseStack.popPose();
            }
        }
    }

    /**
     * Renders an individual armor piece base on the given {@link GeoBone} and {@link ItemStack}
     */
    protected <I extends Item & IGeoRenderer> void renderVanillaArmorPiece(PoseStack poseStack, T animatable, GeoBone bone, EquipmentSlot slot, ItemStack armorStack,
                                                                           ModelPart modelPart, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
        bufferSource=getRenderer().getCurrentRTB();
        ResourceLocation texture = getVanillaArmorResource(animatable, armorStack, slot, "");
        VertexConsumer buffer = getArmorBuffer(bufferSource, null, texture, armorStack.hasFoil());

        if (armorStack.getItem() instanceof DyeableArmorItem dyable) {
            int color = dyable.getColor(armorStack);

            modelPart.render(poseStack, buffer, packedLight, packedOverlay, (color >> 16 & 255) / 255f, (color >> 8 & 255) / 255f, (color & 255) / 255f, 1);

            texture = getVanillaArmorResource(animatable, armorStack, slot, "overlay");
            buffer = getArmorBuffer(bufferSource, null, texture, false);
        }
        modelPart.render(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    /**
     * Returns the standard VertexConsumer for armor rendering from the given buffer source.
     * @param bufferSource The BufferSource to draw the buffer from
     * @param renderType The RenderType to use for rendering, or null to use the default
     * @param texturePath The texture path for the render. May be null if renderType is not null
     * @param enchanted Whether the render should have an enchanted glint or not
     * @return The buffer to draw to
     */
    protected VertexConsumer getArmorBuffer(MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable ResourceLocation texturePath, boolean enchanted) {
        if (renderType == null)
            renderType = RenderType.armorCutoutNoCull(texturePath);

        return ItemRenderer.getArmorFoilBuffer(bufferSource, renderType, false, enchanted);
    }

    /**
     * Returns a cached instance of a base HumanoidModel that is used for rendering/modelling the provided {@link ItemStack}
     */
    @Nonnull
    protected HumanoidModel<?> getModelForItem(GeoBone bone, EquipmentSlot slot, ItemStack stack, T animatable) {
        HumanoidModel<?> defaultModel = slot == EquipmentSlot.LEGS ? INNER_ARMOR_MODEL : OUTER_ARMOR_MODEL;

        return IClientItemExtensions.of(stack).getHumanoidArmorModel(animatable, stack, slot, defaultModel);
    }

    public ResourceLocation getVanillaArmorResource(Entity entity, ItemStack stack, EquipmentSlot slot, String type) {
        String domain = "minecraft";
        String path = ((ArmorItem) stack.getItem()).getMaterial().getName();
        String[] materialNameSplit = path.split(":", 2);

        if (materialNameSplit.length > 1) {
            domain = materialNameSplit[0];
            path = materialNameSplit[1];
        }

        if (!type.isBlank())
            type = "_" + type;

        String texture = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, path, (slot == EquipmentSlot.LEGS ? 2 : 1), type);
        texture = ForgeHooksClient.getArmorTexture(entity, stack, texture, slot, type);

        return ARMOR_PATH_CACHE.computeIfAbsent(texture, ResourceLocation::new);
    }

    /**
     * Render a given {@link AbstractSkullBlock} as a worn armor piece in relation to a given {@link GeoBone}
     */
    protected void renderSkullAsArmor(PoseStack poseStack, GeoBone bone, ItemStack stack, AbstractSkullBlock skullBlock, MultiBufferSource bufferSource, int packedLight) {
        GameProfile skullProfile = null;
        CompoundTag stackTag = stack.getTag();

        if (stackTag != null) {
            Tag skullTag = stackTag.get(PlayerHeadItem.TAG_SKULL_OWNER);

            if (skullTag instanceof CompoundTag compoundTag) {
                skullProfile = NbtUtils.readGameProfile(compoundTag);
            }
            else if (skullTag instanceof StringTag tag) {
                String skullOwner = tag.getAsString();

                if (!skullOwner.isBlank()) {
                    CompoundTag profileTag = new CompoundTag();

                    SkullBlockEntity.updateGameprofile(new GameProfile(null, skullOwner), name ->
                            stackTag.put(PlayerHeadItem.TAG_SKULL_OWNER, NbtUtils.writeGameProfile(profileTag, name)));

                    skullProfile = NbtUtils.readGameProfile(profileTag);
                }
            }
        }

        SkullBlock.Type type = skullBlock.getType();
        SkullModelBase model = SkullBlockRenderer.createSkullRenderers(Minecraft.getInstance().getEntityModels()).get(type);
        RenderType renderType = SkullBlockRenderer.getRenderType(type, skullProfile);

        poseStack.pushPose();
        RenderUtils.translateAndRotateMatrixForBone(poseStack, bone);
        poseStack.scale(1.1875f, 1.1875f, 1.1875f);
        poseStack.translate(-0.5f, 0, -0.5f);
        SkullBlockRenderer.renderSkull(null, 0, 0, poseStack, bufferSource, packedLight, model, renderType);
        poseStack.popPose();
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
}

