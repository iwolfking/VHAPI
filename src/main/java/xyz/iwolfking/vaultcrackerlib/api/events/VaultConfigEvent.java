package xyz.iwolfking.vaultcrackerlib.api.events;


import net.minecraftforge.eventbus.api.Event;

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
