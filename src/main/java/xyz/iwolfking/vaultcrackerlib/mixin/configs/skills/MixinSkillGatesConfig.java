package xyz.iwolfking.vaultcrackerlib.mixin.configs.skills;

import iskallia.vault.config.SkillGatesConfig;

import iskallia.vault.skill.SkillGates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

@Mixin(value = SkillGatesConfig.class, remap = false)
public class MixinSkillGatesConfig {
    @Shadow private SkillGates SKILL_GATES;

    @Inject(method = "getGates", at = @At("HEAD"))
    public void getGates(CallbackInfoReturnable<SkillGates> cir) {
        for(String name : Patchers.SKILL_GATES_PATCHER.getAdditions().keySet()) {
            this.SKILL_GATES.addEntry(name, Patchers.SKILL_GATES_PATCHER.getAdditions().get(name));
        }
    }
}
