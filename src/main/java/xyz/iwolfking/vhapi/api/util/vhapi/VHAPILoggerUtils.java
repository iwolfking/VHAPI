package xyz.iwolfking.vhapi.api.util.vhapi;

import xyz.iwolfking.vhapi.VHAPI;

public class VHAPILoggerUtils {
    public static void debug(String message) {
        VHAPI.LOGGER.debug("[VHAPI] " + message);
    }

    public static void info(String message) {
        VHAPI.LOGGER.info("[VHAPI] " + message);
    }
}
