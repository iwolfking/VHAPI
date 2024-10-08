package xyz.iwolfking.vaultcrackerlib.api.util;

import iskallia.vault.dynamodel.DynamicModel;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.VaultCrackerLib;

public class ResourceLocUtils {
    public static ResourceLocation stripLocationForSprite(ResourceLocation loc) {
        return removeSuffixFromId(".png", DynamicModel.removePrefixFromId("textures/", loc));
    }

    public static ResourceLocation removeSuffixFromId(String suffix, ResourceLocation id) {
        return id.getPath().endsWith(suffix) ? new ResourceLocation(id.getNamespace(), id.getPath().replaceFirst(suffix, "")) : id;
    }

    public static ResourceLocation removePrefixFromId(String prefix, ResourceLocation id) {
        return id.getPath().startsWith(prefix) ? new ResourceLocation(id.getNamespace(), id.getPath().replaceFirst(prefix, "")) : id;
    }

    public static ResourceLocation appendToId(ResourceLocation id, String append) {
        String namespace = id.getNamespace();
        String path = id.getPath();
        return new ResourceLocation(namespace, path + append);
    }

    public static ResourceLocation prependToId(String prepend, ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), prepend + id.getPath());
    }
}
