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

        private Type type;

        public Begin(Type type) {
            this.type = type;
        }
    }

    public static class End extends VaultConfigEvent {
        public Type getType() {
            return type;
        }

        private Type type;

        public End(Type type) {
            this.type = type;
        }
    }
}
