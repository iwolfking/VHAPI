package xyz.iwolfking.vhapi.api.loaders.objectives;

import iskallia.vault.config.MonolithConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MonolithConfigLoader extends VaultConfigProcessor<MonolithConfig> {
    public static final MonolithConfig instance = new MonolithConfig();

    public MonolithConfigLoader() {
        super(new MonolithConfig(), "objectives/monolith");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(MonolithConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.MONOLITH = config;
        }
        super.afterConfigsLoad(event);
    }

}
