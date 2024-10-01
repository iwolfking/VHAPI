package xyz.iwolfking.vaultcrackerlib.mixin.configs.skills;


import iskallia.vault.config.ExpertisesGUIConfig;
import iskallia.vault.config.entry.SkillStyle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.HashMap;

@Mixin(value = ExpertisesGUIConfig.class, remap = false)
public class MixinExpertisesGUIConfig {

    @Shadow private HashMap<String, SkillStyle> styles;

    @Inject(method = "getStyles", at = @At("HEAD"))
    private void addExpertiseGuiStyles(CallbackInfoReturnable<HashMap<String, SkillStyle>> cir) {
        Patchers.EXPERTISES_GUI_PATCHER.doPatches(styles);
    }
}
