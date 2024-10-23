package xyz.iwolfking.vhapi.api.lib.core.processors;

import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;

public interface IConfigProcessor {
    void afterConfigsLoad(VaultConfigEvent.End event);
    void processMatchingConfigs();
}
