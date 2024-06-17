package com.TBK.combat_integration.client.models.compi.bgn;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.models.ReplacedHumanoidModel;
import com.google.common.collect.Maps;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.WarpedEnderMan;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ReplacedWarpedEnderManModel<T extends IAnimatable> extends ReplacedHumanoidModel<T> {
    private static final Map<WarpedEnderMan.WarpedEnderManVariant, ResourceLocation> WARPED_ENDERMAN_LOCATION_MAP= Util.make(Maps.newEnumMap(WarpedEnderMan.WarpedEnderManVariant.class), (p_114874_) -> {
        p_114874_.put(WarpedEnderMan.WarpedEnderManVariant.FRESH, new ResourceLocation(BygoneNetherMod.MODID, "textures/entity/warped_enderman/warped_enderman_fresh.png"));
        p_114874_.put(WarpedEnderMan.WarpedEnderManVariant.LONG_VINE, new ResourceLocation(BygoneNetherMod.MODID, "textures/entity/warped_enderman/warped_enderman_long_vine.png"));
        p_114874_.put(WarpedEnderMan.WarpedEnderManVariant.SHORT_VINE, new ResourceLocation(BygoneNetherMod.MODID, "textures/entity/warped_enderman/warped_enderman_short_vine.png"));
    });;

    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(CombatIntegration.MODID,"geo/bgn/warped_enderman.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        if(this.getCurrentEntity().get()!=null){
            WarpedEnderMan warpedEnderMan = (WarpedEnderMan) this.getCurrentEntity().get();
            return WARPED_ENDERMAN_LOCATION_MAP.get(warpedEnderMan.getVariant());
        }
        return new ResourceLocation(BygoneNetherMod.MODID, "textures/entity/warped_enderman/warped_enderman_fresh.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(CombatIntegration.MODID,"animations/enderman.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
    }
}
