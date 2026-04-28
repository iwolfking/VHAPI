package xyz.iwolfking.vhapi.api.loaders.gen.template;

import iskallia.vault.config.core.TemplatesConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.TemplateKey;
import iskallia.vault.core.data.key.registry.KeyRegistry;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.core.world.template.Template;
import xyz.iwolfking.vhapi.api.events.VaultGenConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryConfigAccessor;

public class TemplatesConfigLoader extends VaultConfigProcessor<TemplatesConfig>{
    public TemplatesConfigLoader() {
        super(new TemplatesConfig(), "templates");
    }

    @Override
    public void afterGenConfigsRegistriesBuilt(VaultGenConfigEvent.RegistriesBuilt event) {
        registerTemplates();
        super.afterGenConfigsRegistriesBuilt(event);
    }

    private void registerTemplates() {
        ((KeyRegistryAccessor) VaultRegistry.TEMPLATE).setLocked(false);
        for(TemplatesConfig config : this.CUSTOM_CONFIGS.values()) {
            for(TemplateKey key : toRegistry(config).getKeys()) {
                VaultRegistry.TEMPLATE.register(key.with(Version.latest(), key.get(Version.v1_0)));
            }
        }
        ((KeyRegistryAccessor)VaultRegistry.TEMPLATE).setLocked(true);
    }

    public KeyRegistry<TemplateKey, Template, Version> toRegistry(TemplatesConfig config) {
        KeyRegistry<TemplateKey, Template, Version> registry = config.create();
        ((KeyRegistryConfigAccessor<TemplateKey>)config).getKeys().forEach(registry::register);
        return registry;
    }
}
