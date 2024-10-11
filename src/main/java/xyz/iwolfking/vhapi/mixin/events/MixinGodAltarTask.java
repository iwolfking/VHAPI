package xyz.iwolfking.vhapi.mixin.events;

import iskallia.vault.block.base.GodAltarTileEntity;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.influence.VaultGod;
import iskallia.vault.gear.charm.CharmHelper;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.task.GodAltarTask;
import iskallia.vault.task.TaskContext;
import iskallia.vault.task.source.EntityTaskSource;
import iskallia.vault.task.source.TaskSource;
import iskallia.vault.world.data.PlayerReputationData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vhapi.api.events.vault.VaultEvents;

import java.util.Iterator;
import java.util.UUID;

@Mixin(value = GodAltarTask.class, remap = false)
public abstract class MixinGodAltarTask {

    @Shadow private BlockPos pos;

    @Shadow private UUID altarUuid;

    @Shadow public abstract VaultGod getGod();

    @Inject(method = "onSucceed", at = @At("HEAD"))
    public void onSucceed(Vault vault, TaskContext context, CallbackInfo ci) {
        TaskSource source = context.getSource();
        if(source instanceof EntityTaskSource entitySource) {
            Iterator<Player> players = entitySource.getEntities(Player.class).iterator();
            while(players.hasNext()) {
                Player player = players.next();
                VaultEvents.GOD_ALTAR_COMPLETED.invoke(player.getLevel(), player, this.pos, this.altarUuid, this.getGod());
            }
        }
    }
}
