package xyz.iwolfking.vhapi.api.datagen.card;

import iskallia.vault.config.card.BoosterPackConfig;
import iskallia.vault.core.card.CardEntry;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedListBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.BoosterPackConfigAccessor;

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

        public Builder addEntry(String key, String boosterPackName, String openedModel, String closedModel, Consumer<WeightedListBuilder<Integer>> rollConsumer, Consumer<WeightedListBuilder<Integer>> tierConsumer, Consumer<WeightedListBuilder<CardEntry.Color>> colorConsumer, Consumer<WeightedListBuilder<List<BoosterPackConfig.CardConfig>>> cardConfigConsumer) {
            WeightedListBuilder<Integer> rolls = new WeightedListBuilder<>();
            WeightedListBuilder<Integer> tiers = new WeightedListBuilder<>();
            WeightedListBuilder<CardEntry.Color> colors = new WeightedListBuilder<>();
            WeightedListBuilder<List<BoosterPackConfig.CardConfig>> cards = new WeightedListBuilder<>();
            rollConsumer.accept(rolls);
            tierConsumer.accept(tiers);
            colorConsumer.accept(colors);
            cardConfigConsumer.accept(cards);
            entries.put(key, new BoosterPackConfig.BoosterPackEntry(new TextComponent(boosterPackName), new BoosterPackConfig.BoosterPackModel(closedModel + "#inventory", openedModel + "#inventory"), rolls.build(), tiers.build(), colors.build(), cards.build()));
            return this;
        }

        @Override
        protected void configureConfig(BoosterPackConfig config) {
            ((BoosterPackConfigAccessor)config).setValues(entries);
        }
    }
}
