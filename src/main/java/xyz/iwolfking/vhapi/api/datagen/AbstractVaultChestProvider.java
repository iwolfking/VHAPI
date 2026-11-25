package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultChestConfig;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.util.VaultRarity;
import iskallia.vault.world.vault.chest.ExplosionEffect;
import iskallia.vault.world.vault.chest.GatewayTrapEffect;
import iskallia.vault.world.vault.chest.ManaStealerTrapEffect;
import iskallia.vault.world.vault.chest.MinefieldTrapEffect;
import iskallia.vault.world.vault.chest.MobTrapEffect;
import iskallia.vault.world.vault.chest.PotionCloudEffect;
import iskallia.vault.world.vault.chest.TeleportTrapEffect;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;

public abstract class AbstractVaultChestProvider
        extends AbstractVaultConfigDataProvider<AbstractVaultChestProvider.Builder> {
    private final String configName;

    protected AbstractVaultChestProvider(DataGenerator generator, String modid, String configName) {
        super(generator, modid, "vault/chest", () -> new Builder(configName));
        this.configName = configName;
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Chest (" + this.configName + ") Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultChestConfig> {
        public final Map<VaultRarity, Double> rarityDistribution = new LinkedHashMap<>();
        public final List<MobTrapEffect> mobTrapEffects = new ArrayList<>();
        public final List<ExplosionEffect> explosionEffects = new ArrayList<>();
        public final List<PotionCloudEffect> potionCloudEffects = new ArrayList<>();
        public final List<TeleportTrapEffect> teleportTrapEffects = new ArrayList<>();
        public final List<ManaStealerTrapEffect> manaStealerTrapEffects = new ArrayList<>();
        public final List<MinefieldTrapEffect> minefieldTrapEffects = new ArrayList<>();
        public final List<GatewayTrapEffect> gatewayTrapEffects = new ArrayList<>();
        public final LevelEntryList<VaultChestConfig.Level> levels = new LevelEntryList<>();

        public Builder(String configName) {
            super(() -> new VaultChestConfig(configName));
        }

        public Builder putRarityDistribution(VaultRarity rarity, double cumulativeCdf) {
            this.rarityDistribution.put(rarity, cumulativeCdf);
            return this;
        }

        public Builder addMobTrapEffect(MobTrapEffect effect) {
            this.mobTrapEffects.add(effect);
            return this;
        }

        public Builder addExplosionEffect(ExplosionEffect effect) {
            this.explosionEffects.add(effect);
            return this;
        }

        public Builder addPotionCloudEffect(PotionCloudEffect effect) {
            this.potionCloudEffects.add(effect);
            return this;
        }

        public Builder addTeleportTrapEffect(TeleportTrapEffect effect) {
            this.teleportTrapEffects.add(effect);
            return this;
        }

        public Builder addManaStealerTrapEffect(ManaStealerTrapEffect effect) {
            this.manaStealerTrapEffects.add(effect);
            return this;
        }

        public Builder addMinefieldTrapEffect(MinefieldTrapEffect effect) {
            this.minefieldTrapEffects.add(effect);
            return this;
        }

        public Builder addGatewayTrapEffect(GatewayTrapEffect effect) {
            this.gatewayTrapEffects.add(effect);
            return this;
        }

        public Builder addLevel(int level, double probability, @Nullable Consumer<WeightedList<String>> poolBuilder) {
            VaultChestConfig.Level entry = new VaultChestConfig.Level(level);
            entry.probability = probability;
            if (poolBuilder != null) {
                poolBuilder.accept(entry.pool);
            }
            this.levels.add(entry);
            return this;
        }

        @Override
        protected void configureConfig(VaultChestConfig config) {
            if (!this.rarityDistribution.isEmpty()) {
                config.RARITY_DISTRIBUTION = new LinkedHashMap<>(this.rarityDistribution);
            }
            if (!this.mobTrapEffects.isEmpty()) {
                config.MOB_TRAP_EFFECTS = new ArrayList<>(this.mobTrapEffects);
            }
            if (!this.explosionEffects.isEmpty()) {
                config.EXPLOSION_EFFECTS = new ArrayList<>(this.explosionEffects);
            }
            if (!this.potionCloudEffects.isEmpty()) {
                config.POTION_CLOUD_EFFECTS = new ArrayList<>(this.potionCloudEffects);
            }
            if (!this.teleportTrapEffects.isEmpty()) {
                config.TELEPORT_TRAP_EFFECTS = new ArrayList<>(this.teleportTrapEffects);
            }
            if (!this.manaStealerTrapEffects.isEmpty()) {
                config.MANA_STEALER_TRAP_EFFECTS = new ArrayList<>(this.manaStealerTrapEffects);
            }
            if (!this.minefieldTrapEffects.isEmpty()) {
                config.MINEFIELD_TRAP_EFFECTS = new ArrayList<>(this.minefieldTrapEffects);
            }
            if (!this.gatewayTrapEffects.isEmpty()) {
                config.GATEWAY_TRAP_EFFECTS = new ArrayList<>(this.gatewayTrapEffects);
            }
            if (!this.levels.isEmpty()) {
                config.LEVELS = new LevelEntryList<>();
                config.LEVELS.addAll(this.levels);
            }
        }
    }
}