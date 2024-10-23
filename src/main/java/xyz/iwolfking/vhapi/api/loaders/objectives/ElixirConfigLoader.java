package xyz.iwolfking.vhapi.api.loaders.objectives;


import iskallia.vault.VaultMod;
import iskallia.vault.config.ElixirConfig;

import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.ElixirConfigAccessor;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ElixirConfigLoader extends VaultConfigProcessor<ElixirConfig> {




    public ElixirConfigLoader() {
        super(new ElixirConfig(), "objectives/elixir");
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
