package xyz.iwolfking.vhapi.api.lib.config.loaders.items;


import iskallia.vault.config.InscriptionConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class InscriptionConfigLoader extends VaultConfigDataLoader<InscriptionConfig> {
    public InscriptionConfigLoader(String namespace) {
        super(new InscriptionConfig(), "inscriptions", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(InscriptionConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.INSCRIPTION.poolToModel.putAll(config.poolToModel);
            for(ResourceLocation key : ModConfigs.INSCRIPTION.pools.keySet()) {
                if(config.pools.containsKey(key)) {
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
    }
}
