
package xyz.iwolfking.vhapi.api.datagen.card;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.card.BoosterPackConfig;
import iskallia.vault.config.card.CardDeckConfig;
import iskallia.vault.config.entry.IntRangeEntry;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.core.util.WeightedList;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedListBuilder;
import xyz.iwolfking.vhapi.api.loaders.card.DecksConfigLoader;
import xyz.iwolfking.vhapi.init.ModConfigs;
import xyz.iwolfking.vhapi.mixin.accessors.CardDeckConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.CardDeckConfigEntryAccessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractDeckProvider extends AbstractVaultConfigDataProvider<AbstractDeckProvider.Builder> {
    protected AbstractDeckProvider(DataGenerator generator, String modid) {
        super(generator, modid, "card/decks", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Card Decks Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<CardDeckConfig> {
        Map<String, CardDeckConfig.Entry> entries = new HashMap<>();

        public Builder() {
            super(CardDeckConfig::new);
        }

        public Builder addEntry(String id, String model, String name, IntRangeEntry essence, String[] layout, IntRangeEntry socketCount) {
            CardDeckConfig.Entry entry = new CardDeckConfig.Entry(model, name, layout);
            ((CardDeckConfigEntryAccessor)entry).setEssence(essence);
            ((CardDeckConfigEntryAccessor)entry).setSocketCount(socketCount);
            entries.put(id, entry);
            return this;
        }

        @Override
        protected void configureConfig(CardDeckConfig config) {
            ((CardDeckConfigAccessor)config).setValues(entries);
        }
    }
}
