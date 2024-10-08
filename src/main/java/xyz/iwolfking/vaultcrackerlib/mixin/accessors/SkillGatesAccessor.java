package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.skill.SkillGates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = SkillGates.class, remap = false)
public interface SkillGatesAccessor {
    @Accessor("entries")
    public Map<String, SkillGates.Entry> getEntries();
}
