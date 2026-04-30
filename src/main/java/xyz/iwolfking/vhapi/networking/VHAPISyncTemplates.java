package xyz.iwolfking.vhapi.networking;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.LoaderRegistry;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class VHAPISyncTemplates {
    private final ResourceLocation id;
    private final byte[] structureNbt;


    public static final BiConsumer<VHAPISyncTemplates, FriendlyByteBuf> ENCODER = (message, buffer) -> {
        buffer.writeResourceLocation(message.id);
        buffer.writeByteArray(message.structureNbt);
    };
    public static final Function<FriendlyByteBuf, VHAPISyncTemplates> DECODER = buffer -> new VHAPISyncTemplates(buffer.readResourceLocation(), buffer.readByteArray());
    public static final BiConsumer<VHAPISyncTemplates, Supplier<NetworkEvent.Context>> CONSUMER = (message, context) -> {
        NetworkEvent.Context cont = context.get();
        message.handle(cont);
    };

    public VHAPISyncTemplates(ResourceLocation id, byte[] stuctureNbt) {
        this.id = id;
        this.structureNbt = stuctureNbt;


    }

    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            VHAPI.LOGGER.info("Received VHAPI template packet for {}, handling...", this.id);
            CompoundTag tag = null;
            try {
                tag = NbtIo.readCompressed(new ByteArrayInputStream(this.structureNbt));
            } catch (IOException e) {
                VHAPI.LOGGER.info("Failed to receive template {}", this.id);
                throw new RuntimeException(e);
            }
            LoaderRegistry.VHAPI_DATA_LOADER.TEMPLATES.put(this.id, tag);
        });
        context.setPacketHandled(true);
    }
}
