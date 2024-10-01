package xyz.iwolfking.vaultcrackerlib.mixin.configs.crystal;

import iskallia.vault.config.VaultCrystalConfig;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.item.crystal.CrystalData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.Map;

@Mixin(value = VaultCrystalConfig.class, remap = false)
public class MixinVaultCrystalConfig {
    @Shadow private Map<ResourceLocation, LevelEntryList<VaultCrystalConfig.SealEntry>> SEALS;

    @Inject(method = "applySeal", at = @At("HEAD"))
    public void applySeal(ItemStack input, ItemStack seal, ItemStack output, CrystalData crystal, CallbackInfoReturnable<Boolean> cir) {
        Patchers.CRYSTAL_SEALS_PATCHER.doPatches(SEALS);
    }
}
