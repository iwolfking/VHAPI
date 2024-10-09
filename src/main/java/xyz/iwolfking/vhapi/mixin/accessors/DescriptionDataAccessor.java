package xyz.iwolfking.vhapi.mixin.accessors;

import com.google.gson.JsonElement;
import iskallia.vault.config.AbilitiesDescriptionsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AbilitiesDescriptionsConfig.DescriptionData.class, remap = false)
public interface DescriptionDataAccessor {
    @Accessor("description")
    public JsonElement getDescription();
}
