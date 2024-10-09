package xyz.iwolfking.vhapi.api.lib.config.loaders.gear;

import iskallia.vault.config.TrinketConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

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
