package xyz.iwolfking.vhapi.api.loaders.general;

import iskallia.vault.config.VaultStatsConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultStatsConfigAccessor;


public class VaultStatsConfigLoader extends VaultConfigProcessor<VaultStatsConfig> {

    public VaultStatsConfigLoader() {
        super(new VaultStatsConfig(), "vault/stats");
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
        super.afterConfigsLoad(event);
    }
}
