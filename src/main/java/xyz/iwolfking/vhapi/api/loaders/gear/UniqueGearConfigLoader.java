package xyz.iwolfking.vhapi.api.loaders.gear;

import iskallia.vault.config.UniqueGearConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.UniqueGearConfigAccessor;

public class UniqueGearConfigLoader extends VaultConfigProcessor<UniqueGearConfig> {
    public UniqueGearConfigLoader() {
        super(new UniqueGearConfig(), "gear/unique_gear");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            UniqueGearConfig config = CUSTOM_CONFIGS.get(key);
            if(key.getPath().contains("overwrite")) {
                ((UniqueGearConfigAccessor)ModConfigs.UNIQUE_GEAR).setEntries(((UniqueGearConfigAccessor)config).getEntries());
                ((UniqueGearConfigAccessor)ModConfigs.UNIQUE_GEAR).setPools(((UniqueGearConfigAccessor)config).getPools());
            }
            else {
                ((UniqueGearConfigAccessor)ModConfigs.UNIQUE_GEAR).getEntries().putAll(((UniqueGearConfigAccessor)config).getEntries());
                ((UniqueGearConfigAccessor)config).getPools().forEach(((resourceLocation, resourceLocationWeightedList) -> {
                    if(((UniqueGearConfigAccessor)ModConfigs.UNIQUE_GEAR).getPools().containsKey(resourceLocation)) {
                        resourceLocationWeightedList.forEach((resourceLocation1, aDouble) -> {
                            ((UniqueGearConfigAccessor)ModConfigs.UNIQUE_GEAR).getPools().get(resourceLocation).add(resourceLocation1, aDouble);
                        });
                    }
                    else {
                        ((UniqueGearConfigAccessor)ModConfigs.UNIQUE_GEAR).getPools().put(resourceLocation, resourceLocationWeightedList);
                    }
                }));
            }
        }
        super.afterConfigsLoad(event);
    }
}
