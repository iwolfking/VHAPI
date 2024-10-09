package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.VaultGeneralConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = VaultGeneralConfig.class, remap = false)
public interface VaultGeneralConfigAccessor {
    @Accessor("ITEM_BLACKLIST")
    public List<String> getItemBlacklist();

    @Accessor("BLOCK_BLACKLIST")
    public List<String> getBlockBlacklist();
}
