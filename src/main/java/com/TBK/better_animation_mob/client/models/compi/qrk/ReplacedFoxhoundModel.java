package com.TBK.better_animation_mob.client.models.compi.qrk;

import com.TBK.better_animation_mob.BetterAnimationMob;
import com.TBK.better_animation_mob.client.models.ReplacedQuatrupleModel;
import com.TBK.better_animation_mob.server.modbusevent.entity.replaced_entity.qrk.ReplacedFoxhound;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import vazkii.quark.content.mobs.entity.Foxhound;

import java.util.UUID;

public class ReplacedFoxhoundModel<T extends ReplacedFoxhound<Foxhound>> extends ReplacedQuatrupleModel<T> {

    private static final ResourceLocation FOXHOUND_IDLE = new ResourceLocation("quark", "textures/model/entity/foxhound/red/idle.png");
    private static final ResourceLocation FOXHOUND_HOSTILE = new ResourceLocation("quark", "textures/model/entity/foxhound/red/hostile.png");
    private static final ResourceLocation FOXHOUND_SLEEPING = new ResourceLocation("quark", "textures/model/entity/foxhound/red/sleeping.png");
    private static final ResourceLocation SOULHOUND_IDLE = new ResourceLocation("quark", "textures/model/entity/foxhound/blue/idle.png");
    private static final ResourceLocation SOULHOUND_HOSTILE = new ResourceLocation("quark", "textures/model/entity/foxhound/blue/hostile.png");
    private static final ResourceLocation SOULHOUND_SLEEPING = new ResourceLocation("quark", "textures/model/entity/foxhound/blue/sleeping.png");
    private static final ResourceLocation BASALT_FOXHOUND_IDLE = new ResourceLocation("quark", "textures/model/entity/foxhound/black/idle.png");
    private static final ResourceLocation BASALT_FOXHOUND_HOSTILE = new ResourceLocation("quark", "textures/model/entity/foxhound/black/hostile.png");
    private static final ResourceLocation BASALT_FOXHOUND_SLEEPING = new ResourceLocation("quark", "textures/model/entity/foxhound/black/sleeping.png");

    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(BetterAnimationMob.MODID, "geo/qrk/foxhound.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        Foxhound entity =((Foxhound)getCurrentEntity().get());
        if (entity.isBlue()) {
            return entity.isSleeping() ? SOULHOUND_SLEEPING : (entity.getRemainingPersistentAngerTime() > 0 ? SOULHOUND_HOSTILE : SOULHOUND_IDLE);
        } else {
            UUID id = entity.getUUID();
            long most = id.getMostSignificantBits();
            if (most % 256L == 0L) {
                return entity.isSleeping() ? BASALT_FOXHOUND_SLEEPING : (entity.getRemainingPersistentAngerTime() > 0 ? BASALT_FOXHOUND_HOSTILE : BASALT_FOXHOUND_IDLE);
            } else {
                return entity.isSleeping() ? FOXHOUND_SLEEPING : (entity.getRemainingPersistentAngerTime() > 0 ? FOXHOUND_HOSTILE : FOXHOUND_IDLE);
            }
        }    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(BetterAnimationMob.MODID,"animations/qrk/foxhound.animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        /*GeoBone main = (GeoBone)this.getBone("main");
        GeoBone realHead = (GeoBone)this.getBone("Head");
        GeoBone tail= (GeoBone) getAnimationProcessor().getBone("tail");
        GeoBone body = (GeoBone)this.getBone("body");
        GeoBone upperBody= (GeoBone) getAnimationProcessor().getBone("mane");
        Wolf wolf = (Wolf) getCurrentEntity().get();
        float partialTick=animationEvent.getPartialTick();
        float limbSwing=animationEvent.getLimbSwing();
        float limbSwingAmount=animationEvent.getLimbSwingAmount();
        if(!wolf.isInSittingPose()){
            this.resetMain(main);
        }
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        if(!wolf.isInSittingPose()){
            if (wolf.isAngry()) {
                tail.setRotationY(0.0F);
            } else {
                tail.setRotationY(Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
            }

            realHead.addRotationZ((wolf.getHeadRollAngle(partialTick) + wolf.getBodyRollAngle(partialTick, 0.0F)));
            upperBody.addRotationZ(wolf.getBodyRollAngle(partialTick, -0.08F));
            body.addRotationZ(wolf.getBodyRollAngle(partialTick, -0.16F));
            tail.addRotationZ(wolf.getBodyRollAngle(partialTick, -0.2F));

            tail.addRotationX(-wolf.getTailAngle());
        }*/
    }
}
