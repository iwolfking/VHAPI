package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.general;

import iskallia.vault.config.ResearchConfig;
import iskallia.vault.config.VaultStatsConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultStatsConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VaultStatsConfigLoader extends VaultConfigDataLoader<VaultStatsConfig> {

    public VaultStatsConfigLoader(String namespace) {
        super(new VaultStatsConfig(), "vault/stats", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultStatsConfig config : this.CUSTOM_CONFIGS.values()) {
            if(config.getChests() != null) {
                ModConfigs.VAULT_STATS.getChests().putAll(config.getChests());
            }

            if(config.getBlocksMined() != null) {
                ((VaultStatsConfigAccessor)ModConfigs.VAULT_STATS).getBlocksMinedMap().putAll(config.getBlocksMined());
            }

            if(((VaultStatsConfigAccessor)config).getCompletionMap() != null) {
                ((VaultStatsConfigAccessor)ModConfigs.VAULT_STATS).getCompletionMap().putAll(((VaultStatsConfigAccessor)config).getCompletionMap());
            }

            if(((VaultStatsConfigAccessor)ModConfigs.VAULT_STATS).getMobsKilledMap() != null) {
                ((VaultStatsConfigAccessor)ModConfigs.VAULT_STATS).getMobsKilledMap().putAll(((VaultStatsConfigAccessor)config).getMobsKilledMap());
            }

            ((VaultStatsConfigAccessor)ModConfigs.VAULT_STATS).setTreasureRoomsOpened(config.getTreasureRoomsOpened());
            ((VaultStatsConfigAccessor)ModConfigs.VAULT_STATS).setPercentOfExperienceDealtAsDurabilityDamage(config.getPercentOfExperienceDealtAsDurabilityDamage());
            ((VaultStatsConfigAccessor)ModConfigs.VAULT_STATS).setFreeExperienceNotDealtAsDurabilityDamage(config.getFreeExperienceNotDealtAsDurabilityDamage());
        }
    }
}
