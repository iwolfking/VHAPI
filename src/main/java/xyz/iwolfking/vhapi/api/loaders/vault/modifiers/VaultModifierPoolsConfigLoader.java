package xyz.iwolfking.vhapi.api.loaders.vault.modifiers;

import iskallia.vault.config.VaultModifierPoolsConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;

import java.util.HashMap;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VaultModifierPoolsConfigLoader extends VaultConfigProcessor<VaultModifierPoolsConfig> {

    public VaultModifierPoolsConfigLoader() {
        super(new VaultModifierPoolsConfig(), "vault/modifier_pools");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultModifierPoolsConfig config : this.CUSTOM_CONFIGS.values()) {
            if(ModConfigs.VAULT_MODIFIER_POOLS.pools == null) {
                ModConfigs.VAULT_MODIFIER_POOLS.pools = new HashMap<>();
            }

            if(config.pools == null) {
                VHAPILoggerUtils.info("Modifier Pools: Config had a null map, skipping.");
                continue;
            }

            ModConfigs.VAULT_MODIFIER_POOLS.pools.putAll(config.pools);
        }
        super.afterConfigsLoad(event);
    }
}
