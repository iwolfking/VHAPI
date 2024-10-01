package xyz.iwolfking.vaultcrackerlib.mixin.configs.general;

import iskallia.vault.config.VaultStatsConfig;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.player.Completion;
import iskallia.vault.core.vault.stat.VaultChestType;
import iskallia.vault.util.VaultRarity;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.Map;

@Mixin(value = VaultStatsConfig.class, remap = false)
public class MixinVaultStatsConfig {
    @Shadow private Map<ResourceLocation, Float> blocksMined;
    @Shadow private Map<ResourceLocation, Float> mobsKilled;
    @Shadow private Map<String, Map<Completion, Float>> completion;


    @Shadow private Map<VaultChestType, Map<VaultRarity, Float>> chests;

    @Inject(method = "getBlocksMined", at = @At("HEAD"))
    public void getBlocksMined(CallbackInfoReturnable<Map<ResourceLocation, Float>> cir) {
        Patchers.VAULT_STATS_BLOCKS_PATCHER.doPatches(blocksMined);
    }

    @Inject(method = "getMobsKilled", at = @At("HEAD"))
    public void getMobsKilled(CallbackInfoReturnable<Map<ResourceLocation, Float>> cir) {
        Patchers.VAULT_STATS_BLOCKS_PATCHER.doPatches(mobsKilled);
    }

    @Inject(method = "getCompletion", at = @At("HEAD"))
    public void getCompletion(Vault vault, CallbackInfoReturnable<Map<Completion, Float>> cir) {
        Patchers.VAULT_STATS_COMPLETION_PATCHER.doPatches(completion);
    }

    @Inject(method = "getChests", at = @At("HEAD"))
    public void getChests(CallbackInfoReturnable<Map<VaultChestType, Map<VaultRarity, Float>>> cir) {
        Patchers.VAULT_STATS_CHEST_PATCHER.doPatches(chests);
    }
}
