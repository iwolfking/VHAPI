package xyz.iwolfking.vhapi.api.lib.core.processors;

import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;

public interface IPreConfigProcessor {
    void beforeConfigsLoad(VaultConfigEvent.Begin event);
}
