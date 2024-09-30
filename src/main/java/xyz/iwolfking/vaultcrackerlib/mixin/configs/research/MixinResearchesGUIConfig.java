package xyz.iwolfking.vaultcrackerlib.mixin.configs.research;

import iskallia.vault.config.ResearchesGUIConfig;
import iskallia.vault.config.entry.SkillStyle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.research.ResearchGUIConfigPatcher;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.research.ResearchGroupGUIConfigPatcher;

import java.util.HashMap;

@Mixin(value = ResearchesGUIConfig.class, remap = false)
public class MixinResearchesGUIConfig {

    @Shadow private HashMap<String, SkillStyle> styles;

    /**
     * @author iwolfking
     * @reason Patch research styles with custom ones. For some reason inject isn't working?
     */
    @Overwrite
    public HashMap<String, SkillStyle> getStyles() {
        if(!ResearchGUIConfigPatcher.isPatched()) {
            for(String name : ResearchGUIConfigPatcher.getResearchGuiToRemove()) {
                this.styles.remove(name);
            }
            this.styles.putAll(ResearchGUIConfigPatcher.getResearchConfigPatches());
            ResearchGUIConfigPatcher.setPatched(true);
        }
        return this.styles;
    }
}
