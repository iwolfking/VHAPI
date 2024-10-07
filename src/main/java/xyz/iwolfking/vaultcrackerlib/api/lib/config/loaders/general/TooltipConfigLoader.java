package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.general;

import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.VaultStatsConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.TooltipConfigAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultStatsConfigAccessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
