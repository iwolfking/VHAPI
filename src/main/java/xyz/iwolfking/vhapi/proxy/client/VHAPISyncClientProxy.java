package xyz.iwolfking.vhapi.proxy.client;
import net.minecraftforge.common.MinecraftForge;
import xyz.iwolfking.vhapi.proxy.IVHAPISyncProxy;

public class VHAPISyncClientProxy implements IVHAPISyncProxy
{
    @Override
    public void init() {
        IVHAPISyncProxy.super.init();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
