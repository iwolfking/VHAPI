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

@Mixin(value = AbilitiesGUIConfig.class, remap = false)
public class MixinAbilitiesGUIConfig {

    @Shadow private HashMap<String, SkillStyle> styles;

    @Inject(method = "getStyles", at = @At("HEAD"))
    private void addAbilitiesGUIStyles(CallbackInfoReturnable<HashMap<String, SkillStyle>> cir) {
        Patchers.ABILITIES_GUI_PATCHER.doPatches(styles);
    }
}
