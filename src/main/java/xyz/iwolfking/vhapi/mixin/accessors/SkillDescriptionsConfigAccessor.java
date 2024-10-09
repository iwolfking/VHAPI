package xyz.iwolfking.vhapi.mixin.accessors;

import com.google.gson.JsonElement;
import iskallia.vault.config.SkillDescriptionsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;

@Mixin(value = SkillDescriptionsConfig.class, remap = false)
public interface SkillDescriptionsConfigAccessor {
    @Accessor("descriptions")
    public HashMap<String, JsonElement> getDescriptions();
}
