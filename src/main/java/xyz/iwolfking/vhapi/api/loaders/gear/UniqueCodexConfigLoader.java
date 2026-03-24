package xyz.iwolfking.vhapi.api.loaders.gear;

import iskallia.vault.config.UniqueCodexConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class UniqueCodexConfigLoader extends VaultConfigProcessor<UniqueCodexConfig> {
    public UniqueCodexConfigLoader() {
        super(new UniqueCodexConfig(), "unique_codex");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation configKey : this.CUSTOM_CONFIGS.keySet()) {
            UniqueCodexConfig config = this.CUSTOM_CONFIGS.get(configKey);
            ModConfigs.UNIQUE_CODEX.getPages().addAll(config.getPages());
            config.getIntroduction().getIndex().forEach((slotType, resourceLocations) -> {
                if(!ModConfigs.UNIQUE_CODEX.getIntroduction().getIndex().containsKey(slotType)) {
                    ModConfigs.UNIQUE_CODEX.getIntroduction().getIndex().put(slotType, resourceLocations);
                }
                else {
                    ModConfigs.UNIQUE_CODEX.getIntroduction().getIndex().get(slotType).addAll(resourceLocations);
                }
            });
        }
        super.afterConfigsLoad(event);
    }
}
