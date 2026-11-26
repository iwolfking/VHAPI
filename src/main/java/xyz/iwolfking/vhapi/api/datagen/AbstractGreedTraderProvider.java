package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.greed.GreedHouseTuning;
import iskallia.vault.config.greed.GreedTraderConfig;
import iskallia.vault.core.world.roll.IntRoll;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.GreedTraderConfigAccessor;

public abstract class AbstractGreedTraderProvider
        extends AbstractVaultConfigDataProvider<AbstractGreedTraderProvider.Builder> {
    protected AbstractGreedTraderProvider(DataGenerator generator, String modid) {
        super(generator, modid, "greed/greed_trader", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Greed Trader Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<GreedTraderConfig> {
        @Nullable
        public Integer etchingPriceBase;
        @Nullable
        public Integer etchingPricePerTier;
        @Nullable
        public Integer dripUnlockTier;
        @Nullable
        public GreedHouseTuning house;
        public final Map<String, String> words = new LinkedHashMap<>();
        public final Map<String, List<String>> voice = new LinkedHashMap<>();
        public final Map<String, List<GreedTraderConfig.PoolEntry>> pools = new LinkedHashMap<>();
        public final Map<String, List<GreedTraderConfig.WeightedRoomPool>> roomPoolsByType = new LinkedHashMap<>();
        public final Map<String, List<GreedTraderConfig.WeightedModifier>> modifierPools = new LinkedHashMap<>();

        public Builder() {
            super(GreedTraderConfig::new);
        }

        public Builder etchingPricing(int base, int perTier) {
            this.etchingPriceBase = base;
            this.etchingPricePerTier = perTier;
            return this;
        }

        public Builder dripUnlockTier(int tier) {
            this.dripUnlockTier = tier;
            return this;
        }

        public Builder house(GreedHouseTuning house) {
            this.house = house;
            return this;
        }

        public Builder putWord(String key, String value) {
            this.words.put(key, value);
            return this;
        }

        public Builder addVoiceLines(String key, List<String> lines) {
            this.voice.computeIfAbsent(key, k -> new ArrayList<>()).addAll(lines);
            return this;
        }

        public Builder addPoolEntry(String poolId, Consumer<PoolEntryBuilder> configurator) {
            PoolEntryBuilder builder = new PoolEntryBuilder();
            configurator.accept(builder);
            this.pools.computeIfAbsent(poolId, k -> new ArrayList<>()).add(builder.build());
            return this;
        }

        public Builder addRoomPool(String roomType, String pool, int weight, int model) {
            String normalizedType = roomType.toUpperCase(Locale.ROOT);
            this.roomPoolsByType.computeIfAbsent(normalizedType, k -> new ArrayList<>())
                    .add(new GreedTraderConfig.WeightedRoomPool(pool, weight, model));
            return this;
        }

        public Builder addModifierPoolEntry(String poolName, String modifier, int weight) {
            this.modifierPools.computeIfAbsent(poolName, k -> new ArrayList<>())
                    .add(new GreedTraderConfig.WeightedModifier(modifier, weight));
            return this;
        }

        @Override
        protected void configureConfig(GreedTraderConfig config) {
            GreedTraderConfigAccessor acc = (GreedTraderConfigAccessor) config;
            acc.setEtchingPriceBase(this.etchingPriceBase);
            acc.setEtchingPricePerTier(this.etchingPricePerTier);
            acc.setDripUnlockTier(this.dripUnlockTier);
            acc.setHouse(this.house);
            acc.setWords(new LinkedHashMap<>(this.words));

            Map<String, List<String>> voiceCopy = new LinkedHashMap<>();
            this.voice.forEach((k, v) -> voiceCopy.put(k, new ArrayList<>(v)));
            acc.setVoice(voiceCopy);

            Map<String, List<GreedTraderConfig.PoolEntry>> poolsCopy = new LinkedHashMap<>();
            this.pools.forEach((k, v) -> poolsCopy.put(k, new ArrayList<>(v)));
            acc.setPools(poolsCopy);

            Map<String, List<GreedTraderConfig.WeightedRoomPool>> roomsCopy = new LinkedHashMap<>();
            this.roomPoolsByType.forEach((k, v) -> roomsCopy.put(k, new ArrayList<>(v)));
            acc.setRoomPoolsByType(roomsCopy);

            Map<String, List<GreedTraderConfig.WeightedModifier>> modsCopy = new LinkedHashMap<>();
            this.modifierPools.forEach((k, v) -> modsCopy.put(k, new ArrayList<>(v)));
            acc.setModifierPools(modsCopy);
        }
    }

    public static class PoolEntryBuilder {
        private final GreedTraderConfig.PoolEntry entry = new GreedTraderConfig.PoolEntry();
        private final GreedTraderConfigAccessor.PoolEntryAccessor acc =
                (GreedTraderConfigAccessor.PoolEntryAccessor) this.entry;

        public PoolEntryBuilder type(String type) {
            acc.setType(type);
            return this;
        }

        public PoolEntryBuilder weight(int weight) {
            acc.setWeight(weight);
            return this;
        }

        public PoolEntryBuilder greedTierRange(int minGreedTier, int maxGreedTier) {
            acc.setMinGreedTier(minGreedTier);
            acc.setMaxGreedTier(maxGreedTier);
            return this;
        }

        public PoolEntryBuilder itemStack(ItemStack stack) {
            acc.setItemStack(stack.copy());
            return this;
        }

        public PoolEntryBuilder catalyst(Consumer<CatalystBuilderHelper> configurator) {
            CatalystBuilderHelper helper = new CatalystBuilderHelper();
            configurator.accept(helper);
            acc.setCatalyst(helper.build());
            return this;
        }

        public PoolEntryBuilder inscription(Consumer<InscriptionBuilderHelper> configurator) {
            InscriptionBuilderHelper helper = new InscriptionBuilderHelper();
            configurator.accept(helper);
            acc.setInscription(helper.build());
            return this;
        }

        public PoolEntryBuilder crystal(Consumer<CrystalBuilderHelper> configurator) {
            CrystalBuilderHelper helper = new CrystalBuilderHelper();
            configurator.accept(helper);
            acc.setCrystal(helper.build());
            return this;
        }

        public GreedTraderConfig.PoolEntry build() {
            return this.entry;
        }
    }

    public static class CatalystBuilderHelper {
        private final GreedTraderConfig.CatalystBuilder builder = new GreedTraderConfig.CatalystBuilder();
        private final GreedTraderConfigAccessor.CatalystBuilderAccessor acc =
                (GreedTraderConfigAccessor.CatalystBuilderAccessor) this.builder;

        public CatalystBuilderHelper size(IntRoll size) {
            acc.setSize(size);
            return this;
        }

        public CatalystBuilderHelper modifierPool(@Nullable String pool, int minModifiers, int maxModifiers) {
            acc.setModifierPool(pool);
            acc.setMinModifiers(minModifiers);
            acc.setMaxModifiers(maxModifiers);
            return this;
        }

        public CatalystBuilderHelper isSuper(boolean isSuper) {
            acc.setSuper(isSuper);
            return this;
        }

        public GreedTraderConfig.CatalystBuilder build() {
            return this.builder;
        }
    }

    public static class InscriptionBuilderHelper {
        private final GreedTraderConfig.InscriptionBuilder builder = new GreedTraderConfig.InscriptionBuilder();
        private final GreedTraderConfigAccessor.InscriptionBuilderAccessor acc =
                (GreedTraderConfigAccessor.InscriptionBuilderAccessor) this.builder;
        private final List<GreedTraderConfig.RoomEntry> rooms = new ArrayList<>();

        public InscriptionBuilderHelper addRoom(@Nullable String pool, @Nullable String roomType, int count, int color) {
            GreedTraderConfig.RoomEntry room = new GreedTraderConfig.RoomEntry();
            GreedTraderConfigAccessor.RoomEntryAccessor roomAcc = (GreedTraderConfigAccessor.RoomEntryAccessor) room;
            roomAcc.setPool(pool);
            roomAcc.setRoomType(roomType);
            roomAcc.setCount(count);
            roomAcc.setColor(color);
            this.rooms.add(room);
            return this;
        }

        public InscriptionBuilderHelper timeRange(int minTime, int maxTime) {
            acc.setMinTime(minTime);
            acc.setMaxTime(maxTime);
            return this;
        }

        public InscriptionBuilderHelper modifierPool(@Nullable String pool, int minModifiers, int maxModifiers) {
            acc.setModifierPool(pool);
            acc.setMinModifiers(minModifiers);
            acc.setMaxModifiers(maxModifiers);
            return this;
        }

        public InscriptionBuilderHelper isSuper(boolean isSuper) {
            acc.setSuper(isSuper);
            return this;
        }

        public InscriptionBuilderHelper color(@Nullable Integer color) {
            acc.setColor(color);
            return this;
        }

        public GreedTraderConfig.InscriptionBuilder build() {
            acc.setRooms(new ArrayList<>(this.rooms));
            return this.builder;
        }
    }

    public static class CrystalBuilderHelper {
        private final GreedTraderConfig.CrystalBuilder builder = new GreedTraderConfig.CrystalBuilder();
        private final GreedTraderConfigAccessor.CrystalBuilderAccessor acc =
                (GreedTraderConfigAccessor.CrystalBuilderAccessor) this.builder;

        public CrystalBuilderHelper poolOne(@Nullable String pool, int min, int max) {
            acc.setModifierPool(pool);
            acc.setMinModifiers(min);
            acc.setMaxModifiers(max);
            return this;
        }

        public CrystalBuilderHelper poolTwo(@Nullable String pool, int min, int max) {
            acc.setModifierPoolTwo(pool);
            acc.setMinModifiersTwo(min);
            acc.setMaxModifiersTwo(max);
            return this;
        }

        public CrystalBuilderHelper poolThree(@Nullable String pool, int min, int max) {
            acc.setModifierPoolThree(pool);
            acc.setMinModifiersThree(min);
            acc.setMaxModifiersThree(max);
            return this;
        }

        public GreedTraderConfig.CrystalBuilder build() {
            return this.builder;
        }
    }
}