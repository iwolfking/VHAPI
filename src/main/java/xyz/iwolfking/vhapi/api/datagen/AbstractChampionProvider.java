package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.annotations.Expose;
import com.mojang.serialization.ListBuilder;
import iskallia.vault.config.ChampionsConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedListBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractChampionProvider extends AbstractVaultConfigDataProvider<AbstractChampionProvider.Builder> {
    protected AbstractChampionProvider(DataGenerator generator, String modid) {
        super(generator, modid, "champions", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Champions Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<ChampionsConfig> {
        private float defaultChampionChance = 0.01F;
        private final Map<EntityPredicate, Float> entityChampionChance = new HashMap<>();
        private final List<ChampionsConfig.AttributeOverride> defaultAttributeOverrides = new ArrayList<>();
        private final Map<EntityPredicate, List<ChampionsConfig.AttributeOverride>> entityAttributeOverrides = new LinkedHashMap<>();
        private final Map<EntityPredicate, WeightedList<CompoundTag>> entityAffixesData = new LinkedHashMap<>();


        public Builder() {
            super(ChampionsConfig::new);
        }

        public Builder setChampionChance(float championChance) {
            this.defaultChampionChance = championChance;
            return this;
        }

        public Builder addChampionChance(EntityPredicate predicate, float chance) {
            entityChampionChance.put(predicate, chance);
            return this;
        }

        public Builder addDefaultAttributeOverride(String name, double value, String operator) {
            defaultAttributeOverrides.add(new ChampionsConfig.AttributeOverride(name, value, operator));
            return this;
        }

        public Builder addEntityAttributeOverride(EntityPredicate predicate, Consumer<BasicListBuilder<ChampionsConfig.AttributeOverride>> builderConsumer) {
            BasicListBuilder<ChampionsConfig.AttributeOverride> builder = new BasicListBuilder<>();
            entityAttributeOverrides.put(predicate, builder.build());
            return this;
        }

        public Builder addEntityAffixData(EntityPredicate predicate, Consumer<WeightedListBuilder<CompoundTag>> builderConsumer) {
            WeightedListBuilder<CompoundTag> builder = new WeightedListBuilder<>();
            builderConsumer.accept(builder);
            entityAffixesData.put(predicate, builder.build());
            return this;
        }

        @Override
        protected void configureConfig(ChampionsConfig config) {
            config.defaultAttributeOverrides.addAll(defaultAttributeOverrides);
            config.entityChampionChance.putAll(entityChampionChance);
            config.entityAttributeOverrides.putAll(entityAttributeOverrides);
            config.entityAffixesData.putAll(entityAffixesData);
            config.defaultChampionChance = defaultChampionChance;
        }
    }
}
