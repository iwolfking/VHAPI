package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear;

import iskallia.vault.VaultMod;
import iskallia.vault.config.TrinketConfig;
import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

public class TrinketConfigLoader extends VaultConfigDataLoader<TrinketConfig> {

    public TrinketConfigLoader(String namespace) {
        super(new TrinketConfig(), "trinkets", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(TrinketConfig config : this.CUSTOM_CONFIGS.values()) {
            for(ResourceLocation key : config.TRINKETS.keySet()) {
                ModConfigs.TRINKET.TRINKETS.put(key, config.TRINKETS.get(key));
            }
        }
    }
}
