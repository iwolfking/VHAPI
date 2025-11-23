package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.TooltipConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.loaders.workstation.lib.CustomVaultRecyclerConfig;
import xyz.iwolfking.vhapi.api.loaders.workstation.lib.GsonChanceItemStack;
import xyz.iwolfking.vhapi.api.loaders.workstation.lib.GsonRecyclerOutput;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractVaultRecyclerProvider extends AbstractVaultConfigDataProvider {
    protected AbstractVaultRecyclerProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_recycler");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Recycler Data Provider";
    }

    public static class VaultRecyclerConfigBuilder {
        public Map<ResourceLocation, GsonRecyclerOutput> OUTPUTS = new HashMap<>();

        public VaultRecyclerConfigBuilder add(ResourceLocation itemId, GsonRecyclerOutput output) {
            OUTPUTS.put(itemId, output);
            return this;
        }

        public VaultRecyclerConfigBuilder add(ResourceLocation itemId, GsonChanceItemStack output1, GsonChanceItemStack output2, GsonChanceItemStack output3) {
            return add(itemId, new GsonRecyclerOutput(output1, output2, output3));
        }


        public CustomVaultRecyclerConfig build() {
            CustomVaultRecyclerConfig newConfig = new CustomVaultRecyclerConfig();
            newConfig.OUTPUTS = OUTPUTS;
            return newConfig;
        }

    }
}
