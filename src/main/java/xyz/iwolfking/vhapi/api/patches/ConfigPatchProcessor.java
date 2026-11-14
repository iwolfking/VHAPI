package xyz.iwolfking.vhapi.api.patches;

import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.events.VaultGenConfigEvent;
import xyz.iwolfking.vhapi.api.lib.core.processors.IConfigProcessor;

/** Gathers the datapack config patches before the_vault re-reads its config files. */
public class ConfigPatchProcessor implements IConfigProcessor {
    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
    }

    @Override
    public void afterGenConfigsRegistriesBuilt(VaultGenConfigEvent.RegistriesBuilt event) {
    }

    @Override
    public void processMatchingConfigs() {
        ConfigPatches.reload(LoaderRegistry.VHAPI_DATA_LOADER.JSON_DATA);
    }
}
