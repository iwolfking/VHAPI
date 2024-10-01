package xyz.iwolfking.vaultcrackerlib.mixin.configs.research;

import iskallia.vault.config.ResearchGroupConfig;
import iskallia.vault.research.group.ResearchGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;


import java.util.Map;

@Mixin(value = ResearchGroupConfig.class, remap = false)
public class MixinResearchGroupConfig {

    @Shadow protected Map<String, ResearchGroup> groups;

    @Inject(method = "getGroups", at = @At("HEAD"))
    private void addCustomResearchGroups(CallbackInfoReturnable<Map<String, ResearchGroup>> cir) {
        Patchers.RESEARCH_GROUP_PATCHER.doPatches(groups);
    }
}
