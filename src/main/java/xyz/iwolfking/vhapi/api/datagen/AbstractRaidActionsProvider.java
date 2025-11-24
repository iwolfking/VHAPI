package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.block.entity.challenge.raid.action.AddMobsChallengeAction;
import iskallia.vault.block.entity.challenge.raid.action.ChallengeAction;
import iskallia.vault.config.RaidActionsConfig;
import iskallia.vault.config.TooltipConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractRaidActionsProvider extends AbstractVaultConfigDataProvider {
    protected AbstractRaidActionsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "raid_actions");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Raid Actions Data Provider";
    }

    public static class RaidActionsConfigBuilder {
        private Map<String, ChallengeAction<?>> values;

        public <C extends ChallengeAction.Config> RaidActionsConfigBuilder add(String id, Consumer<ChallengeAction<C>> actionConsumer) {
            //ChallengeAction<C> challengeAction = new AddMobsChallengeAction(new AddMobsChallengeAction.Config());
            return this;
        }

        public RaidActionsConfig build() {
            RaidActionsConfig newConfig = new RaidActionsConfig();

            return newConfig;
        }


    }
}
