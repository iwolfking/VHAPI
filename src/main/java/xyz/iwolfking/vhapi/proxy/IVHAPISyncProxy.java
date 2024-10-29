package xyz.iwolfking.vhapi.proxy;

import net.minecraftforge.network.NetworkDirection;
import xyz.iwolfking.vhapi.networking.VHAPISyncDescriptor;
import xyz.iwolfking.vhapi.networking.VHAPISyncNetwork;

public interface IVHAPISyncProxy {

    default void init() {
        VHAPISyncNetwork.VHAPI_CHANNEL.messageBuilder(VHAPISyncDescriptor.class, 0, NetworkDirection.PLAY_TO_CLIENT).encoder(VHAPISyncDescriptor.ENCODER).decoder(VHAPISyncDescriptor.DECODER).consumer(VHAPISyncDescriptor.CONSUMER).noResponse().add();
    }
}
