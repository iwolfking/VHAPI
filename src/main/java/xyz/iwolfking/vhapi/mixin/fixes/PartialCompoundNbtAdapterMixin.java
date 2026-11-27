package xyz.iwolfking.vhapi.mixin.fixes;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import iskallia.vault.core.data.adapter.ISimpleAdapter;
import iskallia.vault.core.world.data.entity.PartialCompoundNbt;
import net.minecraft.nbt.Tag;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(targets = "iskallia.vault.core.world.data.entity.PartialCompoundNbt$Adapter")
public abstract class PartialCompoundNbtAdapterMixin  implements ISimpleAdapter<PartialCompoundNbt, Tag, JsonElement> {

    @Override
    public Optional<JsonElement> writeJson(@Nullable PartialCompoundNbt value) {
        if (value == null) {
            return Optional.empty();
        }
        return value.asWhole().map(tag -> new JsonPrimitive(tag.toString()));
    }

    public Optional<PartialCompoundNbt> readJson(@Nullable JsonElement json) {
        if (json == null || json.isJsonNull()) {
            return Optional.empty();
        }
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
            try {
                return Optional.of(PartialCompoundNbt.parse(json.getAsString()));
            } catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}