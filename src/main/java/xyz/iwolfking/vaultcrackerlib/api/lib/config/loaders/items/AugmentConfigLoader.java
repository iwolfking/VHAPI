package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.items;

import iskallia.vault.config.AugmentConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.AugmentConfigAccessor;

import java.util.HashMap;


public class AugmentConfigLoader extends VaultConfigDataLoader<AugmentConfig> {
    public AugmentConfigLoader(String namespace) {
        super(new AugmentConfig(), "augments", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(AugmentConfig config : this.CUSTOM_CONFIGS.values()) {
            ((AugmentConfigAccessor)ModConfigs.AUGMENT).getDrops().putAll(((AugmentConfigAccessor)config).getDrops());
        }
    }
}
