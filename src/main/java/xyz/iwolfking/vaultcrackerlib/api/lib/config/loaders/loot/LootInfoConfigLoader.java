package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.loot;

import iskallia.vault.config.LootInfoConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

public class LootInfoConfigLoader extends VaultConfigDataLoader<LootInfoConfig> {

    public LootInfoConfigLoader(String namespace) {
        super(new LootInfoConfig(), "loot_info", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(LootInfoConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.LOOT_INFO_CONFIG.getLootInfoMap().putAll(config.getLootInfoMap());
        }

    }
}