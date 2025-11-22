package xyz.iwolfking.vhapi.api.lib.core.datagen;

import iskallia.vault.config.TalentsConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.TieredSkill;
import iskallia.vault.skill.talent.GearAttributeSkill;
import iskallia.vault.skill.talent.type.GearAttributeTalent;
import iskallia.vault.skill.tree.TalentTree;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.loaders.talents.TalentConfigLoader;
import xyz.iwolfking.vhapi.mixin.accessors.TieredSkillAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.List;
import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractTalentProvider extends AbstractVaultConfigDataProvider {
    protected AbstractTalentProvider(DataGenerator generator, String modid) {
        super(generator, modid, "talents/talents");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Talents Data Provider";
    }

    public static class TalentBuilder {
        TalentTree tree = new TalentTree();

        public TalentBuilder addGearAttributeTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, VaultGearAttribute<?> attribute, IntToDoubleFunction scalingFunction) {

            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new GearAttributeTalent(unlockLevel, learnPointCost, 1, attribute, scalingFunction.applyAsDouble(i));
            }));
            skill.setId(id);
            skill.setName(name);
            ((TieredSkillAccessor)skill).setMaxLearnableTier(maxLearnableTier);
            tree.skills.add(skill);
            return this;
        }

        public TalentsConfig build() {
            TalentsConfig newConfig = new TalentsConfig();
            newConfig.tree = tree;
            return newConfig;
        }

    }
}
