package xyz.iwolfking.vhapi.api.loaders.gen.template_pools;

import iskallia.vault.config.core.TemplatePoolsConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.TemplatePoolKey;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.core.world.template.data.TemplatePool;
import iskallia.vault.util.data.WeightedList;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.lib.core.processors.IPreProcessor;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryAccessor;

public class TemplatePoolsLoader extends VaultConfigProcessor<TemplatePoolsConfig> implements IPreProcessor {
    public TemplatePoolsLoader() {
        super(new TemplatePoolsConfig(), "template_pools");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        if(event.getType().equals(VaultConfigEvent.Type.GEN)) {
            registerTemplatePools();
        }
        super.afterConfigsLoad(event);
    }

    @Override
    public void preProcessStep() {
        registerTemplatePools();
    }

    private void registerTemplatePools() {
        ((KeyRegistryAccessor) VaultRegistry.TEMPLATE_POOL).setLocked(false);
        for(TemplatePoolsConfig config : this.CUSTOM_CONFIGS.values()) {
            for(TemplatePoolKey key : config.toRegistry().getKeys()) {
                if(key.getId().getPath().endsWith("_merge")) {
                    TemplatePoolKey existingKey = VaultRegistry.TEMPLATE_POOL.getKey(ResourceLocUtils.removeSuffixFromId("_merge", key.getId()));
                    if (existingKey == null) {
                        VHAPI.LOGGER.error("Failed to merge \"{}\", existing template pool not found",  key.getId().getPath());
                        continue;
                    }
                    TemplatePool existingPool = existingKey.get(Version.latest());
                    TemplatePool mergePool = key.get(Version.v1_0);
                    mergePool.getChildren().forEach((o, aDouble) -> {
                        existingPool.getChildren().add(o, aDouble);
                    });

                    VaultRegistry.TEMPLATE_POOL.register(existingKey.with(Version.latest(), existingPool));
                }
                else {
                    VaultRegistry.TEMPLATE_POOL.register(key.with(Version.latest(), key.get(Version.v1_0)));
                }
            }
        }
        ((KeyRegistryAccessor)VaultRegistry.TEMPLATE_POOL).setLocked(true);
    }
}
