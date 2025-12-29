package xyz.iwolfking.vhapi.api.datagen.card;

import iskallia.vault.config.card.CardModifiersConfig;
import iskallia.vault.core.card.CardCondition;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.core.card.CardScaler;
import iskallia.vault.core.card.modifier.card.CardModifier;
import iskallia.vault.core.util.WeightedList;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractCardModifiersProvider extends AbstractVaultConfigDataProvider<AbstractCardModifiersProvider.Builder> {
    protected AbstractCardModifiersProvider(DataGenerator generator, String modid) {
        super(generator, modid, "card/modifiers", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Card Modifiers Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<CardModifiersConfig> {
        private Map<String, CardEntry.Config> values;
        private Map<String, WeightedList<String>> pools;

        public Builder() {
            super(CardModifiersConfig::new);
        }

        public Builder addCardModifier(String key, CardEntry.Config config) {
            values.put(key, config);
            return this;
        }

        public Builder addCardModifier(String key, Component name, @Nullable Consumer<Set<CardEntry.Color>> colorsConsumer, @Nullable Consumer<Set<String>> groupsConsumer, String model, CardModifier<?> value, @Nullable CardScaler scaler, @Nullable CardCondition condition) {
            Set<CardEntry.Color> colors = new HashSet<>();
            Set<String> groups = new HashSet<>();
            if(colorsConsumer != null) {
                colorsConsumer.accept(colors);
            }
            if(groupsConsumer != null) {
                groupsConsumer.accept(groups);
            }
            values.put(key, new CardEntry.Config(name, colors, groups, model, value, scaler, condition));
            return this;
        }

        public Builder addCardModifier(String key, Component name, String model, Consumer<Set<String>> groupsConsumer, CardModifier<?> value) {
            return addCardModifier(key, name, null, groupsConsumer, model, value, null, null);
        }



        @Override
        protected void configureConfig(CardModifiersConfig config) {
        }
    }
}
