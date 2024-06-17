package com.TBK.combat_integration.client.models.piglin;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;

public class ReplacedPiglinBruteModel<T extends IAnimatable> extends ReplacedPiglinModel<T> {
    @Override
    public ResourceLocation getTextureResource(T object) {
        return new ResourceLocation("textures/entity/piglin/piglin_brute.png");
    }

}
