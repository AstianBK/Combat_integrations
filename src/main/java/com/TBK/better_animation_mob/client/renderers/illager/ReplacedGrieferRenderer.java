package com.TBK.better_animation_mob.client.renderers.illager;

import com.TBK.better_animation_mob.client.models.illager.ReplacedGrieferModel;
import com.TBK.better_animation_mob.client.models.illager.ReplacedPillagerModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedPillager;
import com.TBK.better_animation_mob.server.modbusevent.entity.svr.ReplacedGriefer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Griefer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedGrieferRenderer<T extends Griefer,P extends ReplacedGriefer> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedGrieferRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedGrieferModel<>(), (P) new ReplacedGriefer());
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }
}
