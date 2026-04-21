package xyz.iwolfking.vhapi.api.loaders.greed;

import iskallia.vault.config.greed.GreedCauldronConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.DemandEntryAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.GreedCauldronConfigAccessor;
import java.util.ArrayList;
import java.util.List;


public class GreedCauldronConfigLoader extends VaultConfigProcessor<GreedCauldronConfig> {
    public GreedCauldronConfigLoader() {
        super(new GreedCauldronConfig(), "greed/cauldron");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, greedCauldronConfig) -> {
            if(resourceLocation.getPath().contains("remove")) {
                List<GreedCauldronConfig.DemandEntry> demandsToRemove = new ArrayList<>();
                ((GreedCauldronConfigAccessor)ModConfigs.GREED_CAULDRON).getDemands().forEach(demandEntry -> {
                    ((GreedCauldronConfigAccessor)greedCauldronConfig).getDemands().forEach(demandEntry2 -> {
                        if(((DemandEntryAccessor)demandEntry).getItem().equals(((DemandEntryAccessor)demandEntry2).getItem())) {
                            demandsToRemove.add(demandEntry);
                        }
                    });
                });
                ((GreedCauldronConfigAccessor) ModConfigs.GREED_CAULDRON).getDemands().removeAll(demandsToRemove);
            }
            else if(resourceLocation.getPath().contains("replace")) {
                List<GreedCauldronConfig.DemandEntry> demandsToRemove = new ArrayList<>();
                List<GreedCauldronConfig.DemandEntry> demandsToAdd = new ArrayList<>();
                ((GreedCauldronConfigAccessor)ModConfigs.GREED_CAULDRON).getDemands().forEach(demandEntry -> {
                    ((GreedCauldronConfigAccessor)greedCauldronConfig).getDemands().forEach(demandEntry2 -> {
                        if(((DemandEntryAccessor)demandEntry).getItem().equals(((DemandEntryAccessor)demandEntry2).getItem())) {
                            demandsToRemove.add(demandEntry);
                            demandsToAdd.add(demandEntry2);
                        }
                    });
                });
                ((GreedCauldronConfigAccessor) ModConfigs.GREED_CAULDRON).getDemands().removeAll(demandsToRemove);
                ((GreedCauldronConfigAccessor) ModConfigs.GREED_CAULDRON).getDemands().addAll(demandsToAdd);
            }
            else {
                ((GreedCauldronConfigAccessor)ModConfigs.GREED_CAULDRON).getDemands().addAll(((GreedCauldronConfigAccessor)greedCauldronConfig).getDemands());
            }

        });

        super.afterConfigsLoad(event);
    }
}

