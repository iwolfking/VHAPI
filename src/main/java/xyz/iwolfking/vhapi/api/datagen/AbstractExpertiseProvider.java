package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ExpertisesConfig;
import iskallia.vault.config.TalentsConfig;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.TieredSkill;
import iskallia.vault.skill.expertise.type.FarmerTwerker;
import iskallia.vault.skill.talent.type.*;
import iskallia.vault.skill.talent.type.health.HighHealthGearAttributeTalent;
import iskallia.vault.skill.talent.type.luckyhit.DamageLuckyHitTalent;
import iskallia.vault.skill.talent.type.luckyhit.ManaLeechLuckyHitTalent;
import iskallia.vault.skill.talent.type.luckyhit.SweepingLuckyHitTalent;
import iskallia.vault.skill.talent.type.mana.HighManaGearAttributeTalent;
import iskallia.vault.skill.talent.type.mana.LowManaDamageTalent;
import iskallia.vault.skill.talent.type.onhit.CastOnHitTalent;
import iskallia.vault.skill.talent.type.onhit.DamageOnHitTalent;
import iskallia.vault.skill.talent.type.onhit.EffectOnHitTalent;
import iskallia.vault.skill.talent.type.onkill.CastOnKillTalent;
import iskallia.vault.skill.tree.ExpertiseTree;
import iskallia.vault.skill.tree.TalentTree;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.loaders.Processors;
import xyz.iwolfking.vhapi.api.loaders.expertises.ExpertiseConfigLoader;
import xyz.iwolfking.vhapi.mixin.accessors.FarmerTwerkerAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.PuristTalentAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TieredSkillAccessor;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;

public abstract class AbstractExpertiseProvider extends AbstractVaultConfigDataProvider {
    protected AbstractExpertiseProvider(DataGenerator generator, String modid) {
        super(generator, modid, "expertise/expertises");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Expertises Data Provider";
    }

    public static class ExpertiseBuilder {
        ExpertiseTree tree = new ExpertiseTree();

        private void assignSkillValues(TieredSkill skill, String id, String name) {
            skill.setId(id);
            skill.setName(name);
            tree.skills.add(skill);
        }

        public <T extends LearnableSkill> ExpertiseBuilder addExpertise(String id, String name, int unlockLevel, int learnPointCost, int numberOfTiers, IntFunction<T> expertiseFactory) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 0, IntStream.range(0, numberOfTiers).mapToObj(expertiseFactory));

            assignSkillValues(skill, id, name);

            return this;
        }



        public ExpertisesConfig build() {
            ExpertisesConfig newConfig = new ExpertisesConfig();
            newConfig.tree = tree;
            return newConfig;
        }

    }
}
