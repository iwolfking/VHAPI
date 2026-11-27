package xyz.iwolfking.vhapi.api.builtins;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import iskallia.vault.core.world.data.entity.PartialCompoundNbt;
import iskallia.vault.core.world.data.tile.PartialBlockState;
import iskallia.vault.core.world.data.tile.PartialTile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

public class BuiltInTiles {
    public static PartialTile tileWithNoTrap(String type) throws CommandSyntaxException {
        CompoundTag tag = new CompoundTag();
        ListTag templateTags = new ListTag();
        templateTags.add(StringTag.valueOf("no_trap"));
        tag.put("template_tags", templateTags);

        PartialBlockState state = PartialBlockState.parse("the_vault:placeholder[type=" + type + ",facing=up]");

        return PartialTile.of(state, PartialCompoundNbt.of(tag));
    }
}
