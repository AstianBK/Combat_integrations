package com.TBK.better_animation_mob.client.renderers.boss;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.layers.ArmorGeckoLayer;
import com.TBK.better_animation_mob.client.layers.WardenGEmissiveLayer;
import com.TBK.better_animation_mob.client.models.boss.ReplacedWardenModel;
import com.TBK.better_animation_mob.client.models.zombie.ReplacedZombieModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedWarden;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedZombie;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

@OnlyIn(Dist.CLIENT)
public class ReplacedWardenRenderer<T extends Warden,P extends ReplacedWarden<T>> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/warden/warden.png");
    private static final ResourceLocation BIOLUMINESCENT_LAYER_TEXTURE = new ResourceLocation("textures/entity/warden/warden_bioluminescent_layer.png");
    private static final ResourceLocation HEART_TEXTURE = new ResourceLocation("textures/entity/warden/warden_heart.png");
    private static final ResourceLocation PULSATING_SPOTS_TEXTURE_1 = new ResourceLocation("textures/entity/warden/warden_pulsating_spots_1.png");
    private static final ResourceLocation PULSATING_SPOTS_TEXTURE_2 = new ResourceLocation("textures/entity/warden/warden_pulsating_spots_2.png");

    public ReplacedWardenRenderer(EntityRendererProvider.Context renderManager){
        this(renderManager,new ResourceLocation("textures/entity/warden/warden.png"),new ReplacedWardenModel());
    }

    public ReplacedWardenRenderer(EntityRendererProvider.Context renderManager, ResourceLocation texture, ReplacedWardenModel model) {
        super(renderManager, model, (P) new ReplacedWarden());
        this.addLayer(new WardenGEmissiveLayer<>(this, BIOLUMINESCENT_LAYER_TEXTURE, (p_234809_, p_234810_, p_234811_) -> {
            return 1.0F;
        }, ReplacedWardenModel::getBioluminescentLayerModelParts));
        this.addLayer(new WardenGEmissiveLayer<>(this, PULSATING_SPOTS_TEXTURE_1, (p_234805_, p_234806_, p_234807_) -> {
            return Math.max(0.0F, Mth.cos(p_234807_ * 0.045F) * 0.25F);
        }, ReplacedWardenModel::getPulsatingSpotsLayerModelParts));
        this.addLayer(new WardenGEmissiveLayer<>(this, PULSATING_SPOTS_TEXTURE_2, (p_234801_, p_234802_, p_234803_) -> {
            return Math.max(0.0F, Mth.cos(p_234803_ * 0.045F + (float)Math.PI) * 0.25F);
        }, ReplacedWardenModel::getPulsatingSpotsLayerModelParts));
        this.addLayer(new WardenGEmissiveLayer<>(this, TEXTURE, (p_234797_, p_234798_, p_234799_) -> {
            return p_234797_.getTendrilAnimation(p_234798_);
        }, ReplacedWardenModel::getTendrilsLayerModelParts));
        this.addLayer(new WardenGEmissiveLayer<>(this, HEART_TEXTURE, (p_234793_, p_234794_, p_234795_) -> {
            return p_234793_.getHeartAnimation(p_234794_);
        }, ReplacedWardenModel::getHeartLayerModelParts));
        this.shadowRadius=0.5F;
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
    }

    @Override
    public RenderType getRenderType(Object o, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}
