package xyz.iwolfking.vhapi.api.events.vault;

import iskallia.vault.core.event.Event;

import java.util.ArrayList;
import java.util.List;

public class VaultEvents {
    public static final List<Event<?, ?>> REGISTRY = new ArrayList();


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
