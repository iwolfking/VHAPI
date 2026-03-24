package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.VaultCrystalCatalystConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.loaders.vault.crystal.VaultCrystalCatalystModifiersConfigLoader;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultCrystalCatalystConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractCatalystModifiersPoolProvider extends AbstractVaultConfigDataProvider<AbstractCatalystModifiersPoolProvider.Builder> {
    protected AbstractCatalystModifiersPoolProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/crystal_catalyst_modifiers", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Catalyst Pool Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultCrystalCatalystConfig> {
        Map<String, VaultCrystalCatalystConfig.ModifierPool> entries = new HashMap<>();

        public Builder() {
            super(VaultCrystalCatalystConfig::new);
        }

        public Builder add(String poolName, Consumer<BasicListBuilder<ResourceLocation>> modifierIdsConsumer) {
            BasicListBuilder<ResourceLocation> builder = new BasicListBuilder<>();
            modifierIdsConsumer.accept(builder);
            entries.put(poolName, new VaultCrystalCatalystConfig.ModifierPool(builder.build()));
            return this;
        }

        @Override
        protected void configureConfig(VaultCrystalCatalystConfig config) {
            ((VaultCrystalCatalystConfigAccessor)config).getModifierPools().putAll(entries);
        }
    }
}
