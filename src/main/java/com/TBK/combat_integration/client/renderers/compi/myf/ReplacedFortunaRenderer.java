package com.TBK.combat_integration.client.renderers.compi.myf;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.layers.SpiderEyeGeckoLayer;
import com.TBK.combat_integration.client.models.compi.myf.ReplacedFortunaModel;
import com.TBK.combat_integration.client.renderers.ExtendedGeoReplacedEntityRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.myf.ReplacedFortuna;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import lykrast.meetyourfight.MeetYourFight;
import lykrast.meetyourfight.entity.DameFortunaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.geo.render.built.GeoBone;

@OnlyIn(Dist.CLIENT)
public class ReplacedFortunaRenderer<T extends DameFortunaEntity,P extends ReplacedFortuna> extends ExtendedGeoReplacedEntityRenderer<T,P> {
    private static final ResourceLocation GLOW = MeetYourFight.rl("textures/entity/dame_fortuna_glow.png");

    public ReplacedFortunaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ReplacedFortunaModel<>(), (P) new ReplacedFortuna<>());
        this.addLayer(new SpiderEyeGeckoLayer<>(this,GLOW,new ResourceLocation(CombatIntegration.MODID,"geo/myf/fortuna.geo.json")));
        this.shadowRadius=0.5F;
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            boolean trident = item.getItem() instanceof TridentItem;
            if (item == currentEntity.getMainHandItem()) {
                stack.mulPose(Vector3f.XP.rotationDegrees(-90F));
                if (trident) {
                    stack.translate(0.05D,0.0D,0.0D);
                }else {
                    stack.translate(0.05,0.2D,0.0D);
                }
            }
        }
    }
}
