package com.TBK.better_animation_mob.client.renderers.skeleton;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.layers.ArmorGeckoLayer;
import com.TBK.better_animation_mob.client.models.skeleton.ReplacedSkeletonModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedSkeletonRenderer<T extends AbstractSkeleton,P extends ReplacedSkeleton> extends ExtendedGeoReplacedEntityRenderer<T,P> {
    public ReplacedSkeletonRenderer(EntityRendererProvider.Context renderManager){
        this(renderManager,new ReplacedSkeletonModel(),(P) new ReplacedSkeleton(),new ResourceLocation("textures/entity/skeleton/skeleton.png"));
    }
    public ReplacedSkeletonRenderer(EntityRendererProvider.Context renderManager, ReplacedSkeletonModel model,P skeleton,ResourceLocation texture) {
        super(renderManager, model,skeleton);
        this.addLayer(new ArmorGeckoLayer<>(this,this.getGeoModelProvider(),texture,new ResourceLocation(BetterAnimationMob.MODID,"geo/skeleton.geo.json")){
            @NotNull
            @Override
            protected ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack, LivingEntity animatable, HumanoidModel armorModel) {
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

            @org.jetbrains.annotations.Nullable
            @Override
            protected ItemStack getArmorItemForBone(GeoBone bone, LivingEntity animatable) {
                ItemStack stack=null;
                switch (bone.getName()){
                    case "HatLayer"->stack=animatable.getItemBySlot(EquipmentSlot.HEAD);
                    case "BodyLayer","RightArmLayer","LeftArmLayer"->stack=animatable.getItemBySlot(EquipmentSlot.CHEST);
                    case "LeftLegLayer", "RightLegLayer" ->stack=animatable.getItemBySlot(EquipmentSlot.LEGS);
                    case "LeftBootLayer","RightBootLayer" ->stack=animatable.getItemBySlot(EquipmentSlot.FEET);
                }
                return stack;
            }

            @NotNull
            @Override
            protected EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, LivingEntity animatable) {
                EquipmentSlot slot=null;
                switch (bone.getName()){
                    case "HatLayer"->slot=EquipmentSlot.HEAD;
                    case "BodyLayer","LeftArmLayer","RightArmLayer"-> slot=EquipmentSlot.CHEST;
                    case "LeftLegLayer", "RightLegLayer" ->slot=EquipmentSlot.LEGS;
                    case "LeftBootLayer","RightBootLayer" ->slot=EquipmentSlot.FEET;
                }
                return slot;
            }
        });
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            boolean trident = item.getItem() instanceof BowItem;
            if (item == currentEntity.getMainHandItem()) {
                if (trident) {
                    stack.mulPose(Vector3f.XP.rotationDegrees(-90F));
                    stack.translate(0.15D,-0.0D,0.05D);
                }else {
                    stack.translate(0.05,-0.25D,-0.5D);
                }
            }
            if(item.getItem() instanceof ShieldItem){
                stack.mulPose(Vector3f.XP.rotationDegrees(-90F));
                stack.translate(0.05,-0.25D,-0.5D);
            }
        }
    }

    @Override
    public RenderType getRenderType(Object o, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}
