package xyz.iwolfking.vhapi.api.lib.core.datagen;

import iskallia.vault.config.TooltipConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTooltipProvider extends AbstractVaultConfigDataProvider {
    protected AbstractTooltipProvider(DataGenerator generator, String modid) {
        super(generator, modid, "tooltips");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Tooltip Data Provider";
    }

    public static class TooltipConfigBuilder {
        List<TooltipConfig.TooltipEntry> entries = new ArrayList<>();

        public TooltipConfigBuilder addTooltipEntry(ResourceLocation itemName, String tooltipText) {
            entries.add(new TooltipConfig.TooltipEntry(itemName.toString(), tooltipText));
            return this;
        }

        public TooltipConfig build() {
            TooltipConfig newConfig = new TooltipConfig();
            entries.forEach(tooltipEntry -> {
                ((TooltipConfigAccessor)newConfig).getTooltips().add(tooltipEntry);
            });
            return newConfig;
        }

    }
}
