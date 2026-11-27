package xyz.iwolfking.vhapi.mixin.fixes;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import iskallia.vault.core.data.adapter.ISimpleAdapter;
import iskallia.vault.core.world.data.entity.PartialCompoundNbt;
import iskallia.vault.core.world.data.tile.PartialBlockState;
import net.minecraft.nbt.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(targets = "iskallia.vault.core.world.data.tile.PartialBlockState$Adapter")
public abstract class PartialBlockStateAdapterMixin implements ISimpleAdapter<PartialBlockState, Tag, JsonElement> {

    @Override
    public Optional<JsonElement> writeJson(@Nullable PartialBlockState value) {
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(new JsonPrimitive(value.toString()));
    }

    public Optional<PartialBlockState> readJson(@Nullable JsonElement json) {
        if (json == null || json.isJsonNull()) {
            return Optional.empty();
        }
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
            return PartialBlockState.parse(json.getAsString(), true);
        }
        return Optional.empty();
    }
}