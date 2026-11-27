package xyz.iwolfking.vhapi.api.builtins;

import iskallia.vault.core.world.data.entity.PartialCompoundNbt;
import iskallia.vault.core.world.data.entity.PartialEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;

public class BuiltInEntities {
    public static PartialEntity raidMob(ResourceLocation entityId) {
        CompoundTag tag = baseRaidTag();

        ListTag tags = new ListTag();
        tags.add(StringTag.valueOf("no_champion"));
        tags.add(StringTag.valueOf("ethereal_immune"));
        tag.put("Tags", tags);

        return PartialEntity.of(
                null,
                BlockPos.ZERO,
                entityId,
                PartialCompoundNbt.of(tag)
        );
    }

    public static PartialEntity raidChampion(ResourceLocation entityId) {
        CompoundTag tag = baseRaidTag();

        ListTag tags = new ListTag();
        tags.add(StringTag.valueOf("vault_champion"));
        tags.add(StringTag.valueOf("no_drops"));
        tags.add(StringTag.valueOf("ethereal_immune"));
        tag.put("Tags", tags);

        return PartialEntity.of(
                null,
                BlockPos.ZERO,
                entityId,
                PartialCompoundNbt.of(tag)
        );
    }

    private static CompoundTag baseRaidTag() {
        CompoundTag tag = new CompoundTag();
        ListTag immunity = new ListTag();
        immunity.add(StringTag.valueOf("#the_vault:modifier_type/mob_frenzy"));
        immunity.add(StringTag.valueOf("#the_vault:modifier_type/mob_attribute"));
        immunity.add(StringTag.valueOf("#the_vault:modifier_type/entity_effect"));
        tag.put("modifierImmunity", immunity);
        return tag;
    }
}