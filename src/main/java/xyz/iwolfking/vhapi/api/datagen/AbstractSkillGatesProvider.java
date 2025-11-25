package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.SkillGatesConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.skillgate.ConstantSkillGate;
import iskallia.vault.config.skillgate.EitherSkillGate;
import iskallia.vault.config.skillgate.SkillGateType;
import iskallia.vault.skill.SkillGates;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.SkillGatesConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractSkillGatesProvider extends AbstractVaultConfigDataProvider<AbstractSkillGatesProvider.Builder> {
    protected AbstractSkillGatesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "skill/gates", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Skill Gates Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<SkillGatesConfig> {
        private final SkillGates SKILL_GATES = new SkillGates();

        public Builder() {
            super(SkillGatesConfig::new);
        }

        public Builder add(String skillName, Consumer<SkillGateEntryBuilder> builderConsumer) {
            SkillGateEntryBuilder builder = new SkillGateEntryBuilder();
            builderConsumer.accept(builder);
            SKILL_GATES.addEntry(skillName, builder.build());
            return this;
        }

        @Override
        protected void configureConfig(SkillGatesConfig config) {
            ((SkillGatesConfigAccessor)config).setGates(SKILL_GATES);
        }

        public static class SkillGateEntryBuilder {
            private final SkillGates.Entry entry = new SkillGates.Entry();

            public SkillGateEntryBuilder dependsOn(Consumer<TypeBuilder> gatesBuilder) {
                TypeBuilder builder = new TypeBuilder();
                gatesBuilder.accept(builder);
                entry.setDependsOn(builder.build().toArray(new SkillGateType[]{}));
                return this;
            }

            public SkillGateEntryBuilder lockedBy(Consumer<TypeBuilder> gatesBuilder) {
                TypeBuilder builder = new TypeBuilder();
                gatesBuilder.accept(builder);
                entry.setLockedBy(builder.build().toArray(new SkillGateType[]{}));
                return this;
            }

            public SkillGates.Entry build() {
                return entry;
            }

            public static class TypeBuilder extends BasicListBuilder<SkillGateType> {
                public TypeBuilder constant(String skillId) {
                    add(new ConstantSkillGate(skillId));
                    return this;
                }

                public TypeBuilder either(Consumer<TypeBuilder> builderConsumer) {
                    TypeBuilder builder = new TypeBuilder();
                    builderConsumer.accept(builder);
                    add(new EitherSkillGate(builder.build()));
                    return this;
                }
            }
        }
    }
}
