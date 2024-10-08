package xyz.iwolfking.vaultcrackerlib.api.util.vhapi;

import xyz.iwolfking.vaultcrackerlib.VaultCrackerLib;

public class VHAPILoggerUtils {
    public static void debug(String message) {
        VaultCrackerLib.LOGGER.debug("[VHAPI] " + message);
    }

    public static void info(String message) {
        VaultCrackerLib.LOGGER.info("[VHAPI] " + message);
    }
}
