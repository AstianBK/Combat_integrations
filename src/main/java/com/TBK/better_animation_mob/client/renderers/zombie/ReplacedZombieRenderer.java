package com.TBK.better_animation_mob.client.renderers.zombie;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.layers.ArmorGeckoLayer;
import com.TBK.better_animation_mob.client.models.zombie.ReplacedZombieModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedZombie;
import com.google.common.collect.ImmutableList;
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
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;

@OnlyIn(Dist.CLIENT)
public class ReplacedZombieRenderer<T extends Zombie ,P extends ReplacedZombie> extends ExtendedGeoReplacedEntityRenderer<T,P> {
    public ReplacedZombieRenderer(EntityRendererProvider.Context renderManager){
        this(renderManager,new ResourceLocation("textures/entity/zombie/zombie.png"),new ReplacedZombieModel());
    }

    public ReplacedZombieRenderer(EntityRendererProvider.Context renderManager,ResourceLocation texture,ReplacedZombieModel model) {
        super(renderManager, model, (P) new ReplacedZombie());
        this.addLayer(new ArmorGeckoLayer<>(this,getGeoModelProvider(),texture,new ResourceLocation(BetterAnimationMob.MODID,"geo/zombie.geo.json")){
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
        this.shadowRadius=0.5F;
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            boolean trident = item.getItem() instanceof TridentItem;
            if (item == currentEntity.getMainHandItem()) {
                if (trident) {
                    stack.mulPose(Vector3f.XP.rotationDegrees(90F));
                    stack.translate(0.0D,0.0D,-0.8D);
                }else {
                    stack.translate(0.05,-0.25D,-0.5D);
                }
            }
        }
    }
}
