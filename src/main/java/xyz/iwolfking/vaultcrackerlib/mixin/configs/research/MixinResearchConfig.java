package xyz.iwolfking.vaultcrackerlib.mixin.configs.research;

import iskallia.vault.config.ResearchConfig;
import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;
import iskallia.vault.research.type.Research;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;


import java.util.*;

@Mixin(value = ResearchConfig.class, remap = false)
public class MixinResearchConfig {
    @Shadow public List<ModResearch> MOD_RESEARCHES;
    @Shadow public List<CustomResearch> CUSTOM_RESEARCHES;

    /**
     * @author iwolfking
     * @reason Add new researches and patch existing ones.
     */
    @Overwrite
    public List<Research> getAll() {
        Set<Research> all = new HashSet<>();
        if(!Patchers.MOD_RESEARCH_PATCHER.isPatched()) {
            this.MOD_RESEARCHES.addAll(Patchers.MOD_RESEARCH_PATCHER.getAdditions());
            this.CUSTOM_RESEARCHES.addAll(Patchers.CUSTOM_RESEARCH_PATCHER.getAdditions());
            Patchers.MOD_RESEARCH_PATCHER.setPatched(true);
            Patchers.CUSTOM_RESEARCH_PATCHER.setPatched(true);
        }
        all.addAll(this.MOD_RESEARCHES);
        all.addAll(this.CUSTOM_RESEARCHES);
        return new LinkedList<>(all);
    }
}
