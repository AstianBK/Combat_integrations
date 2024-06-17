package com.TBK.combat_integration.client.renderers.illager;

import com.TBK.combat_integration.client.models.illager.ReplacedEvokerModel;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedEvoker;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedEvokerRenderer<T extends Evoker,P extends ReplacedEvoker> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    public ReplacedEvokerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedEvokerModel<>(), (P) new ReplacedEvoker());
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }
}
