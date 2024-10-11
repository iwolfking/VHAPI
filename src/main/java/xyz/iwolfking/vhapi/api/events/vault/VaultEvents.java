package xyz.iwolfking.vhapi.api.events.vault;

import iskallia.vault.core.event.CommonEvents;
import iskallia.vault.core.event.Event;
import iskallia.vault.core.event.common.AltarProgressEvent;
import xyz.iwolfking.vhapi.api.events.vault.lib.GodAltarCompletedEvent;

import java.util.ArrayList;
import java.util.List;

public class VaultEvents {
    public static final List<Event<?, ?>> REGISTRY = new ArrayList();

    public static final GodAltarCompletedEvent GOD_ALTAR_COMPLETED = (GodAltarCompletedEvent) register(new GodAltarCompletedEvent());

    public VaultEvents() {
    }

    public static void release(Object reference) {
        REGISTRY.forEach((event) -> {
            event.release(reference);
        });
    }

    private static <T extends Event<?, ?>> T register(T event) {
        REGISTRY.add(event);
        return event;
    }

    public static void init() {
    }
}
