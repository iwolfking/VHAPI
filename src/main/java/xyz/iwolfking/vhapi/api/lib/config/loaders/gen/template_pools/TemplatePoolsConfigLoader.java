package xyz.iwolfking.vhapi.api.lib.config.loaders.gen.template_pools;

import iskallia.vault.config.core.TemplatePoolsConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.TemplatePoolKey;
import iskallia.vault.core.vault.VaultRegistry;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoaderRF;
import xyz.iwolfking.vhapi.api.lib.loaders.core.processors.IPreConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryAccessor;

public class TemplatePoolsConfigLoader extends VaultConfigDataLoaderRF<TemplatePoolsConfig> implements IPreConfigProcessor{
    public TemplatePoolsConfigLoader(String namespace) {
        super(new TemplatePoolsConfig(), "template_pools", namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        if(event.getType().equals(VaultConfigEvent.Type.GEN)) {
            ((KeyRegistryAccessor) VaultRegistry.TEMPLATE_POOL).setLocked(false);
            for(TemplatePoolsConfig config : this.CUSTOM_CONFIGS.values()) {
                for(TemplatePoolKey key : config.toRegistry().getKeys()) {
                    VaultRegistry.TEMPLATE_POOL.register(key.with(Version.latest(), key.get(Version.v1_0)));
                }
            }
            ((KeyRegistryAccessor)VaultRegistry.TEMPLATE_POOL).setLocked(true);
        }
    }


    @Override
    public void preProcessStep() {
        ((KeyRegistryAccessor) VaultRegistry.TEMPLATE_POOL).setLocked(false);
        for(TemplatePoolsConfig config : this.CUSTOM_CONFIGS.values()) {
            for(TemplatePoolKey key : config.toRegistry().getKeys()) {
                VaultRegistry.TEMPLATE_POOL.register(key.with(Version.latest(), key.get(Version.v1_0)));
            }
        }
        ((KeyRegistryAccessor)VaultRegistry.TEMPLATE_POOL).setLocked(true);
    }
}
