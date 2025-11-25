package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.VaultCrystalConfig;
import iskallia.vault.config.entry.LevelEntryList;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = VaultCrystalConfig.class, remap = false)
public interface VaultCrystalConfigAccessor {
    @Accessor("SEALS")
    Map<ResourceLocation, LevelEntryList<VaultCrystalConfig.SealEntry>> getSeals();

    @Accessor("SEALS")
    void setSeals(Map<ResourceLocation, LevelEntryList<VaultCrystalConfig.SealEntry>> seals);

    @Accessor("OBJECTIVES")
    void setObjectives(Map<ResourceLocation, LevelEntryList<VaultCrystalConfig.ObjectiveEntry>> objectives);

    @Accessor("THEMES")
    void setThemes(Map<ResourceLocation, LevelEntryList<VaultCrystalConfig.ThemeEntry>> themes);

    @Accessor("LAYOUTS")
    void setLayouts(LevelEntryList<VaultCrystalConfig.LayoutEntry> layouts);
}
