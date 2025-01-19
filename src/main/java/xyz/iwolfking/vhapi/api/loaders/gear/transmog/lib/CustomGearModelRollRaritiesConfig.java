package xyz.iwolfking.vhapi.api.loaders.gear.transmog.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.Config;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;

public class CustomGearModelRollRaritiesConfig extends Config {

    @Expose
    public ResourceLocation itemRegistryName;

    @Expose
    public Map<String, List<String>> MODEL_ROLLS;
    @Override
    public String getName() {
        return "custom_gear_model_roll_rarities";
    }

    @Override
    protected void reset() {

    }
}
