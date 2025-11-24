package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.block.entity.challenge.raid.action.*;
import iskallia.vault.config.RaidActionsConfig;
import iskallia.vault.core.world.data.entity.PartialEntity;
import iskallia.vault.core.world.roll.IntRoll;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractRaidActionsProvider extends AbstractVaultConfigDataProvider<AbstractRaidActionsProvider.Builder> {
    protected AbstractRaidActionsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "raid_actions", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Raid Actions Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<RaidActionsConfig> {
        private final Map<String, ChallengeAction<?>> values = new HashMap<>();

        public Builder() {
            super(RaidActionsConfig::new);
        }

        public Builder add(String key, Consumer<ActionBuilder> builderConsumer) {
            ActionBuilder builder = new ActionBuilder();
            builderConsumer.accept(builder);
            values.put(key, builder.build());
            return this;
        }

        @Override
        protected void configureConfig(RaidActionsConfig config) {
            ((RaidActionsConfigAccessor)config).getValues().putAll(values);
        }

        public static class ActionBuilder {
            private ChallengeAction<?> action;

            public ChallengeAction<?> addMobAction(PartialEntity entity, String name, String spawner, IntRoll count) {
                AddMobsChallengeAction.Config actionConfig = new AddMobsChallengeAction.Config();
                ((AddMobsChallengeActionConfigAccessor)actionConfig).setEntity(entity);
                ((AddMobsChallengeActionConfigAccessor)actionConfig).setName(name);
                ((AddMobsChallengeActionConfigAccessor)actionConfig).setSpawner(spawner);
                ((AddMobsChallengeActionConfigAccessor)actionConfig).setCount(count);
                this.action = new AddMobsChallengeAction(actionConfig);
                return build();
            }

            public ChallengeAction<?> addRewardAction(ItemStack rewardStack, String name, int color1, int color2) {
                FloatingItemRewardChallengeAction.Config actionConfig = new FloatingItemRewardChallengeAction.Config();
                ((FloatingItemRewardChallengeActionConfigAccessor)actionConfig).setItemStack(rewardStack);
                ((FloatingItemRewardChallengeActionConfigAccessor)actionConfig).setName(name);
                ((FloatingItemRewardChallengeActionConfigAccessor)actionConfig).setColor1(color1);
                ((FloatingItemRewardChallengeActionConfigAccessor)actionConfig).setColor2(color2);
                this.action = new FloatingItemRewardChallengeAction(actionConfig);
                return build();
            }

//            public ChallengeAction<?> addReferenceAction(String path) {
//                ReferenceChallengeAction.Config actionConfig = new ReferenceChallengeAction.Config();
//                ((ReferenceActionConfigAccessor)actionConfig).setPath(path);
//                this.action = new ReferenceChallengeAction(actionConfig);
//                return build();
//            }
//
//            public ChallengeAction<?> addAttributeAction(String path) {
//                ReferenceChallengeAction.Config actionConfig = new ReferenceChallengeAction.Config();
//                ((ReferenceActionConfigAccessor)actionConfig).setPath(path);
//                this.action = new ReferenceChallengeAction(actionConfig);
//                return build();
//            }
//
//            public ChallengeAction<?> addGroupAction(Consumer<GroupBuilder> groupBuilderConsumer) {
//                GroupBuilder groupBuilder = new GroupBuilder();
//                groupBuilderConsumer.accept(groupBuilder);
//
//                GroupChallengeAction.Config actionConfig = new GroupChallengeAction.Config();
//                ((GroupChallengeActionConfigAccessor)actionConfig).setChildren(groupBuilder.build());
//                this.action = new GroupChallengeAction(actionConfig);
//                return build();
//            }

            public ChallengeAction<?> build() {
                return action;
            }

            public static class GroupBuilder {
                private final Map<ChallengeAction<?>, IntRoll> group = new HashMap<>();

                public GroupBuilder add(Consumer<ActionBuilder> actionBuilderConsumer, IntRoll roll) {
                    ActionBuilder builder = new ActionBuilder();
                    actionBuilderConsumer.accept(builder);
                    group.put(builder.build(), roll);
                    return this;
                }

                public Map<ChallengeAction<?>, IntRoll> build() {
                    return group;
                }
            }
        }

    }
}
