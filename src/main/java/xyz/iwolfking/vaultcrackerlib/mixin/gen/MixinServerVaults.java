package xyz.iwolfking.vaultcrackerlib.mixin.gen;

import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.world.storage.ThreadingMode;
import iskallia.vault.core.world.storage.VirtualWorld;
import iskallia.vault.nbt.VListNBT;
import iskallia.vault.world.data.ServerVaults;
import iskallia.vault.world.data.VaultSnapshots;
import iskallia.vault.world.data.VirtualWorlds;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ServerVaults.class, remap = false)
public abstract class MixinServerVaults {


    /**
     * @author iwolfking
     * @reason Redirect onVaultStarted
     */
    @Redirect(method = "add", at = @At(value = "INVOKE", target = "Liskallia/vault/world/data/VaultSnapshots;onVaultStarted(Liskallia/vault/core/vault/Vault;)V"))
    private static void redirectOnVaultStarted(Vault vault) {

    }
}
