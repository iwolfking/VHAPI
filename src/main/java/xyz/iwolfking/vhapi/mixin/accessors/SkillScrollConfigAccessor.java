package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.SkillGatesConfig;
import iskallia.vault.config.SkillScrollConfig;
import iskallia.vault.skill.SkillGates;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SkillScrollConfig.class, remap = false)
public interface SkillScrollConfigAccessor {
    @Accessor("abilities")
    void setAbilities(WeightedList<String> abilities);

    @Accessor("abilities")
    WeightedList<String> getAbilities();

    @Accessor("talents")
    WeightedList<String> getTalents();

    @Accessor("talents")
    void setTalents(WeightedList<String> talents);

    @Accessor("modifiers")
    void setModifiers(WeightedList<ResourceLocation> modifiers);


    @Accessor("modifiers")
    WeightedList<ResourceLocation> getModifiers();
}
