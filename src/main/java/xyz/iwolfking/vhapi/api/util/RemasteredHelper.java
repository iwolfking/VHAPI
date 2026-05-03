package xyz.iwolfking.vhapi.api.util;

public class RemasteredHelper {
    public static boolean isRemasteredVaultMod() {
        try {
            Class.forName("iskallia.vault.aura.ActiveAura");
            return false;
        } catch (ClassNotFoundException e) {
            return true;
        }
    }
}
