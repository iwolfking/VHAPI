package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.talents;

import iskallia.vault.config.ExpertisesConfig;
import iskallia.vault.config.TalentsConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

public class TalentConfigLoader extends VaultConfigDataLoader<TalentsConfig> {
    public TalentConfigLoader(String namespace) {
        super(new TalentsConfig(), "talents/talent", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(TalentsConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.TALENTS.tree.skills.addAll(config.tree.skills);
        }
    }
}
