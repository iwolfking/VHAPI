package xyz.iwolfking.vhapi.api.loaders.gear;

import iskallia.vault.config.TrinketConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class TrinketConfigLoader extends VaultConfigProcessor<TrinketConfig> {

    public TrinketConfigLoader() {
        super(new TrinketConfig(), "trinkets");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(TrinketConfig config : this.CUSTOM_CONFIGS.values()) {
            for(ResourceLocation key : config.TRINKETS.keySet()) {
                ModConfigs.TRINKET.TRINKETS.put(key, config.TRINKETS.get(key));
            }
        }
        super.afterConfigsLoad(event);
    }
}
