package com.TBK.better_animation_mob.client.renderers.compi.qrk;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.layers.SpiderEyeGeckoLayer;
import com.TBK.better_animation_mob.client.layers.StrayGeckoLayer;
import com.TBK.better_animation_mob.client.models.qrk.ReplacedForgottenModel;
import com.TBK.better_animation_mob.client.models.skeleton.ReplacedSkeletonModel;
import com.TBK.better_animation_mob.client.renderers.skeleton.ReplacedSkeletonRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedSkeleton;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.qrk.ReplacedForgotten;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import vazkii.quark.base.Quark;
import vazkii.quark.content.mobs.entity.Forgotten;

public class ReplacedForgottenRenderer<T extends Forgotten,P extends ReplacedForgotten> extends ReplacedSkeletonRenderer<T,P> {
    public ReplacedForgottenRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedForgottenModel<>(), (P) new ReplacedForgotten(),new ResourceLocation(Quark.MOD_ID,"model/entity/forgotten/main.png"));
        this.addLayer(new SpiderEyeGeckoLayer<>(this,new ResourceLocation(Quark.MOD_ID,"model/entity/forgotten/eye.png")));
        this.addLayer(new StrayGeckoLayer<>(this,this.getGeoModelProvider(),new ResourceLocation(Quark.MOD_ID,"model/entity/forgotten/main.png"),new ResourceLocation(BetterAnimationMob.MODID,"geo/forgotten.geo.json"),new ResourceLocation(Quark.MOD_ID,"model/entity/forgotten/overlay.png")));
    }
}
