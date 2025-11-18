package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.VoidCrucibleConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class VoidCrucibleConfigLoader extends VaultConfigProcessor<VoidCrucibleConfig> {
    public VoidCrucibleConfigLoader() {
        super(new VoidCrucibleConfig(), "void_crucible");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            VoidCrucibleConfig config = CUSTOM_CONFIGS.get(key);

            if(key.getPath().contains("overwrite")) {
                ModConfigs.VOID_CRUCIBLE = config;
            } else if (key.getPath().contains("remove")) {
                ModConfigs.VOID_CRUCIBLE.getThemes().removeAll(config.getThemes());
                ModConfigs.VOID_CRUCIBLE.getAllowedBlocks().removeAll(config.getAllowedBlocks());
            }
            else {
                for(ResourceLocation themeKey : config.getThemes()) {
                    ModConfigs.VOID_CRUCIBLE.getThemes().add(themeKey);
                    ModConfigs.VOID_CRUCIBLE.getAllowedBlocks().addAll(config.getAllowedBlocks());
                }
            }
        }
        super.afterConfigsLoad(event);
    }
}
