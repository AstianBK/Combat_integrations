package com.TBK.better_animation_mob.client.renderers.compi.myf;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.layers.EnergySwirlGeckoLayer;
import com.TBK.better_animation_mob.client.layers.RosalyneGlowLayer;
import com.TBK.better_animation_mob.client.layers.SpiderEyeGeckoLayer;
import com.TBK.better_animation_mob.client.models.compi.myf.ReplacedFortunaModel;
import com.TBK.better_animation_mob.client.models.compi.myf.ReplacedRosalyneModel;
import com.TBK.better_animation_mob.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.myf.ReplacedFortuna;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.myf.ReplacedRosalyne;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.DameFortunaEntity;
import lykrast.meetyourfight.entity.RosalyneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.geo.render.built.GeoBone;

@OnlyIn(Dist.CLIENT)
public class ReplacedRosalyneRenderer<T extends RosalyneEntity,P extends ReplacedRosalyne> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    private static final ResourceLocation TEXTURE = MeetYourFight.rl("textures/entity/rosalyne_armor.png");

    public ReplacedRosalyneRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ReplacedRosalyneModel<>(), (P) new ReplacedRosalyne<>());
        this.addLayer(new RosalyneGlowLayer<>(this));
        this.addLayer(new EnergySwirlGeckoLayer<>(this,TEXTURE,new ResourceLocation(BetterAnimationMob.MODID,"geo/myf/rosalyne.geo.json")));
        this.shadowRadius=0.5F;
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
    }
}
