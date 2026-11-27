package xyz.iwolfking.vhapi.api.loaders.challenge;

import iskallia.vault.config.ChallengeActionsConfig;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.core.vault.challenge.action.ChallengeAction;
import iskallia.vault.core.vault.challenge.action.PoolChallengeAction;
import iskallia.vault.core.vault.challenge.action.ReferenceChallengeAction;
import iskallia.vault.init.ModConfigs;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.ChallengeActionAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.LevelPoolAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.ReferenceChallengeActionConfigAccessor;

public class ChallengeActionsConfigLoader extends VaultConfigProcessor<ChallengeActionsConfig> {
    private final boolean mergePools;

    public ChallengeActionsConfigLoader() {
        this(true);
    }

    public ChallengeActionsConfigLoader(boolean mergePools) {
        super(new ChallengeActionsConfig(), "challenge/actions");
        this.mergePools = mergePools;
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        ChallengeActionsConfig baseConfig = ModConfigs.CHALLENGE_ACTIONS;
        if (baseConfig == null) {
            super.afterConfigsLoad(event);
            return;
        }

        if (baseConfig.values == null) {
            baseConfig.values = new HashMap<>();
        }

        for (ChallengeActionsConfig customConfig : this.CUSTOM_CONFIGS.values()) {
            if (customConfig.values == null || customConfig.values.isEmpty()) {
                continue;
            }
            for (Map.Entry<String, ChallengeAction<?>> entry : customConfig.values.entrySet()) {
                String id = entry.getKey();
                ChallengeAction<?> incoming = entry.getValue();
                if (id == null || incoming == null) {
                    continue;
                }

                ChallengeAction<?> existing = baseConfig.values.get(id);
                if (this.mergePools
                        && existing instanceof PoolChallengeAction existingPool
                        && incoming instanceof PoolChallengeAction incomingPool) {
                    mergePoolAction(existingPool, incomingPool);
                } else {
                    baseConfig.values.put(id, incoming);
                }
            }
        }

        super.afterConfigsLoad(event);
    }

    private void mergePoolAction(PoolChallengeAction baseAction, PoolChallengeAction customAction) {
        PoolChallengeAction.Config baseCfg = ((ChallengeActionAccessor<PoolChallengeAction.Config>) baseAction).getConfig();
        PoolChallengeAction.Config customCfg = ((ChallengeActionAccessor<PoolChallengeAction.Config>) customAction).getConfig();
        if (baseCfg == null || customCfg == null || customCfg.pools == null) {
            return;
        }

        for (PoolChallengeAction.Config.LevelPool customLevelPool : customCfg.pools) {
            int customLevel = ((LevelPoolAccessor) customLevelPool).vhapi$getLevel();
            PoolChallengeAction.Config.LevelPool matchingBase = findLevelPool(baseCfg.pools, customLevel);

            if (matchingBase == null) {
                baseCfg.pools.add(customLevelPool);
            } else {
                mergeWeightedEntries(matchingBase.pool, customLevelPool.pool);
            }
        }

        baseCfg.pools.sort(Comparator.comparingInt(lp -> ((LevelPoolAccessor) lp).vhapi$getLevel()));
    }

    private PoolChallengeAction.Config.LevelPool findLevelPool(
            java.util.List<PoolChallengeAction.Config.LevelPool> pools,
            int targetLevel
    ) {
        for (PoolChallengeAction.Config.LevelPool pool : pools) {
            if (((LevelPoolAccessor) pool).vhapi$getLevel() == targetLevel) {
                return pool;
            }
        }
        return null;
    }

    private void mergeWeightedEntries(
            WeightedList<ChallengeAction<?>> baseList,
            WeightedList<ChallengeAction<?>> customList
    ) {
        if (baseList == null || customList == null) {
            return;
        }

        for (Map.Entry<ChallengeAction<?>, Double> entry : customList.entrySet()) {
            ChallengeAction<?> action = entry.getKey();
            Double weight = entry.getValue();
            if (action != null && weight != null) {
                removeMatchingAction(baseList, action);
                baseList.put(action, weight);
            }
        }
    }

    private void removeMatchingAction(WeightedList<ChallengeAction<?>> list, ChallengeAction<?> target) {
        String targetRef = extractReferenceId(target);
        list.keySet().removeIf(existing -> {
            if (existing == null) {
                return false;
            }
            String existingRef = extractReferenceId(existing);
            if (targetRef != null && targetRef.equals(existingRef)) {
                return true;
            }
            return existing.equals(target);
        });
    }

    private String extractReferenceId(ChallengeAction<?> action) {
        if (action instanceof ReferenceChallengeAction refAction) {
            ReferenceChallengeAction.Config cfg = ((ChallengeActionAccessor<ReferenceChallengeAction.Config>) refAction).getConfig();
            return cfg != null ? ((ReferenceChallengeActionConfigAccessor) cfg).vhapi$getPath() : null;
        }
        return null;
    }
}