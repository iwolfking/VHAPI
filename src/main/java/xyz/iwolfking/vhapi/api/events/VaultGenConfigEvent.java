package xyz.iwolfking.vhapi.api.events;


import iskallia.vault.config.Config;
import net.minecraftforge.eventbus.api.Event;

/**
 * Contains events for when Vault Configs begin and finish loading (see ModConfigs.register() method from the_vault mod
 */
public class VaultGenConfigEvent extends Event {

    public static class Begin extends VaultGenConfigEvent {


        public Begin() {
        }
    }

    public static class RegistriesBuilt extends VaultGenConfigEvent {

        public RegistriesBuilt() {
        }
    }

    public static class End extends VaultGenConfigEvent {

        public End() {
        }
    }
}
