package xyz.iwolfking.vhapi.api.util;

import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;

public class RemasteredHelper {
    public static boolean isRemasteredVaultMod() {
        ModFileInfo vaultModInfo = LoadingModList.get().getModFileById("the_vault");
        return vaultModInfo.getFile().getFileName().contains("remastered");
    }
}
