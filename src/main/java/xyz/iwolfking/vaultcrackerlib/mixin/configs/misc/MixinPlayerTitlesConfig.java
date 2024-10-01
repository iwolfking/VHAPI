package xyz.iwolfking.vaultcrackerlib.mixin.configs.misc;

import iskallia.vault.config.PlayerTitlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.Map;

@Mixin(value = PlayerTitlesConfig.class, remap = false)
public class MixinPlayerTitlesConfig {

    @Shadow private Map<PlayerTitlesConfig.Affix, Map<String, PlayerTitlesConfig.Title>> titles;

    @Inject(method = "getAll", at = @At("HEAD"))
    private void patchAffixes(PlayerTitlesConfig.Affix affix, CallbackInfoReturnable<Map<String, PlayerTitlesConfig.Title>> cir) {
        if(!Patchers.PLAYER_TITLES_PATCHER.isPatched()) {
            Map<String, PlayerTitlesConfig.Title> titleAdditions = Patchers.PLAYER_TITLES_PATCHER.getAdditions().get(affix);
            for (String name : titleAdditions.keySet()) {
                this.titles.get(affix).put(name, titleAdditions.get(name));
            }
        }
    }
}
