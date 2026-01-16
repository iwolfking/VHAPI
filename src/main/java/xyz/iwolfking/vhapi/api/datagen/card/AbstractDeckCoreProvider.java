
package xyz.iwolfking.vhapi.api.datagen.card;

import iskallia.vault.config.card.CardDeckConfig;
import iskallia.vault.config.card.DeckModifiersConfig;
import iskallia.vault.config.entry.IntRangeEntry;
import iskallia.vault.core.card.modifier.deck.DeckModifier;
import iskallia.vault.core.card.modifier.deck.DummyDeckModifier;
import iskallia.vault.core.card.modifier.deck.StatEfficiencyDeckModifier;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.core.world.roll.FloatRoll;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.dynamodel.model.item.PlainItemModel;
import iskallia.vault.init.ModConfigs;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedListBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.CardDeckConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.CardDeckConfigEntryAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.DeckModifiersConfigAccessor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractDeckCoreProvider extends AbstractVaultConfigDataProvider<AbstractDeckCoreProvider.Builder> {
    protected AbstractDeckCoreProvider(DataGenerator generator, String modid) {
        super(generator, modid, "card/deck_mods", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Card Deck Modifiers Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<DeckModifiersConfig> {
        Map<String, DeckModifier<?>> entries = new HashMap<>();
        Map<String, WeightedList<String>> pools = new HashMap<>();

        public Builder() {
            super(DeckModifiersConfig::new);
        }

        public <T extends DeckModifier<C>, C extends DeckModifier.Config> Builder addCore(String id, Function<C, T> coreFactory, C modifierConfig, String name, int colour, String model) {
            DeckModifier<C> modifier = coreFactory.apply(modifierConfig);
            modifier.setName(name);
            modifier.setColour(colour);
            modifier.setId(id);
            modifier.setModelId(model);
            entries.put(id, modifier);
            return this;
        }

        public Builder addPool(String poolId, Consumer<WeightedListBuilder<String>> builderConsumer) {
            WeightedListBuilder<String> builder = new WeightedListBuilder<>();
            builderConsumer.accept(builder);
            pools.put(poolId, builder.build());
            return this;
        }

        @Override
        protected void configureConfig(DeckModifiersConfig config) {
            config.getValues().putAll(entries);
            ((DeckModifiersConfigAccessor)config).getPools().putAll(pools);
        }
    }
}
