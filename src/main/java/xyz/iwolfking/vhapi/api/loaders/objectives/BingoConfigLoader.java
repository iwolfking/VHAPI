package xyz.iwolfking.vhapi.api.loaders.objectives;


import iskallia.vault.VaultMod;
import iskallia.vault.config.BingoConfig;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.task.BingoTask;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

import java.util.*;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BingoConfigLoader extends VaultConfigProcessor<BingoConfig> {



    public BingoConfigLoader() {
        super(new BingoConfig(), "objectives/bingo");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation configKey : this.CUSTOM_CONFIGS.keySet()) {
            BingoConfig config = this.CUSTOM_CONFIGS.get(configKey);
            Collection<LevelEntryList<BingoTask>> taskOverrides =  config.pools.values();
            if(!Objects.equals(configKey, VaultMod.id("bingo"))) {
                (ModConfigs.BINGO).pools.putAll((config.pools));
            }
            else {
                for(LevelEntryList<BingoTask> tasks : config.pools.values()) {
                    ModConfigs.BINGO.pools.values().add(tasks);
                }
            }
            super.afterConfigsLoad(event);
        }
    }

}
