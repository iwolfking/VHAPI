package xyz.iwolfking.vhapi.api.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.loaders.workstation.lib.CustomVaultRecyclerConfig;
import xyz.iwolfking.vhapi.api.loaders.workstation.lib.GsonChanceItemStack;
import xyz.iwolfking.vhapi.api.loaders.workstation.lib.GsonRecyclerOutput;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class AbstractVaultRecyclerProvider extends AbstractVaultConfigDataProvider<AbstractVaultRecyclerProvider.Builder> {
    protected AbstractVaultRecyclerProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_recycler", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Recycler Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<CustomVaultRecyclerConfig> {
        public Map<ResourceLocation, GsonRecyclerOutput> OUTPUTS = new HashMap<>();

        public Builder() {
            super(CustomVaultRecyclerConfig::new);
        }

        public Builder add(ResourceLocation itemId, GsonRecyclerOutput output) {
            OUTPUTS.put(itemId, output);
            return this;
        }

        public Builder add(ResourceLocation itemId, GsonChanceItemStack output1, GsonChanceItemStack output2, GsonChanceItemStack output3) {
            return add(itemId, new GsonRecyclerOutput(output1, output2, output3));
        }

        @Override
        protected void configureConfig(CustomVaultRecyclerConfig config) {
            config.OUTPUTS = OUTPUTS;

        }

    }
}
