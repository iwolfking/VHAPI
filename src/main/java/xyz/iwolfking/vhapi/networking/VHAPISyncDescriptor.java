package xyz.iwolfking.vhapi.networking;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import iskallia.vault.init.ModConfigs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.data.core.ConfigData;
import xyz.iwolfking.vhapi.api.lib.core.processors.IConfigProcessor;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;
import xyz.iwolfking.vhapi.networking.util.StringCompressor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class VHAPISyncDescriptor {
    private final Map<ResourceLocation, byte[]> configsMap;



    public static final BiConsumer<VHAPISyncDescriptor, FriendlyByteBuf> ENCODER = (message, buffer) -> buffer.writeMap(message.configsMap, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeByteArray);
    public static final Function<FriendlyByteBuf, VHAPISyncDescriptor> DECODER = buffer -> new VHAPISyncDescriptor(buffer.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readByteArray));
    public static final BiConsumer<VHAPISyncDescriptor, Supplier<NetworkEvent.Context>> CONSUMER = (message, context) -> {
        NetworkEvent.Context cont = context.get();
        message.handle(cont);
    };

    public VHAPISyncDescriptor(Map<ResourceLocation, byte[]> configsMap) {
        this.configsMap = configsMap;
    }

    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            VHAPILoggerUtils.info("Received VHAPI sync packet, handling...");
            Map<ResourceLocation, JsonElement> loadedConfigs = new HashMap<>();
           try {
               Map<ResourceLocation, byte[]> compressedJsonMap = this.configsMap;

               for(ResourceLocation loc : compressedJsonMap.keySet()) {
                    String rawJsonString = StringCompressor.decompress(compressedJsonMap.get(loc));
                    if(rawJsonString == null) {
                        VHAPILoggerUtils.info("Received config from server that was unexpectedly null: " + loc);
                        throw new RuntimeException();
                    }
                    loadedConfigs.put(loc, JsonParser.parseString(rawJsonString));
               }
           } catch (Exception e) {
               VHAPILoggerUtils.info("Failed to receive datapacks from server.");
               throw new RuntimeException(e);
           }
            LoaderRegistry.VHAPI_DATA_LOADER.JSON_DATA.putAll(loadedConfigs);
            LoaderRegistry.VHAPI_DATA_LOADER.gatherConfigsToProcessors();
        });
        context.setPacketHandled(true);
    }
}
