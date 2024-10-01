package xyz.iwolfking.vaultcrackerlib.mixin.configs.misc;

import iskallia.vault.config.VaultMetaChestConfig;
import iskallia.vault.util.VaultRarity;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.Map;

@Mixin(value = VaultMetaChestConfig.class, remap = false)
public class MixinVaultMetaChestConfig {
    @Shadow @Final private Map<Block, Map<VaultRarity, Double>> catalystChances;

    @Inject(method = "getCatalystChance", at = @At("HEAD"))
    private void patchCatalystChances(Block block, VaultRarity rarity, CallbackInfoReturnable<Double> cir) {
        Patchers.VAULT_CHEST_CATALYST_CHANCE_PATCHER.doPatches(this.catalystChances);
    }
}
