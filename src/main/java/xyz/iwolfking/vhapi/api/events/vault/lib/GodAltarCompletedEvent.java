package xyz.iwolfking.vhapi.api.events.vault.lib;

import iskallia.vault.core.event.Event;
import iskallia.vault.core.vault.influence.VaultGod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class GodAltarCompletedEvent extends Event<GodAltarCompletedEvent, GodAltarCompletedEvent.Data> {
    public GodAltarCompletedEvent() {
    }

    protected GodAltarCompletedEvent(GodAltarCompletedEvent parent) {
        super(parent);
    }

    public GodAltarCompletedEvent createChild() {
        return new GodAltarCompletedEvent(this);
    }

    public GodAltarCompletedEvent.Data invoke(Level world, Player player, BlockPos pos, UUID altarUUID, VaultGod god) {
        return (GodAltarCompletedEvent.Data)this.invoke(new GodAltarCompletedEvent.Data(world, player, pos, altarUUID, god));
    }

    public GodAltarCompletedEvent in(Level world) {
        return (GodAltarCompletedEvent)this.filter((data) -> {
            return data.getWorld() == world;
        });
    }

    public static class Data {
        private final Level world;
        private final Player player;
        private final BlockPos pos;
        private final UUID altarUUID;
        private final VaultGod god;


        public Data(Level world, Player player, BlockPos pos, UUID altarUUID, VaultGod god) {
            this.world = world;
            this.player = player;
            this.pos = pos;
            this.altarUUID = altarUUID;
            this.god = god;
        }

        public Level getWorld() {
            return this.world;
        }

        public Player getPlayer() {
            return this.player;
        }


        public BlockPos getPos() {
            return this.pos;
        }

        public UUID getAltarUUID() {
            return altarUUID;
        }

        public VaultGod getGod() {
            return god;
        }


    }
}
