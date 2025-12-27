package xyz.iwolfking.vhapi.api.events;


import iskallia.vault.config.Config;
import iskallia.vault.init.ModConfigs;
import net.minecraftforge.eventbus.api.Event;

/**
 * Contains events for when Vault Configs begin and finish loading (see ModConfigs.register() method from the_vault mod
 */
public class VaultConfigEvent extends Event {

    public enum Type {
        GEN,
        NORMAL
    }

    public static class Begin extends VaultConfigEvent {
        public Type getType() {
            return type;
        }

        private final Type type;

        public Begin(Type type) {
            this.type = type;
        }
    }

    public static class Read<T extends Config> extends VaultConfigEvent {

        public T getConfig() {
            return config;
        }

        private final T config;

        public Read(T config) {
            this.config = config;
        }
    }

    public static class End extends VaultConfigEvent {
        public Type getType() {
            return type;
        }

        private final Type type;

        public End(Type type) {
            this.type = type;
        }
    }
}
