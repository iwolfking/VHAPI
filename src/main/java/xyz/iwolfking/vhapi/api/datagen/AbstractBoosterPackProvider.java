package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.card.BoosterPackConfig;
import iskallia.vault.core.card.CardEntry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedLevelEntryListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedListBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractBoosterPackProvider extends AbstractVaultConfigDataProvider<AbstractBoosterPackProvider.Builder> {
    protected AbstractBoosterPackProvider(DataGenerator generator, String modid) {
        super(generator, modid, "card/booster_packs", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Booster Pack Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<BoosterPackConfig> {
        Map<String, BoosterPackConfig.BoosterPackEntry> entries = new HashMap<>();

        public Builder() {
            super(BoosterPackConfig::new);
        }

        public Builder addEntry(String key, String boosterPackName, ResourceLocation openedModel, ResourceLocation closedModel, Consumer<WeightedListBuilder<Integer>> rollConsumer, Consumer<WeightedListBuilder<Integer>> tierConsumer, Consumer<WeightedListBuilder<CardEntry.Color>> colorConsumer, Consumer<WeightedListBuilder<BoosterPackConfig.CardConfig>> cardConfigConsumer) {
            return this;
        }

        @Override
        protected void configureConfig(BoosterPackConfig config) {
            config.getValues().putAll(entries);
        }
    }
}
