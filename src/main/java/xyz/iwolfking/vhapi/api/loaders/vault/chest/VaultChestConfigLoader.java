package xyz.iwolfking.vhapi.api.loaders.vault.chest;

import iskallia.vault.config.VaultChestConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.util.VaultRarity;
import iskallia.vault.world.vault.chest.ExplosionEffect;
import iskallia.vault.world.vault.chest.GatewayTrapEffect;
import iskallia.vault.world.vault.chest.ManaStealerTrapEffect;
import iskallia.vault.world.vault.chest.MinefieldTrapEffect;
import iskallia.vault.world.vault.chest.MobTrapEffect;
import iskallia.vault.world.vault.chest.PotionCloudEffect;
import iskallia.vault.world.vault.chest.TeleportTrapEffect;
import iskallia.vault.world.vault.chest.VaultChestEffect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.util.LevelEntryListHelper;

public class VaultChestConfigLoader extends VaultConfigProcessor<VaultChestConfig> {
    public VaultChestConfigLoader() {
        super(new VaultChestConfig("vault_chest"), "vault/chest");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        VaultChestConfig baseConfig = ModConfigs.VAULT_CHEST;
        if (baseConfig == null) {
            super.afterConfigsLoad(event);
            return;
        }

        this.CUSTOM_CONFIGS.forEach((resourceLocation, customConfig) -> {
            if (customConfig.RARITY_DISTRIBUTION != null && !customConfig.RARITY_DISTRIBUTION.isEmpty()) {
                if (baseConfig.RARITY_DISTRIBUTION == null) {
                    baseConfig.RARITY_DISTRIBUTION = new HashMap<>();
                }
                for (Map.Entry<VaultRarity, Double> entry : customConfig.RARITY_DISTRIBUTION.entrySet()) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        baseConfig.RARITY_DISTRIBUTION.put(entry.getKey(), entry.getValue());
                    }
                }
            }

            baseConfig.MOB_TRAP_EFFECTS = mergeEffectsByName(baseConfig.MOB_TRAP_EFFECTS, customConfig.MOB_TRAP_EFFECTS);
            baseConfig.EXPLOSION_EFFECTS = mergeEffectsByName(baseConfig.EXPLOSION_EFFECTS, customConfig.EXPLOSION_EFFECTS);
            baseConfig.POTION_CLOUD_EFFECTS = mergeEffectsByName(baseConfig.POTION_CLOUD_EFFECTS, customConfig.POTION_CLOUD_EFFECTS);
            baseConfig.TELEPORT_TRAP_EFFECTS = mergeEffectsByName(baseConfig.TELEPORT_TRAP_EFFECTS, customConfig.TELEPORT_TRAP_EFFECTS);
            baseConfig.MANA_STEALER_TRAP_EFFECTS = mergeEffectsByName(baseConfig.MANA_STEALER_TRAP_EFFECTS, customConfig.MANA_STEALER_TRAP_EFFECTS);
            baseConfig.MINEFIELD_TRAP_EFFECTS = mergeEffectsByName(baseConfig.MINEFIELD_TRAP_EFFECTS, customConfig.MINEFIELD_TRAP_EFFECTS);
            baseConfig.GATEWAY_TRAP_EFFECTS = mergeEffectsByName(baseConfig.GATEWAY_TRAP_EFFECTS, customConfig.GATEWAY_TRAP_EFFECTS);

            if (customConfig.LEVELS != null && !customConfig.LEVELS.isEmpty()) {
                LevelEntryListHelper.mergeList(
                        customConfig.LEVELS,
                        baseConfig.LEVELS,
                        level -> level.level,
                        (existing, src, list) -> {
                            if (src.probability > 0) {
                                existing.probability = src.probability;
                            }
                            if (existing.pool != null && src.pool != null) {
                                existing.pool.putAll(src.pool);
                            }
                        }
                );
            }
        });

        super.afterConfigsLoad(event);
    }

    private static <T extends VaultChestEffect> List<T> mergeEffectsByName(
            @Nullable List<T> baseList,
            @Nullable List<T> customList
    ) {
        if (customList == null || customList.isEmpty()) {
            return baseList != null ? new ArrayList<>(baseList) : new ArrayList<>();
        }

        List<T> merged = baseList != null ? new ArrayList<>(baseList) : new ArrayList<>();
        Map<String, T> byName = new HashMap<>();
        for (T effect : customList) {
            if (effect != null && effect.getName() != null) {
                byName.put(effect.getName(), effect);
            }
        }

        merged.removeIf(existing -> existing != null && existing.getName() != null && byName.containsKey(existing.getName()));
        merged.addAll(customList);
        return merged;
    }
}