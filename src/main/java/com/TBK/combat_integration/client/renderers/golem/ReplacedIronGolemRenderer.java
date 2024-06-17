package com.TBK.combat_integration.client.renderers.golem;

import com.TBK.combat_integration.client.layers.IronGolemCrackingGeckoLayer;
import com.TBK.combat_integration.client.models.golem.ReplacedIronGolemModel;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedIronGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedIronGolemRenderer<T extends IronGolem,P extends ReplacedIronGolem<T>> extends ExtendedGeoReplacedEntityRenderer<T,P> {
    public ReplacedIronGolemRenderer(EntityRendererProvider.Context renderManager){
        this(renderManager,new ReplacedIronGolemModel<>(),(P) new ReplacedIronGolem<T>());
    }
    public ReplacedIronGolemRenderer(EntityRendererProvider.Context renderManager, ReplacedIronGolemModel<IAnimatable> model, P replaced) {
        super(renderManager, model, replaced);
        this.addLayer(new IronGolemCrackingGeckoLayer<>(this));
    }

    @Override
    public boolean shouldRenderItemStack(T currentEntity) {
        return false;
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {

    }

}
