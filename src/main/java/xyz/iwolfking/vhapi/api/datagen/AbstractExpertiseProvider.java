package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ExpertisesConfig;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.TieredSkill;
import iskallia.vault.skill.tree.ExpertiseTree;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;

import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public abstract class AbstractExpertiseProvider extends AbstractVaultConfigDataProvider<AbstractExpertiseProvider.Builder> {
    protected AbstractExpertiseProvider(DataGenerator generator, String modid) {
        super(generator, modid, "expertise/expertises", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Expertises Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<ExpertisesConfig> {
        ExpertiseTree tree = new ExpertiseTree();

        public Builder() {
            super(ExpertisesConfig::new);
        }

        private void assignSkillValues(TieredSkill skill, String id, String name) {
            skill.setId(id);
            skill.setName(name);
            tree.skills.add(skill);
        }

        public <T extends LearnableSkill> Builder addExpertise(String id, String name, int unlockLevel, int learnPointCost, int numberOfTiers, IntFunction<T> expertiseFactory) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 0, IntStream.range(0, numberOfTiers).mapToObj(expertiseFactory));

            assignSkillValues(skill, id, name);

            return this;
        }


        @Override
        protected void configureConfig(ExpertisesConfig config) {
            config.tree = tree;
        }

    }
}
