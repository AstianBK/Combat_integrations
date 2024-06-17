package com.TBK.combat_integration.server.modbusevent.network;

import com.TBK.combat_integration.CombatIntegration;
import com.TBK.combat_integration.server.modbusevent.network.message.PacketSyncAnimAttack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1.0";

    public static SimpleChannel MOD_CHANNEL;

    public static void registerMessages() {
        SimpleChannel channel= NetworkRegistry.ChannelBuilder.named(
                        new ResourceLocation(CombatIntegration.MODID, "messages"))
                .networkProtocolVersion(()-> PROTOCOL_VERSION)
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        MOD_CHANNEL=channel;

        int index = 0;

        channel.registerMessage(index++, PacketSyncAnimAttack.class, PacketSyncAnimAttack::write,
                PacketSyncAnimAttack::new, PacketSyncAnimAttack::handle);



    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        MOD_CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),message);
    }

    public static <MSG> void sendToServer(MSG message) {
        MOD_CHANNEL.sendToServer(message);
    }

    public static <MSG> void sendToAllTracking(MSG message, LivingEntity entity) {
        MOD_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);
    }
}
