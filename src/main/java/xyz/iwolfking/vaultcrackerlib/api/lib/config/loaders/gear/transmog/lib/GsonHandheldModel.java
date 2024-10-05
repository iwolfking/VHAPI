package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.dynamodel.DynamicModelProperties;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import net.minecraft.resources.ResourceLocation;

public class GsonHandheldModel {

    @Expose
    ResourceLocation id;

    @Expose
    String displayName;

    @Expose
    boolean discoverOnRoll;

    @Expose
    boolean allowTransmogrification;


    public GsonHandheldModel(ResourceLocation id, String displayName, boolean discoverOnRoll, boolean allowTransmogrification) {
        this.id = id;
        this.displayName = displayName;
        this.discoverOnRoll = discoverOnRoll;
        this.allowTransmogrification = allowTransmogrification;
    }

    public ResourceLocation getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isDiscoverOnRoll() {
        return discoverOnRoll;
    }

    public boolean isAllowTransmogrification() {
        return allowTransmogrification;
    }
}
