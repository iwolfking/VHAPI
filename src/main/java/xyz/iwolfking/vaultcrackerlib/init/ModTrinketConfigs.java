package xyz.iwolfking.vaultcrackerlib.init;

import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

public class ModTrinketConfigs {
    public static void registerAllTrinketConfigAdditions() {
        for(ResourceLocation trinketId : Patchers.TRINKET_PATCHER.getAdditions().keySet()) {
            ModConfigs.TRINKET.TRINKETS.put(trinketId, Patchers.TRINKET_PATCHER.getAdditions().get(trinketId));
        }
    }
}
