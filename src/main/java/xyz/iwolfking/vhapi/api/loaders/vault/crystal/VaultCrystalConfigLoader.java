package xyz.iwolfking.vhapi.api.loaders.vault.crystal;

import iskallia.vault.config.VaultCrystalConfig;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.util.LevelEntryListHelper;
import xyz.iwolfking.vhapi.mixin.accessors.VaultCrystalConfigAccessor;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VaultCrystalConfigLoader extends VaultConfigProcessor<VaultCrystalConfig> {


    public VaultCrystalConfigLoader() {
        super(new VaultCrystalConfig(), "vault/crystal");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultCrystalConfig config : this.CUSTOM_CONFIGS.values()) {

            if(config.LAYOUTS != null) {
                LevelEntryListHelper.mergeList(
                        config.LAYOUTS,
                        ModConfigs.VAULT_CRYSTAL.LAYOUTS,
                        VaultCrystalConfig.LayoutEntry::getLevel,
                        (VaultCrystalConfig.LayoutEntry e) -> e.pool
                );
            }

            //Motes
            if(config.MOTES != null) {
                ModConfigs.VAULT_CRYSTAL.MOTES.clarityLevelCost = config.MOTES.clarityLevelCost;
                ModConfigs.VAULT_CRYSTAL.MOTES.purityLevelCost = config.MOTES.purityLevelCost;
                ModConfigs.VAULT_CRYSTAL.MOTES.sanctityLevelCost = config.MOTES.sanctityLevelCost;
            }

            //Modifier Stability
            if(config.MODIFIER_STABILITY != null) {
                ModConfigs.VAULT_CRYSTAL.MODIFIER_STABILITY.curseColor = config.MODIFIER_STABILITY.curseColor;
                ModConfigs.VAULT_CRYSTAL.MODIFIER_STABILITY.exhaustProbability = config.MODIFIER_STABILITY.exhaustProbability;
                ModConfigs.VAULT_CRYSTAL.MODIFIER_STABILITY.instabilityPerCraft = config.MODIFIER_STABILITY.instabilityPerCraft;
            }

            if (config.THEMES != null) {
                LevelEntryListHelper.mergeMap(
                        config.THEMES,
                        ModConfigs.VAULT_CRYSTAL.THEMES,
                        themeEntry -> themeEntry.level,
                        (existing, incoming, list) -> {
                            // Merge seasonalWeights if both exist
                            if (existing.seasonalWeights != null && incoming.seasonalWeights != null) {
                                incoming.seasonalWeights.forEach((k, v) -> {
                                    existing.seasonalWeights.merge(k, v, (oldVal, newVal) -> {
                                        // merge the DateRangeWeight lists
                                        oldVal.ranges.addAll(newVal.ranges);
                                        return oldVal;
                                    });
                                });
                            }

                            // Merge pool if both exist
                            if (existing.pool != null && incoming.pool != null) {
                                existing.pool.putAll(incoming.pool);
                            }
                        }
                );
            }


//            //Themes
//            if(config.THEMES != null) {
//                for(ResourceLocation key : config.THEMES.keySet()) {
//                    LevelEntryList<VaultCrystalConfig.ThemeEntry> levelEntryList = config.THEMES.get(key);
//                    if(ModConfigs.VAULT_CRYSTAL.THEMES.containsKey(key)) {
//                        levelEntryList.forEach(themeEntry -> {
//                            ModConfigs.VAULT_CRYSTAL.THEMES.get(key)
//                                    .getForLevel(themeEntry.level)
//                                    .ifPresentOrElse(themeEntry1 -> {
//                                        if(themeEntry1.seasonalWeights != null && themeEntry.seasonalWeights != null) {
//                                            themeEntry1.seasonalWeights.putAll(levelEntryList.getForLevel(themeEntry.level).get().seasonalWeights);
//                                        }
//                                        else if(themeEntry1.pool != null && !themeEntry1.pool.isEmpty()) {
//                                            themeEntry1.pool.putAll(levelEntryList.getForLevel(themeEntry.level).get().pool);
//                                        }
//                                    }, () -> ModConfigs.VAULT_CRYSTAL.THEMES.get(key).add(themeEntry));
//                        });
//                    }
//                    else {
//                        ModConfigs.VAULT_CRYSTAL.THEMES.put(key, levelEntryList);
//                    }
//                }
//            }

            if(config.OBJECTIVES != null) {
                LevelEntryListHelper.mergeMap(
                        config.OBJECTIVES,
                        ModConfigs.VAULT_CRYSTAL.OBJECTIVES,
                        VaultCrystalConfig.ObjectiveEntry::getLevel,
                        objectiveEntry -> objectiveEntry.pool
                );
            }

            if(config.TIMES != null) {
                LevelEntryListHelper.mergeMap(
                        config.TIMES,
                        ModConfigs.VAULT_CRYSTAL.TIMES,
                        VaultCrystalConfig.TimeEntry::getLevel,
                        timeEntry -> timeEntry.pool
                );
            }

            if(((VaultCrystalConfigAccessor)config).getSeals() != null) {
                LevelEntryListHelper.mergeMap(
                        ((VaultCrystalConfigAccessor)config).getSeals(),
                        ((VaultCrystalConfigAccessor)ModConfigs.VAULT_CRYSTAL).getSeals(),
                        VaultCrystalConfig.SealEntry::getLevel,
                        (existing, src, list) -> list.add(src)
                );
            }

        }
        super.afterConfigsLoad(event);
    }

    public <T> WeightedList<?> getPool(T entry) {
        if(entry instanceof VaultCrystalConfig.ObjectiveEntry objectiveEntry) {
            return objectiveEntry.pool;
        }

        return WeightedList.empty();
    }
}
