package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.PlayerTitlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = PlayerTitlesConfig.class, remap = false)
public interface PlayerTitlesConfigAccessor {
    @Accessor("titles")
    public Map<PlayerTitlesConfig.Affix, Map<String, PlayerTitlesConfig.Title>> getTitles();
}
