package xyz.iwolfking.vaultcrackerlib.mixin.configs.misc;

import iskallia.vault.config.MenuPlayerStatDescriptionConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.LinkedHashMap;
import java.util.TreeMap;

@Mixin(value = MenuPlayerStatDescriptionConfig.class, remap = false)
public class MixinMenuPlayerStatDescriptionConfig {
    @Shadow private LinkedHashMap<String, String> VAULT_STATS_DESCRIPTIONS;

    @Shadow private LinkedHashMap<String, String> PROMINENT_STATS_DESCRIPTIONS;

    @Shadow private TreeMap<ResourceLocation, String> MOD_GEAR_ATTRIBUTE_DESCRIPTIONS;

    @Inject(method = "getVaultStatDescriptionFor", at = @At("HEAD"))
    private void addNewVaultStatDescriptions(String key, CallbackInfoReturnable<String> cir) {
        Patchers.MENU_PLAYER_STATS_VAULT_PATCHER.doPatches(this.VAULT_STATS_DESCRIPTIONS);
    }

    @Inject(method = "getProminentStatDescriptionFor", at = @At("HEAD"))
    private void addNewProminentStatDescriptions(String key, CallbackInfoReturnable<String> cir) {
        Patchers.MENU_PLAYER_STAT_PROMINENT_PATCHER.doPatches(this.PROMINENT_STATS_DESCRIPTIONS);
    }

    @Inject(method = "getModGearAttributeDescriptionFor", at = @At("HEAD"))
    private void addNewGearStatDescriptions(ResourceLocation resourceLocation, CallbackInfoReturnable<String> cir) {
        Patchers.MENU_PLAYER_STAT_GEAR_PATCHER.doPatches(this.MOD_GEAR_ATTRIBUTE_DESCRIPTIONS);
    }
}
