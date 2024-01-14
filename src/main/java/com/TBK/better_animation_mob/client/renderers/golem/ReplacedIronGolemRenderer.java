package com.TBK.better_animation_mob.client.renderers.golem;

import com.TBK.better_animation_mob.client.layers.IronGolemCrackingGeckoLayer;
import com.TBK.better_animation_mob.client.models.golem.ReplacedIronGolemModel;
import com.TBK.better_animation_mob.client.models.illager.ReplacedVindicatorModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedIronGolem;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedVindicator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Vindicator;
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
