package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ChallengeActionsConfig;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.core.vault.challenge.action.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;

import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.core.world.data.entity.PartialEntity;
import iskallia.vault.core.world.data.tile.PartialTile;
import iskallia.vault.core.world.roll.DoubleRoll;
import iskallia.vault.core.world.roll.IntRoll;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.*;

public abstract class AbstractChallengeActionsProvider
        extends AbstractVaultConfigDataProvider<AbstractChallengeActionsProvider.Builder> {
    protected AbstractChallengeActionsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "challenge/actions", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Challenge Actions Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<ChallengeActionsConfig> {
        public final Map<String, ChallengeAction<?>> values = new LinkedHashMap<>();

        public Builder() {
            super(ChallengeActionsConfig::new);
        }

        public Builder addAction(String id, ChallengeAction<?> action) {
            this.values.put(id, action);
            return this;
        }

        public Builder addReference(String id, String targetActionId, int textColor) {
            ReferenceChallengeAction.Config config = new ReferenceChallengeAction.Config(targetActionId);
            config.textColor = textColor;
            return addAction(id, ReferenceChallengeActionInvoker.create(config));
        }

        public Builder addTileReward(
                String id,
                PartialTile tile,
                int count,
                @Nullable String displayName,
                int textColor
        ) {
            TileRewardChallengeAction.Config config = new TileRewardChallengeAction.Config();
            config.tile = tile.copy();
            config.count = count;
            config.name = displayName;
            config.textColor = textColor;
            return addAction(id, new TileRewardChallengeAction(config));
        }

        public Builder addPlayerVanillaAttribute(
                String id,
                @Nullable String displayName,
                @Nullable EntityPredicate filter,
                Attribute attribute,
                AttributeModifier.Operation operation,
                int textColor,
                DoubleRoll... amount
        ) {
            VanillaAttributeChallengeAction.Config config = new VanillaAttributeChallengeAction.Config(
                    displayName, filter, attribute, operation, amount
            );
            config.textColor = textColor;
            PlayerVanillaAttributeChallengeAction action = new PlayerVanillaAttributeChallengeAction();
            ((ChallengeActionAccessor<VanillaAttributeChallengeAction.Config>) action).setConfig(config);
            return addAction(id, action);
        }

        public Builder addMobVanillaAttribute(
                String id,
                @Nullable String displayName,
                @Nullable EntityPredicate filter,
                Attribute attribute,
                AttributeModifier.Operation operation,
                int textColor,
                DoubleRoll... amount
        ) {
            VanillaAttributeChallengeAction.Config config = new VanillaAttributeChallengeAction.Config(
                    displayName, filter, attribute, operation, amount
            );
            config.textColor = textColor;
            MobVanillaAttributeChallengeAction action = new MobVanillaAttributeChallengeAction();
            ((ChallengeActionAccessor<VanillaAttributeChallengeAction.Config>) action).setConfig(config);
            return addAction(id, action);
        }

        public Builder addMultiplyActions(String id, @Nullable String displayName, int textColor) {
            MultiplyActionsChallengeAction.Config config = new MultiplyActionsChallengeAction.Config();
            ((MultiplyActionsChallengeActionConfigAccessor) config).setName(displayName);
            config.textColor = textColor;
            return addAction(id, new MultiplyActionsChallengeAction(config));
        }

        public Builder addGroup(String id, int textColor, Consumer<GroupBuilder> groupBuilderConsumer) {
            GroupBuilder builder = new GroupBuilder(textColor);
            groupBuilderConsumer.accept(builder);
            return addAction(id, builder.build());
        }

        public static class GroupBuilder {
            private final GroupChallengeAction.Config config = new GroupChallengeAction.Config();

            public GroupBuilder(int textColor) {
                this.config.textColor = textColor;
            }

            public GroupBuilder addChild(ChallengeAction<?> action, IntRoll roll) {
                ((GroupChallengeActionConfigAccessor) this.config).getChildren().put(action, roll);
                return this;
            }

            public GroupChallengeAction build() {
                return GroupChallengeActionInvoker.create(this.config);
            }
        }

        public Builder addForfeit(String id, boolean loot, int textColor) {
            ForfeitChallengeAction.Config config = new ForfeitChallengeAction.Config(loot);
            config.textColor = textColor;
            return addAction(id, new ForfeitChallengeAction(config));
        }

        public Builder addDouble(String id, int textColor) {
            DoubleChallengeAction.Config config = new DoubleChallengeAction.Config();
            config.setTextColor(textColor);
            return addAction(id, new DoubleChallengeAction(config));
        }

        public Builder addModifier(String id, String modifierId, int time, int textColor) {
            AddModifierChallengeAction.Config config = new AddModifierChallengeAction.Config();
            AddModifierChallengeActionConfigAccessor accessor = (AddModifierChallengeActionConfigAccessor) config;
            accessor.setModifierId(modifierId);
            accessor.setTime(time);
            config.textColor = textColor;
            return addAction(id, new AddModifierChallengeAction(config));
        }

        public Builder addMobs(
                String id,
                @Nullable String displayName,
                PartialEntity entity,
                String spawner,
                IntRoll count,
                int textColor
        ) {
            AddMobsChallengeAction.Config config = new AddMobsChallengeAction.Config();
            AddMobsChallengeActionConfigAccessor accessor = (AddMobsChallengeActionConfigAccessor) config;
            accessor.setName(displayName);
            accessor.setEntity(entity.copy());
            accessor.setSpawner(spawner);
            accessor.setCount(count);
            config.textColor = textColor;
            return addAction(id, new AddMobsChallengeAction(config));
        }

        public Builder addFloatingItemReward(
                String id,
                ItemStack stack,
                @Nullable String displayName,
                int color1,
                int color2,
                int textColor
        ) {
            FloatingItemRewardChallengeAction.Config config = new FloatingItemRewardChallengeAction.Config();
            config.item = stack.copy();
            config.name = displayName;
            config.color1 = color1;
            config.color2 = color2;
            config.textColor = textColor;
            return addAction(id, new FloatingItemRewardChallengeAction(config));
        }


        public Builder addPool(String id, int textColor, Consumer<PoolBuilder> poolBuilderConsumer)
        {
            PoolBuilder builder = new PoolBuilder(textColor);
            poolBuilderConsumer.accept(builder);
            return addAction(id, builder.build());
        }

        @Override
        protected void configureConfig(ChallengeActionsConfig config) {
            config.values = new LinkedHashMap<>(this.values);
        }
    }

    public static class PoolBuilder {
        private final PoolChallengeAction.Config config = new PoolChallengeAction.Config();

        public PoolBuilder(int textColor) {
            this.config.textColor = textColor;
        }

        public PoolBuilder addLevel(int level, Consumer<WeightedList<ChallengeAction<?>>> poolConfigurer) {
            PoolChallengeAction.Config.LevelPool levelPool = new PoolChallengeAction.Config.LevelPool();
            try {
                var field = PoolChallengeAction.Config.LevelPool.class.getDeclaredField("level");
                field.setAccessible(true);
                field.setInt(levelPool, level);
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("Failed to set LevelPool.level", e);
            }
            poolConfigurer.accept(levelPool.pool);
            this.config.pools.add(levelPool);
            return this;
        }

        public PoolChallengeAction build() {
            return PoolChallengeActionInvoker.create(this.config);
        }
    }
}