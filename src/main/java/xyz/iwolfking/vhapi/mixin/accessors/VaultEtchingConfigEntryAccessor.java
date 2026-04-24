package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.gear.EtchingTierConfig;
import iskallia.vault.config.gear.VaultEtchingConfig;
import iskallia.vault.gear.attribute.VaultGearModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = VaultEtchingConfig.EtchingEntry.class, remap = false)
public interface VaultEtchingConfigEntryAccessor {
    @Accessor("affixType")
    void setAffixType(VaultGearModifier.AffixType affixType);

    @Accessor("minGreedTier")
    void setMinGreedTier(int minGreedTier);

    @Accessor("typeGroups")
    void setTypeGroups(List<String> typeGroups);

    @Accessor("attributes")
    void setEtchingAttributeGroup(EtchingTierConfig.EtchingAttributeGroup etchingAttributeGroup);
}
