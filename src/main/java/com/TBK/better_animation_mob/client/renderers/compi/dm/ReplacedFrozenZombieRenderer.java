package com.TBK.better_animation_mob.client.renderers.compi.dm;

import com.TBK.better_animation_mob.client.models.dm.ReplacedFrozenZombieModel;
import com.TBK.better_animation_mob.client.models.zombie.ReplacedHuskModel;
import com.TBK.better_animation_mob.client.renderers.zombie.ReplacedZombieRenderer;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.ReplacedZombie;
import com.infamous.dungeons_mobs.DungeonsMobs;
import com.infamous.dungeons_mobs.entities.undead.FrozenZombieEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Husk;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class ReplacedFrozenZombieRenderer<T extends FrozenZombieEntity,P extends ReplacedZombie<T>> extends ReplacedZombieRenderer<T,P> {

    public ReplacedFrozenZombieRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ResourceLocation(DungeonsMobs.MODID,"textures/entity/zombie/frozen_zombie.png"),new ReplacedFrozenZombieModel<>());
    }
}
