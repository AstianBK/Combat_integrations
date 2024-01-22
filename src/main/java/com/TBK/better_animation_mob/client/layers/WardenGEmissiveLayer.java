package com.TBK.better_animation_mob.client.layers;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.boss.ReplacedWardenModel;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedWarden;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class WardenGEmissiveLayer<T extends Warden & IAnimatable, M extends ReplacedWardenModel> extends GeoLayerRenderer<T> {
    private final ResourceLocation texture;
    private final AlphaFunction<T> alphaFunction;
    private final DrawSelector<T, M> drawSelector;

    public WardenGEmissiveLayer(IGeoRenderer p_234885_, ResourceLocation p_234886_, AlphaFunction<T> p_234887_,DrawSelector<T, M> p_234888_) {
        super(p_234885_);
        this.texture = p_234886_;
        this.alphaFunction = p_234887_;
        this.drawSelector = p_234888_;
    }

    public void render(PoseStack p_234902_, MultiBufferSource p_234903_, int p_234904_, T p_234905_, float p_234906_, float p_234907_, float p_234908_, float p_234909_, float p_234910_, float p_234911_) {
        if (!p_234905_.isInvisible()) {
            this.onlyDrawSelectedParts();
            entityRenderer.render(getEntityModel().getModel(new ResourceLocation(BetterAnimationMob.MODID,"geo/warden.geo.json")),p_234905_,p_234909_,getRenderType(texture),p_234902_,p_234903_,p_234903_.getBuffer(getRenderType(texture)),p_234904_,LivingEntityRenderer.getOverlayCoords(p_234905_, 0.0F)
                    , 1.0F, 1.0F, 1.0F, this.alphaFunction.apply(p_234905_, p_234908_, p_234909_));
            this.resetDrawForAllParts();
        }
    }

    @Override
    public RenderType getRenderType(ResourceLocation textureLocation) {
        return RenderType.entityTranslucentEmissive(this.texture);
    }

    private void onlyDrawSelectedParts() {
        List<GeoBone> list = this.drawSelector.getPartsToDraw((M) this.getEntityModel());
        AnimatedGeoModel<?> animatedGeoModel=((AnimatedGeoModel<?>)getEntityModel());
        GeoBone main= (GeoBone) animatedGeoModel.getAnimationProcessor().getBone("main");
        main.childBones.forEach((p_234918_) -> {
            p_234918_.dontRender = true;
        });
        if(list!=null){
            list.forEach((p_234916_) -> {
                p_234916_.dontRender = false;
            });
        }
    }

    private void resetDrawForAllParts() {
        AnimatedGeoModel<?> animatedGeoModel=((AnimatedGeoModel<?>)getEntityModel());
        GeoBone main= (GeoBone) animatedGeoModel.getAnimationProcessor().getBone("main");
        main.childBones.forEach((p_234918_) -> {
            p_234918_.dontRender = true;
        });
    }

    @OnlyIn(Dist.CLIENT)
    public interface AlphaFunction<T extends Warden> {
        float apply(T p_234920_, float p_234921_, float p_234922_);
    }

    @OnlyIn(Dist.CLIENT)
    public interface DrawSelector<T extends Warden, M extends ReplacedWardenModel> {
        List<GeoBone> getPartsToDraw(ReplacedWardenModel p_234924_);
    }
}
