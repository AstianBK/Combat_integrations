package com.TBK.better_animation_mob.client.renderers.skeleton;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.layers.ArmorGeckoLayer;
import com.TBK.better_animation_mob.client.layers.StrayGeckoLayer;
import com.TBK.better_animation_mob.client.models.skeleton.ReplacedSkeletonModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedSkeleton;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedSkeletonRenderer<T extends AbstractSkeleton,P extends ReplacedSkeleton> extends ExtendedGeoReplacedEntityRenderer<T,P> {
    public ReplacedSkeletonRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedSkeletonModel<>(), (P) new ReplacedSkeleton());
        this.addLayer(new StrayGeckoLayer<>(this));
        this.addLayer(new ArmorGeckoLayer<>(this,this.getGeoModelProvider(),new ResourceLocation("textures/entity/skeleton/skeleton.png"),new ResourceLocation(BetterAnimationMob.MODID,"geo/skeleton.geo.json")){
            @NotNull
            @Override
            protected ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack, LivingEntity animatable, HumanoidModel armorModel) {
                ModelPart part=null;
                switch (bone.getName()){
                    case "Head"->part=armorModel.head;
                    case "Body"->part=armorModel.body;
                    case "RightArm"->part=armorModel.rightArm;
                    case "LeftArm"->part=armorModel.leftArm;
                    case "RightLeg"->part=armorModel.rightLeg;
                    case "LeftLeg"->part=armorModel.leftLeg;
                }
                return part;
            }

            @org.jetbrains.annotations.Nullable
            @Override
            protected ItemStack getArmorItemForBone(GeoBone bone, LivingEntity animatable) {
                ItemStack stack=null;
                switch (bone.getName()){
                    case "Head"->stack=animatable.getItemBySlot(EquipmentSlot.HEAD);
                    case "Body","RightArm","LeftArm"->stack=animatable.getItemBySlot(EquipmentSlot.CHEST);
                    case "LeftLeg", "RightLeg" ->stack=animatable.getItemBySlot(EquipmentSlot.LEGS);
                }
                return stack;
            }

            @NotNull
            @Override
            protected EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, LivingEntity animatable) {
                EquipmentSlot slot=null;
                switch (bone.getName()){
                    case "Head"->slot=EquipmentSlot.HEAD;
                    case "Body","LeftArm","RightArm"-> slot=EquipmentSlot.CHEST;
                    case "LeftLeg", "RightLeg" ->slot=EquipmentSlot.LEGS;
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
                    stack.mulPose(Vector3f.XP.rotationDegrees(180F));
                    stack.translate(0.0D,-0.05D,0.0D);
                }else {
                    stack.translate(0.05,-0.25D,-0.5D);
                }
            }
        }
    }

}
