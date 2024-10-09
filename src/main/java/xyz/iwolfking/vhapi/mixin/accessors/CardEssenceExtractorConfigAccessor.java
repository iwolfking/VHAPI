package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.CardEssenceExtractorConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = CardEssenceExtractorConfig.class, remap = false)
public interface CardEssenceExtractorConfigAccessor {
    @Accessor("tierConfigs")
    public Map<Integer, CardEssenceExtractorConfig.TierConfig> getTierConfigs();

}
