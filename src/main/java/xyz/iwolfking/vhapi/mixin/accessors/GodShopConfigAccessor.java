package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.GodShopConfig;
import iskallia.vault.core.vault.influence.VaultGod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.List;
import java.util.Map;

@Mixin(value = GodShopConfig.class, remap = false)
public interface GodShopConfigAccessor {
    @Accessor("godEntries")
    Map<VaultGod, List<GodShopConfig.Entry>> getGodEntries();

    @Accessor("godEntries")
    void setGodEntries(Map<VaultGod, List<GodShopConfig.Entry>> entries);
}
