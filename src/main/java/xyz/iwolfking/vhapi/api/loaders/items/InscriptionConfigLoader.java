package xyz.iwolfking.vhapi.api.loaders.items;


import iskallia.vault.config.InscriptionConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class InscriptionConfigLoader extends VaultConfigProcessor<InscriptionConfig> {
    public InscriptionConfigLoader() {
        super(new InscriptionConfig(), "inscriptions");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(InscriptionConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.INSCRIPTION.poolToModel.putAll(config.poolToModel);
            for(ResourceLocation key : config.pools.keySet()) {
                if(ModConfigs.INSCRIPTION.pools.containsKey(key)) {
                    ModConfigs.INSCRIPTION.pools.get(key).addAll(config.pools.get(key));
                }
                else {
                    ModConfigs.INSCRIPTION.pools.put(key, config.pools.get(key));
                }
            }
            if(config.ringWeights != null) {
                ModConfigs.INSCRIPTION.ringWeights = config.ringWeights;
            }
        }
        super.afterConfigsLoad(event);
    }
}
