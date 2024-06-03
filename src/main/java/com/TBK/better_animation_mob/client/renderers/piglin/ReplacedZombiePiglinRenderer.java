package com.TBK.better_animation_mob.client.renderers.piglin;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.layers.ArmorGeckoLayer;
import com.TBK.better_animation_mob.client.models.piglin.ReplacedPiglinBruteModel;
import com.TBK.better_animation_mob.client.models.piglin.ReplacedZombiePiglinModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedZombieRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedPiglin;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedZombiePiglin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedZombiePiglinRenderer<T extends ZombifiedPiglin,P extends ReplacedZombiePiglin<T>> extends ExtendedGeoReplacedEntityRenderer<T,P> {
    public ReplacedZombiePiglinRenderer(EntityRendererProvider.Context renderManager){
        super(renderManager,new ReplacedZombiePiglinModel<>(),(P)new ReplacedZombiePiglin());
        this.addLayer(new ArmorGeckoLayer<>(this,getGeoModelProvider(),new ResourceLocation("textures/entity/piglin/zombified_piglin.png"),new ResourceLocation(BetterAnimationMob.MODID,"geo/piglin.geo.json")){
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
            stack.mulPose(Vector3f.XP.rotationDegrees(-90F));
            if (item == currentEntity.getMainHandItem()) {
                stack.translate(0.05F,0.15D,0.0D);
            }
        }
    }

}
