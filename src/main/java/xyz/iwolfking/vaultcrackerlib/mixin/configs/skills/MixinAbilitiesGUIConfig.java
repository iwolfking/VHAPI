package xyz.iwolfking.vaultcrackerlib.mixin.configs.skills;


import iskallia.vault.config.AbilitiesGUIConfig;
import iskallia.vault.config.TalentsGUIConfig;
import iskallia.vault.config.entry.SkillStyle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = AbilitiesGUIConfig.class, remap = false)
public class MixinAbilitiesGUIConfig {


    @Shadow private Map<String, AbilitiesGUIConfig.AbilityStyle> styles;

    @Inject(method = "getStyles", at = @At("HEAD"))
    private void addStyles(CallbackInfoReturnable<Map<String, AbilitiesGUIConfig.AbilityStyle>> cir) {
        Patchers.ABILITIES_GUI_PATCHER.doPatches(styles);
    }

}
