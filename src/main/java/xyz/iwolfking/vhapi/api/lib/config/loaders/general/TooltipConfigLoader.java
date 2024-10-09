package xyz.iwolfking.vhapi.api.lib.config.loaders.general;

import iskallia.vault.config.TooltipConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TooltipConfigLoader extends VaultConfigDataLoader<TooltipConfig> {
    public TooltipConfigLoader(String namespace) {
        super(new TooltipConfig(), "tooltips", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(TooltipConfig config : this.CUSTOM_CONFIGS.values()) {
            Set<TooltipConfig.TooltipEntry> entriesToRemove = new HashSet<>();
            for(TooltipConfig.TooltipEntry entry: ((TooltipConfigAccessor)config).getTooltips()) {
                for(TooltipConfig.TooltipEntry existingEntry : ((TooltipConfigAccessor)ModConfigs.TOOLTIP).getTooltips()) {
                    if(entry.getItem().equals(existingEntry.getItem())) {
                        entriesToRemove.add(existingEntry);
                    }
                }
                ((TooltipConfigAccessor)ModConfigs.TOOLTIP).getTooltips().removeAll(entriesToRemove);
                ((TooltipConfigAccessor)ModConfigs.TOOLTIP).getTooltips().addAll(((TooltipConfigAccessor) config).getTooltips());
            }
        }
    }
}
