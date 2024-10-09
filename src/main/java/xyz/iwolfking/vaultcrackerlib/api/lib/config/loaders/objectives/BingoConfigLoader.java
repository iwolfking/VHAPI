package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives;


import com.google.gson.JsonElement;
import iskallia.vault.VaultMod;
import iskallia.vault.config.BingoConfig;
import iskallia.vault.config.Config;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.task.BingoTask;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.CustomVaultConfigReader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.*;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BingoConfigLoader extends VaultConfigDataLoader<BingoConfig> {



    public BingoConfigLoader(String namespace) {
        super(new BingoConfig(), "objectives/bingo", new HashMap<>(), namespace);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        dataMap.forEach((resourceLocation, jsonElement) -> {
            CustomVaultConfigReader<Config> configReader = new CustomVaultConfigReader<>();
            Config config = configReader.readCustomConfig(resourceLocation.getPath(), jsonElement, BingoConfig.class);
            if(config instanceof BingoConfig bingoConfig) {
                CUSTOM_CONFIGS.put(new ResourceLocation(getNamespace(), resourceLocation.getPath()), bingoConfig);
            }
        });

        performFinalActions();
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

        }
    }

}
