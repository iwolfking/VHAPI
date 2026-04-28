package xyz.iwolfking.vhapi.api.events;


import net.minecraftforge.eventbus.api.Event;

/**
 * Contains events for when Vault Configs begin and finish loading (see ModConfigs.register() method from the_vault mod
 */
public class VaultConfigEvent extends Event {

    public static class Begin extends VaultConfigEvent {


        public Begin() {
        }
    }

    public static class End extends VaultConfigEvent {

        public End() {
        }
    }
}
