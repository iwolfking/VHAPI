package xyz.iwolfking.vhapi.mixin.extension_handling;

import iskallia.vault.client.gui.screen.player.StatisticsElementContainerScreenData;
import iskallia.vault.client.gui.screen.player.stats.PlayerStatEntry;
import iskallia.vault.core.vault.stat.StatTotals;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.registry.PlayerStatEntriesRegistry;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = StatisticsElementContainerScreenData.class, remap = false)
public abstract class MixinStatisticsElementContainerScreenData {

    @Shadow
    protected Player player;

    @Inject(method = "getStatListPlayer", at = @At("RETURN"))
    private void vhapi$appendPlayerStats(CallbackInfoReturnable<List<PlayerStatEntry>> cir) {
        PlayerStatEntriesRegistry.INSTANCE.appendPlayerEntries(this.player, cir.getReturnValue());
    }

    @Inject(method = "getStatListVault", at = @At("RETURN"), cancellable = true)
    private void vhapi$appendVaultStats(StatTotals data, CallbackInfoReturnable<List<PlayerStatEntry>> cir) {
        List<PlayerStatEntry> combined = new ArrayList<>(cir.getReturnValue());
        PlayerStatEntriesRegistry.INSTANCE.appendVaultEntries(data, combined);
        cir.setReturnValue(combined);
    }
}