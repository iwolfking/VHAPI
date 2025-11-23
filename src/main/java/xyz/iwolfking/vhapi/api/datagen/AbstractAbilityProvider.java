package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.AbilitiesConfig;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.SpecializedSkill;
import iskallia.vault.skill.base.TieredSkill;
import iskallia.vault.skill.tree.AbilityTree;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import xyz.iwolfking.vhapi.mixin.accessors.TieredSkillAccessor;

import java.io.IOException;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public abstract class AbstractAbilityProvider extends AbstractVaultConfigDataProvider {
    protected AbstractAbilityProvider(DataGenerator generator, String modid) {
        super(generator, modid, "abilities/abilities");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Abilities Data Provider";
    }

    public static class AbilityBuilder {
        AbilityTree tree = new AbilityTree();

        public <V extends TieredSkill> AbilityBuilder addSpecializedAbility(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int specializationCount, IntFunction<V> abilityFactory) {
            SpecializedSkill skill = new SpecializedSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, specializationCount).mapToObj(abilityFactory));
            assignSkillValues(skill, id, name);
            tree.skills.add(skill);
            return this;
        }


        public AbilitiesConfig build() {
            AbilitiesConfig newConfig = new AbilitiesConfig();
            newConfig.tree = tree;
            return newConfig;
        }

    }

    private static void assignSkillValues(LearnableSkill skill, String id, String name) {
        skill.setId(id);
        skill.setName(name);
    }

    private static TieredSkill assignSkillValues(TieredSkill skill, String id, String name, int maxLearnableTier) {
        ((TieredSkillAccessor)skill).setMaxLearnableTier(maxLearnableTier);
        assignSkillValues(skill, id, name);
        return skill;
    }

    public static LearnableSkill createAndAssignId(String idBase, int idNumber, LearnableSkill skill) {
        skill.setId(idBase + "_" + idNumber);
        return skill;
    }


    public static <T extends LearnableSkill> TieredSkill createTieredSkill(String id, String name, int unlockLevel, int learnPointCost, int maxLearnableTier, int tiers, IntFunction<T> abilityFactory) {
        return assignSkillValues(new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, tiers).mapToObj(abilityFactory)), id, name, maxLearnableTier);
    }
}
