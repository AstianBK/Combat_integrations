package com.TBK.better_animation_mob.client.renderers;

import com.TBK.better_animation_mob.client.models.ReplacedIllagerModel;
import com.TBK.better_animation_mob.server.modbusevent.entity.ReplacedPillager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedPillagerRenderer<T extends Pillager,P extends ReplacedPillager> extends ExtendedGeoReplacedEntityRenderer<T,P>{

    public ReplacedPillagerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedIllagerModel<>(), (P) new ReplacedPillager());
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }
}
