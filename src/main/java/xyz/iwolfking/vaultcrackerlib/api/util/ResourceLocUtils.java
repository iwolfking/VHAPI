package xyz.iwolfking.vaultcrackerlib.api.util;

import iskallia.vault.dynamodel.DynamicModel;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.VaultCrackerLib;

public class ResourceLocUtils {
    /**
     * This is used during in VHAPI during texture stitching to clean up the resource locations to work with what is expected for input.
     * @param loc The ResourceLocation that will have "textures/" removed from the front and .png removed from the back.
     * @return A ResourceLocation with no textures in the front of it's path, and not ending in .png
     */
    public static ResourceLocation stripLocationForSprite(ResourceLocation loc) {
        return removeSuffixFromId(".png", DynamicModel.removePrefixFromId("textures/", loc));
    }

    /**
     *
     * @param suffix The suffix to remove from the ResourceLocation.
     * @param id The ResourceLocation to remove the suffix from.
     * @return A ResourceLocation with the suffix removed from it.
     */
    public static ResourceLocation removeSuffixFromId(String suffix, ResourceLocation id) {
        return id.getPath().endsWith(suffix) ? new ResourceLocation(id.getNamespace(), id.getPath().replaceFirst(suffix, "")) : id;
    }

    /**
     *
     * @param prefix The prefix to remove from the ResourceLocation.
     * @param id The ResourceLocation to remove the prefix from.
     * @return A ResourceLocation with the prefix removed from it.
     */
    public static ResourceLocation removePrefixFromId(String prefix, ResourceLocation id) {
        return id.getPath().startsWith(prefix) ? new ResourceLocation(id.getNamespace(), id.getPath().replaceFirst(prefix, "")) : id;
    }

    /**
     *
     * @param id  The ResourceLocation to append a String to.
     * @param append A String that will be appended to the end of id.
     * @return A ResourceLocation with the string passed in appended.
     */
    public static ResourceLocation appendToId(ResourceLocation id, String append) {
        String namespace = id.getNamespace();
        String path = id.getPath();
        return new ResourceLocation(namespace, path + append);
    }

    /**
     *
     * @param id  The ResourceLocation to prepend a String to.
     * @param prepend A String that will be prepended to the end of id.
     * @return A ResourceLocation with the string passed in prepended.
     */
    public static ResourceLocation prependToId(String prepend, ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), prepend + id.getPath());
    }

    /**
     *
     * @param loc A ResourceLocation that will have the namespace portion swapped with the newNamespace input.
     * @param newNamespace The namespace to set the ResourceLocation to.
     * @return A ResourceLocation with the namespace swapped for what is passed in.
     */
    public static ResourceLocation swapNamespace(ResourceLocation loc, String newNamespace) {
        return new ResourceLocation(newNamespace, loc.getPath());
    }
}
