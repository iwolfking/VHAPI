package xyz.iwolfking.vhapi.api.extensions.general;

import iskallia.vault.block.VaultCrateBlock.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import xyz.iwolfking.vhapi.api.extensions.lib.IEnumFactory;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class VaultCrateTypeFactory implements IEnumFactory {
    public static final VaultCrateTypeFactory INSTANCE = new VaultCrateTypeFactory();

    private final Map<Type, CratePair> createdCrateKeys = new LinkedHashMap<>();

    private VaultCrateTypeFactory() {
    }

    @Override
    public String getTargetEnumClassName() {
        return Type.class.getName();
    }


    public Type create(String enumName, CratePair crateKey, Class<?>... switchUsers) {
        Type created = appendConstant(enumName, new Object[0], switchUsers);
        createdCrateKeys.put(created, crateKey);
        return created;
    }

    public Type create(String enumName, Block block, Class<?>... switchUsers) {
        return create(enumName, new CratePair(enumName.toLowerCase(Locale.ROOT), block), switchUsers);
    }


    public Type create(ResourceLocation enumId, Block block, Class<?>... switchUsers) {
        String internalName = toInternalName(enumId);
        return create(internalName, new CratePair(enumId.getPath().toLowerCase(Locale.ROOT), block), switchUsers);
    }

    public boolean isFactoryCreated(Type type) {
        return createdCrateKeys.containsKey(type);
    }

    @Nullable
    public CratePair getCrateKey(Type type) {
        return createdCrateKeys.get(type);
    }

    public Map<Type, CratePair> getCreatedCrateKeys() {
        return Collections.unmodifiableMap(createdCrateKeys);
    }

    public record CratePair(String key, Block block){}
}