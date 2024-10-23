package xyz.iwolfking.vhapi.api.loaders.gear.transmog.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.dynamodel.DynamicModelProperties;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.dynamodel.model.item.PlainItemModel;
import iskallia.vault.dynamodel.model.item.shield.ShieldModel;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.mixin.accessors.DynamicModelPropertiesAccessor;

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

    public DynamicModelProperties getProperties() {
        DynamicModelProperties properties = new DynamicModelProperties();
        ((DynamicModelPropertiesAccessor)properties).setAllowTransmogrification(allowTransmogrification);
        ((DynamicModelPropertiesAccessor)properties).setDiscoverOnRoll(discoverOnRoll);
        return properties;
    }

    public HandHeldModel getModel() {
        return new HandHeldModel(id, displayName).properties(getProperties());
    }

    public ShieldModel getShieldModel() {
        return new ShieldModel(id, displayName).properties(getProperties());
    }

    public PlainItemModel getPlainModel() {
        return new PlainItemModel(id, displayName).properties(getProperties());
    }
}
