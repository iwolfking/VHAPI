package xyz.iwolfking.vhapi.api.extensions.general;

import iskallia.vault.network.message.transmog.DiscoveredEntriesMessage.Type;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.extensions.lib.IEnumFactory;

public class DiscoveredEntriesTypeFactory implements IEnumFactory {
    public static final DiscoveredEntriesTypeFactory INSTANCE = new DiscoveredEntriesTypeFactory();

    private DiscoveredEntriesTypeFactory() {
    }

    @Override
    public String getTargetEnumClassName() {
        return Type.class.getName();
    }

    public Type create(String enumName, Class<?>... switchUsers) {
        return appendConstant(enumName, new Object[0], switchUsers);
    }

    public Type create(ResourceLocation enumId, Class<?>... switchUsers) {
        return create(toInternalName(enumId), switchUsers);
    }
}