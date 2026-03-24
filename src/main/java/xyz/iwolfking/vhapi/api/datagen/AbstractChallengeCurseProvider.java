package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ChallengeCurseConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.core.vault.challenge.curse.ChallengeCurse;
import iskallia.vault.core.vault.challenge.curse.DummyChallengeCurse;
import iskallia.vault.core.vault.challenge.curse.MobEffectChallengeCurse;
import iskallia.vault.core.vault.challenge.curse.PlayerStatChallengeCurse;
import iskallia.vault.core.world.roll.FloatRoll;
import iskallia.vault.core.world.roll.IntRoll;
import iskallia.vault.util.calc.PlayerStat;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedListBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.ChallengeCurseConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractChallengeCurseProvider extends AbstractVaultConfigDataProvider<AbstractChallengeCurseProvider.Builder> {
    protected AbstractChallengeCurseProvider(DataGenerator generator, String modid) {
        super(generator, modid, "challenge/curse", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Challenge Curses Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<ChallengeCurseConfig> {
        Map<ResourceLocation, ChallengeCurse<?>> curseMap = new HashMap<>();
        Map<String, WeightedList<ResourceLocation>> curseWeights = new HashMap<>();

        public Builder() {
            super(ChallengeCurseConfig::new);
        }

        public Builder addCurse(ResourceLocation curseId, ChallengeCurse<?> curse) {
            curseMap.put(curseId, curse);
            return this;
        }

        public Builder addPlayerStat(ResourceLocation curseId, PlayerStat stat, FloatRoll value) {
            curseMap.put(curseId, new PlayerStatChallengeCurse(new PlayerStatChallengeCurse.Config(stat, value)));
            return this;
        }

        public Builder addMobEffect(ResourceLocation curseId, MobEffect effect, IntRoll duration, IntRoll amplifier, boolean ambient, boolean showParticles) {
            curseMap.put(curseId, new MobEffectChallengeCurse(new MobEffectChallengeCurse.Config(effect, duration, amplifier, ambient, showParticles)));
            return this;
        }

        public Builder addCurseWeights(String id, Consumer<WeightedListBuilder<ResourceLocation>> builderConsumer) {
            WeightedListBuilder<ResourceLocation> builder = new WeightedListBuilder<>();
            builderConsumer.accept(builder);
            curseWeights.put(id, builder.build());
            return this;
        }


        @Override
        protected void configureConfig(ChallengeCurseConfig config) {
            ((ChallengeCurseConfigAccessor)config).setCurses(curseMap);
            ((ChallengeCurseConfigAccessor)config).setCurseWeights(curseWeights);
        }
    }
}
