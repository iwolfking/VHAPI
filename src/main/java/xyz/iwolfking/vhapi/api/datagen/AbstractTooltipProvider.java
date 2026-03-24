package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.TooltipConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTooltipProvider extends AbstractVaultConfigDataProvider<AbstractTooltipProvider.Builder> {
    protected AbstractTooltipProvider(DataGenerator generator, String modid) {
        super(generator, modid, "tooltips", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Tooltip Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<TooltipConfig> {
        List<TooltipConfig.TooltipEntry> entries = new ArrayList<>();

        public Builder() {
            super(TooltipConfig::new);
        }

        public Builder addTooltipEntry(ResourceLocation itemName, String tooltipText) {
            entries.add(new TooltipConfig.TooltipEntry(itemName.toString(), tooltipText));
            return this;
        }

        @Override
        protected void configureConfig(TooltipConfig config) {
            entries.forEach(tooltipEntry -> {
                ((TooltipConfigAccessor)config).getTooltips().add(tooltipEntry);
            });
        }
    }
}
