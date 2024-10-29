package xyz.iwolfking.vhapi.api.loaders.objectives;

import iskallia.vault.config.ScavengerConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ScavengerConfigLoader extends VaultConfigProcessor<ScavengerConfig> {
    public static final ScavengerConfig instance = new ScavengerConfig();

    public ScavengerConfigLoader() {
        super(new ScavengerConfig(), "objectives/scavenger");
    }



    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ScavengerConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.SCAVENGER = config;
        }
        super.afterConfigsLoad(event);
    }

}
