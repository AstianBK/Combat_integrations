package com.TBK.combat_integration.client.renderers.illager;

import com.TBK.combat_integration.client.models.illager.ReplacedGrieferModel;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.svr.ReplacedGriefer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamabnormals.savage_and_ravage.common.entity.monster.Griefer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
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
