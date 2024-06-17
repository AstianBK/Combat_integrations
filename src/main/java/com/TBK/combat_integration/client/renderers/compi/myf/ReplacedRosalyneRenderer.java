package com.TBK.combat_integration.client.renderers.compi.myf;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.layers.EnergySwirlGeckoLayer;
import com.TBK.combat_integration.client.layers.RosalyneGlowLayer;
import com.TBK.combat_integration.client.models.compi.myf.ReplacedRosalyneModel;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.myf.ReplacedRosalyne;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.RosalyneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.geo.render.built.GeoBone;

@OnlyIn(Dist.CLIENT)
public class ReplacedRosalyneRenderer<T extends RosalyneEntity,P extends ReplacedRosalyne> extends ExtendedGeoReplacedEntityRenderer<T,P> {

    private static final ResourceLocation TEXTURE = MeetYourFight.rl("textures/entity/rosalyne_armor.png");

    public ReplacedRosalyneRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ReplacedRosalyneModel<>(), (P) new ReplacedRosalyne<>());
        this.addLayer(new RosalyneGlowLayer<>(this));
        this.addLayer(new EnergySwirlGeckoLayer<>(this,TEXTURE,new ResourceLocation(CombatIntegration.MODID,"geo/myf/rosalyne.geo.json")));
        this.shadowRadius=0.5F;
    }

    @Override
    public void renderChildBones(GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderChildBones(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
    }
}
