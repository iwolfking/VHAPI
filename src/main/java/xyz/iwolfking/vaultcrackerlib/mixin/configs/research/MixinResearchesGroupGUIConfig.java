package xyz.iwolfking.vaultcrackerlib.mixin.configs.research;

import iskallia.vault.config.ResearchGroupStyleConfig;
import iskallia.vault.config.entry.ResearchGroupStyle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;


import java.util.Map;

@Mixin(value = ResearchGroupStyleConfig.class, remap = false)
public class MixinResearchesGroupGUIConfig {
    @Shadow protected Map<String, ResearchGroupStyle> groupStyles;

    @Inject(method = "getStyles", at = @At("HEAD"))
    public void getStyles(CallbackInfoReturnable<Map<String, ResearchGroupStyle>> cir) {
        Patchers.RESEARCH_GROUP_GUI_PATCHER.doPatches(groupStyles);
    }

    @Inject(method = "getStyle", at = @At("HEAD"))
    public void getStyle(String groupId, CallbackInfoReturnable<ResearchGroupStyle> cir) {
        Patchers.RESEARCH_GROUP_GUI_PATCHER.doPatches(groupStyles);
    }
}
