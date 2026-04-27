package xyz.iwolfking.vhapi.api.loaders.gen.template;

import iskallia.vault.config.core.TemplatesConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.TemplateKey;
import iskallia.vault.core.vault.VaultRegistry;
import xyz.iwolfking.vhapi.api.events.VaultGenConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryAccessor;

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
            for(TemplateKey key : config.toRegistry().getKeys()) {
                VaultRegistry.TEMPLATE.register(key.with(Version.latest(), key.get(Version.v1_0)));
            }
        }
        ((KeyRegistryAccessor)VaultRegistry.TEMPLATE).setLocked(true);
    }
}
