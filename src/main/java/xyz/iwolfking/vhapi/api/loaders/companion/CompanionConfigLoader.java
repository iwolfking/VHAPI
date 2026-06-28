package xyz.iwolfking.vhapi.api.loaders.companion;

import iskallia.vault.config.CompanionsConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.CompanionsConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.HashSet;
import java.util.Set;

public class CompanionConfigLoader extends VaultConfigProcessor<CompanionsConfig> {
    public CompanionConfigLoader() {
        super(new CompanionsConfig(), "companions");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, companionsConfig) -> {
            ((CompanionsConfigAccessor)ModConfigs.COMPANIONS).getPetWeights().putAll(((CompanionsConfigAccessor)companionsConfig).getPetWeights());
        });
        super.afterConfigsLoad(event);
    }
}
