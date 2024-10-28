package xyz.iwolfking.vhapi.proxy.client;

import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.proxy.IVHAPISyncProxy;

public class VHAPISyncClientProxy implements IVHAPISyncProxy
{
    @Override
    public void init() {
        IVHAPISyncProxy.super.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLogout(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        LoaderRegistry.VHAPI_DATA_LOADER.JSON_DATA.clear();
    }
}
