package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.VaultLevelsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = VaultLevelsConfig.class, remap = false)
public interface VaultLevelsConfigAccessor {
    @Accessor("levelMetas")
    public List<String> getLevelMetas();

    @Accessor("maxLevel")
    public void setMaxLevel(int maxLevel);
}
