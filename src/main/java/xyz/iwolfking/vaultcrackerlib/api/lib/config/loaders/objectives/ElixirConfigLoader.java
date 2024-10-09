package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives;


import com.google.gson.JsonElement;
import iskallia.vault.VaultMod;
import iskallia.vault.config.Config;
import iskallia.vault.config.ElixirConfig;

import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.fml.common.Mod;

import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.CustomVaultConfigReader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.ElixirConfigAccessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ElixirConfigLoader extends VaultConfigDataLoader<ElixirConfig> {




    public ElixirConfigLoader(String namespace) {
        super(new ElixirConfig(), "objectives/elixir", new HashMap<>(), namespace);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        dataMap.forEach((resourceLocation, jsonElement) -> {
            CustomVaultConfigReader<Config> configReader = new CustomVaultConfigReader<>();
            Config config = configReader.readCustomConfig(resourceLocation.getPath(), jsonElement, ElixirConfig.class);
            if(config instanceof ElixirConfig elixirConfig) {
                CUSTOM_CONFIGS.put(new ResourceLocation(getNamespace(), resourceLocation.getPath()), elixirConfig);
            }
        });

        performFinalActions();
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation configKey : this.CUSTOM_CONFIGS.keySet()) {
            ElixirConfig config = this.CUSTOM_CONFIGS.get(configKey);
            if(!Objects.equals(configKey, VaultMod.id("elixir"))) {
                ((ElixirConfigAccessor)ModConfigs.ELIXIR).getElixirToSize().putAll(((ElixirConfigAccessor)config).getElixirToSize());
                ((ElixirConfigAccessor)ModConfigs.ELIXIR).getMobGroups().putAll(((ElixirConfigAccessor)config).getMobGroups());
                ((ElixirConfigAccessor)ModConfigs.ELIXIR).getEntries().addAll(((ElixirConfigAccessor)config).getEntries());
            }
            else {
                ((ElixirConfigAccessor)ModConfigs.ELIXIR).setElixirToSize(((ElixirConfigAccessor)config).getElixirToSize());
                ((ElixirConfigAccessor)ModConfigs.ELIXIR).setMobGroups(((ElixirConfigAccessor)config).getMobGroups());
                ((ElixirConfigAccessor)ModConfigs.ELIXIR).setEntries(((ElixirConfigAccessor)config).getEntries());
            }

        }
    }
}
