package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.CardEssenceExtractorConfig;
import iskallia.vault.config.VaultModifiersConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = CardEssenceExtractorConfig.class, remap = false)
public interface CardEssenceExtractorConfigAccessor {
    @Accessor("tierConfigs")
    public Map<Integer, CardEssenceExtractorConfig.TierConfig> getTierConfigs();

}
