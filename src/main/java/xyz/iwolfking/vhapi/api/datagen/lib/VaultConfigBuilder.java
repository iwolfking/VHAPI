package xyz.iwolfking.vhapi.api.datagen.lib;

import iskallia.vault.config.Config;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;

import java.util.function.Supplier;

public abstract class VaultConfigBuilder<C extends Config> implements AbstractVaultConfigDataProvider.Buildable<C> {

    protected final C config;

    public VaultConfigBuilder(Supplier<C> factory) {
        this.config = factory.get();
    }

    protected abstract void configureConfig(C config);

    public C build() {
        configureConfig(config);
        return config;
    }
}
