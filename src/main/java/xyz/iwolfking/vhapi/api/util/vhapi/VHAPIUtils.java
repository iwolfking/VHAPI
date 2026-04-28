package xyz.iwolfking.vhapi.api.util.vhapi;

import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.LoaderRegistry;

public class VHAPIUtils {
    public static void loadConfigs() {
        LoaderRegistry.VHAPI_DATA_LOADER.gatherConfigsToProcessors();
        LoaderRegistry.VHAPI_DATA_LOADER.gatherGenConfigsToProcessors();
    }

    public static void purgeConfigs() {
        LoaderRegistry.VHAPI_DATA_LOADER.JSON_DATA.clear();
    }
}
