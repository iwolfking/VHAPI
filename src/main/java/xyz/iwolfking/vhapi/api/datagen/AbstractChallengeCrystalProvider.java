package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.VaultMod;
import iskallia.vault.config.ChallengeCrystalConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.VaultCrystalConfig;
import iskallia.vault.core.vault.hazard.Hazard;
import iskallia.vault.core.vault.modifier.VaultModifierStack;
import iskallia.vault.core.vault.modifier.modifier.EmptyModifier;
import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.core.world.roll.IntRoll;
import iskallia.vault.item.crystal.hazard.CrystalHazards;
import iskallia.vault.item.crystal.layout.ClassicInfiniteCrystalLayout;
import iskallia.vault.item.crystal.layout.CrystalLayout;
import iskallia.vault.item.crystal.modifiers.CrystalModifiers;
import iskallia.vault.item.crystal.modifiers.DefaultCrystalModifiers;
import iskallia.vault.item.crystal.objective.CrystalObjective;
import iskallia.vault.item.crystal.objective.EmptyCrystalObjective;
import iskallia.vault.item.crystal.objective.NullCrystalObjective;
import iskallia.vault.item.crystal.theme.CrystalTheme;
import iskallia.vault.item.crystal.theme.PoolCrystalTheme;
import iskallia.vault.item.crystal.time.CrystalTime;
import iskallia.vault.item.crystal.time.ValueCrystalTime;
import net.minecraft.ChatFormatting;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.IGenericBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.ChallengeCrystalConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractChallengeCrystalProvider extends AbstractVaultConfigDataProvider<AbstractChallengeCrystalProvider.Builder> {
    protected AbstractChallengeCrystalProvider(DataGenerator generator, String modid) {
        super(generator, modid, "challenge_crystal", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Challenge Crystal Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<ChallengeCrystalConfig> {
        Map<String, ChallengeCrystalConfig.Entry> entries = new LinkedHashMap<>();

        public Builder() {
            super(ChallengeCrystalConfig::new);
        }

        public Builder addEntry(String challengeId, ChallengeCrystalConfig.Entry entry) {
            entries.put(challengeId, entry);
            return this;
        }

        public Builder addEntry(String challengeId, Consumer<ChallengeEntryBuilder> builderConsumer) {
            ChallengeEntryBuilder builder = new ChallengeEntryBuilder();
            builderConsumer.accept(builder);
            entries.put(challengeId, builder.build());
            return this;
        }

        @Override
        protected void configureConfig(ChallengeCrystalConfig config) {
            ((ChallengeCrystalConfigAccessor)config).setChallengesMap(entries);
        }
    }

    public static class ChallengeEntryBuilder {
        private CrystalObjective objective;
        private CrystalLayout layout;
        private CrystalTheme theme;
        private CrystalTime time;
        private final CrystalModifiers modifiers = new DefaultCrystalModifiers();
        private final CrystalHazards hazards = new CrystalHazards();
        private String sigil;
        private boolean exhausted = false;

        public ChallengeEntryBuilder objective(CrystalObjective objective) {
            this.objective = objective;
            return this;
        }

        public ChallengeEntryBuilder layout(CrystalLayout layout) {
            this.layout = layout;
            return this;
        }

        public ChallengeEntryBuilder theme(CrystalTheme theme) {
            this.theme = theme;
            return this;
        }

        public ChallengeEntryBuilder time(CrystalTime time) {
            this.time = time;
            return this;
        }

        public ChallengeEntryBuilder modifier(VaultModifierStack modifier) {
            this.modifiers.add(modifier);
            return this;
        }

        public ChallengeEntryBuilder modifier(ResourceLocation id, int size) {
            CompoundTag tag = new CompoundTag();
            tag.putString("id", id.toString());
            tag.putInt("size", size);
            //Why you make me do this :(, fake that modifier is registered so VaultModifierStack is valid, only called in datagen so no problem
            if(VaultModifierRegistry.getOpt(id).isEmpty()) {
                VaultModifierRegistry.register(id, new EmptyModifier(id, new EmptyModifier.Properties(), new VaultModifier.Display("", TextColor.fromLegacyFormat(ChatFormatting.AQUA), "")));
            }
            this.modifiers.add(VaultModifierStack.of(tag));

            return this;
        }

        public ChallengeEntryBuilder hazard(Hazard hazard) {
            this.hazards.addHazard(hazard);
            return this;
        }

        public ChallengeEntryBuilder sigil(String sigil) {
            this.sigil = sigil;
            return this;
        }

        public ChallengeEntryBuilder exhausted() {
            this.exhausted = true;
            return this;
        }


        public ChallengeCrystalConfig.Entry build() {
            return new ChallengeCrystalConfig.Entry(objective, layout, theme, time, modifiers, hazards, sigil, exhausted);
        }
    }
}
