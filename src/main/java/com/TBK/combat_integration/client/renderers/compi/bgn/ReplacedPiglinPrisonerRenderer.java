package com.TBK.combat_integration.client.renderers.compi.bgn;

import com.TBK.combat_integration.client.models.compi.bgn.ReplacedPiglinPrisonerModel;
import com.TBK.combat_integration.client.renderers.piglin.ReplacedPiglinRenderer;
import com.TBK.combat_integration.server.modbusevent.entity.replaced_entity.ReplacedPiglin;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.PiglinPrisoner;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedPiglinPrisonerRenderer<T extends PiglinPrisoner,P extends ReplacedPiglin<T>> extends ReplacedPiglinRenderer<T,P> {
    public ReplacedPiglinPrisonerRenderer(EntityRendererProvider.Context renderManager){
        super(renderManager,new ReplacedPiglinPrisonerModel<>(),(P) new ReplacedPiglin(),new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/piglin/piglin_prisoner.png"));
    }

    @Override
    protected void preRenderItem(PoseStack stack, ItemStack item, String name, T currentEntity, GeoBone bone, float currentPartialTicks) {
        if (item == currentEntity.getMainHandItem() || item == currentEntity.getOffhandItem()) {
            stack.mulPose(Vector3f.XP.rotationDegrees(-90.F));
            if (item == currentEntity.getMainHandItem()) {
                stack.translate(0.0F,0.2D,0.0D);
            }
        }
    }

}
