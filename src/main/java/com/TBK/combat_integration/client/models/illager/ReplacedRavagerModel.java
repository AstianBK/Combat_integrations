package com.TBK.combat_integration.client.models.illager;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.client.models.ReplacedQuatrupleModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Ravager;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class ReplacedRavagerModel <T extends IAnimatable> extends ReplacedQuatrupleModel<T> {
    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(CombatIntegration.MODID, "geo/ravager.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/entity/illager/ravager.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(CombatIntegration.MODID,"animations/ravager.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        IBone neck = this.getAnimationProcessor().getBone("neck");
        IBone mouth = this.getBone("mouth");
        int i = ((Ravager)this.getCurrentEntity().get()).getStunnedTick();
        int j = ((Ravager)this.getCurrentEntity().get()).getRoarTick();
        float pPartialTick = animationEvent.getPartialTick();
        float f6 = -1.0F * Mth.sin(neck.getRotationX());
        boolean flag = i > 0;
        neck.setPositionY( flag ? f6 -3.0F : f6 );
        neck.setRotationX(flag ? -0.21991149F : 0.0F);
        mouth.setRotationX((float)Math.PI * (flag ? -0.05F : -0.01F));
        if (flag) {
            double d0 = (double)i / 40.0D;
            neck.setPositionX((float)Math.sin(d0 * 10.0D) * 3.0F);
        } else if (j > 0) {
            float f7 = Mth.sin(((float)(20 - j) - pPartialTick) / 20.0F * (float)Math.PI * 0.25F);
            mouth.setRotationX(((float)Math.PI / 2F) * -f7);
        }
    }
}
