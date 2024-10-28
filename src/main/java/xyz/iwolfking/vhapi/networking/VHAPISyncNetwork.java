package xyz.iwolfking.vhapi.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import xyz.iwolfking.vhapi.VHAPI;

public class VHAPISyncNetwork {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel VHAPI_CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(VHAPI.MODID, "sync_configs"), () -> PROTOCOL_VERSION, version -> true, PROTOCOL_VERSION::equals);

    public static <T> void syncVHAPIConfigs(T message, ServerPlayer player) {
        VHAPISyncNetwork.VHAPI_CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
