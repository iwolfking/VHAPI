package xyz.iwolfking.vaultcrackerlib.mixin;

import iskallia.vault.init.ModConfigs;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.BasicPatcher;
import xyz.iwolfking.vaultcrackerlib.init.ModTrinketConfigs;


@Mixin(value = ModConfigs.class, remap = false)
public class MixinModConfigs {

    @Inject(method = "register", at = @At("TAIL"))
    private static void resetPatchedStateForPatchers(CallbackInfo ci) {
        //Reset state of all patchers, so they will patch the configs again.
        for(BasicPatcher patcher : Patchers.PATCHERS) {
            patcher.setPatched(false);
        }

        //Add additional trinkets to Trinket config. Note: adding trinkets does not fully work atm
        ModTrinketConfigs.registerAllTrinketConfigAdditions();

        //Patch "Box" configs
        Patchers.MYSTERY_BOX_PATCHER.doPatches(ModConfigs.MYSTERY_BOX.POOL);
        Patchers.MYSTERY_EGG_PATCHER.doPatches(ModConfigs.MYSTERY_EGG.POOL);
        Patchers.MYSTERY_EGG_HOSTILE_PATCHER.doPatches(ModConfigs.MYSTERY_HOSTILE_EGG.POOL);
        Patchers.MOD_BOX_PATCHER.doPatches(ModConfigs.MOD_BOX.POOL);

        //Entity Champion Chances
        Patchers.ENTITY_CHAMPION_CHANCE_PATCHER.doPatches(ModConfigs.CHAMPIONS.entityChampionChance);

        //Vault Modifiers
        Patchers.VAULT_MODIFIER_POOLS_PATCHER.doPatches(ModConfigs.VAULT_MODIFIER_POOLS.pools);
    }

}
