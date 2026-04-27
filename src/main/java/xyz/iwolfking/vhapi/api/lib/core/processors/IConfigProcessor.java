package xyz.iwolfking.vhapi.api.lib.core.processors;

import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.events.VaultGenConfigEvent;

public interface IConfigProcessor {
    void afterConfigsLoad(VaultConfigEvent.End event);
    void afterGenConfigsRegistriesBuilt(VaultGenConfigEvent.RegistriesBuilt event);
    void processMatchingConfigs();
}
