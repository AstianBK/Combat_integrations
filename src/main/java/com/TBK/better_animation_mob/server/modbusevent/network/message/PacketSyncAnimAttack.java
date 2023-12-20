package com.TBK.better_animation_mob.server.modbusevent.network.message;

import com.TBK.better_animation_mob.server.modbusevent.cap.Capabilities;
import com.TBK.better_animation_mob.server.modbusevent.entity.parch.PiglinPatch;
import com.TBK.better_animation_mob.server.modbusevent.network.Handler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncAnimAttack implements Packet<PacketListener> {
    private final int id;
    private final Entity entity;

    public PacketSyncAnimAttack(FriendlyByteBuf buf) {
        Minecraft mc = Minecraft.getInstance();
        assert mc.level != null;
        this.entity = mc.level.getEntity(buf.readInt());
        this.id = buf.readInt();
    }

    public PacketSyncAnimAttack(Entity entity,int id) {
        this.entity = entity;
        this.id = id;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.entity.getId());
        buf.writeInt(this.id);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() ->{
            assert context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;
            handlerAnim();
        });
        context.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private void handlerAnim() {
        Handler.handlerManager(id,entity);
    }

    @Override
    public void handle(PacketListener p_131342_) {

    }
}
