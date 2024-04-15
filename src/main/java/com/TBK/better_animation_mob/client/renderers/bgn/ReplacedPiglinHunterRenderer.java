package com.TBK.better_animation_mob.client.renderers.bgn;

import com.TBK.better_animation_mob.client.models.bgn.ReplacedPiglinHunterModel;
import com.TBK.better_animation_mob.client.models.bgn.ReplacedPiglinPrisonerModel;
import com.TBK.better_animation_mob.client.renderers.piglin.ReplacedPiglinRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedPiglin;
import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.PiglinHunter;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ReplacedPiglinHunterRenderer<T extends PiglinHunter,P extends ReplacedPiglin<T>> extends ReplacedPiglinRenderer<T,P> {
    public ReplacedPiglinHunterRenderer(EntityRendererProvider.Context renderManager){
        super(renderManager,new ReplacedPiglinHunterModel<>(),(P) new ReplacedPiglin(),new ResourceLocation(BygoneNetherMod.MODID,"textures/entity/piglin/piglin_hunter.png"));
    }


}
