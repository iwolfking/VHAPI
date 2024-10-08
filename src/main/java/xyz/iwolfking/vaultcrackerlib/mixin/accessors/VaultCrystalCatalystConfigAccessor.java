package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import iskallia.vault.config.VaultCrystalCatalystConfig;
import iskallia.vault.config.VaultLevelsConfig;
import iskallia.vault.util.data.WeightedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(value = VaultCrystalCatalystConfig.class, remap = false)
public interface VaultCrystalCatalystConfigAccessor {
    @Accessor("MODIFIER_POOLS")
    public Map<String, VaultCrystalCatalystConfig.ModifierPool> getModifierPools();

    @Accessor("MODIFIER_POOL_GROUPS")
    public WeightedList<VaultCrystalCatalystConfig.ModifierPoolGroup> getModifierPoolGroups();
}
