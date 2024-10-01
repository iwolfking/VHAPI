package xyz.iwolfking.vaultcrackerlib.mixin.configs.research;

import iskallia.vault.config.ResearchesGUIConfig;
import iskallia.vault.config.entry.SkillStyle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;


import java.util.HashMap;

@Mixin(value = ResearchesGUIConfig.class, remap = false)
public class MixinResearchesGUIConfig {

    @Shadow private HashMap<String, SkillStyle> styles;

    /**
     * @author iwolfking
     * @reason Patch research styles with custom ones.
     */
    @Inject(method = "getStyles", at = @At("HEAD"))
    public void getStyles(CallbackInfoReturnable<HashMap<String, SkillStyle>> cir) {
        Patchers.RESEARCH_GUI_PATCHER.doPatches(styles);
    }
}
