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

public class ReplacedStrayRenderer<T extends AbstractSkeleton,P extends ReplacedSkeleton> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedStrayRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedSkeletonModel<>(), (P) new ReplacedSkeleton());
        this.addLayer(new StrayGeckoLayer<>(this,this.getGeoModelProvider(),new ResourceLocation("textures/entity/skeleton/skeleton.png"),new ResourceLocation(BetterAnimationMob.MODID,"geo/skeleton.geo.json")));
    }
}
