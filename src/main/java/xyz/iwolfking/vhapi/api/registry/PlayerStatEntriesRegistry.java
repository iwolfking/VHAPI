package xyz.iwolfking.vhapi.api.registry;

import iskallia.vault.client.gui.screen.player.StatisticsElementContainerScreenData;
import iskallia.vault.client.gui.screen.player.stats.PlayerStatEntry;
import iskallia.vault.core.vault.stat.StatTotals;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.snapshot.AttributeSnapshotHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

@OnlyIn(Dist.CLIENT)
public final class PlayerStatEntriesRegistry {
    public static final PlayerStatEntriesRegistry INSTANCE = new PlayerStatEntriesRegistry();
    public static final int DEFAULT_VAULT_STAT_INK = 16777215;

    private final List<Function<Player, PlayerStatEntry>> playerEntryFactories = new ArrayList<>();
    private final List<Function<StatTotals, PlayerStatEntry>> vaultEntryFactories = new ArrayList<>();

    private PlayerStatEntriesRegistry() {
    }

    public PlayerStatEntriesRegistry addPlayerEntry(Function<Player, PlayerStatEntry> factory) {
        this.playerEntryFactories.add(Objects.requireNonNull(factory, "factory"));
        return this;
    }

    public PlayerStatEntriesRegistry addPlayerAttribute(VaultGearAttribute<?> attribute) {
        Objects.requireNonNull(attribute, "attribute");
        return addPlayerAttribute(
                attribute,
                player -> StatisticsElementContainerScreenData.snapshotSum(
                        AttributeSnapshotHelper.getInstance().getSnapshot(player),
                        attribute
                ),
                null,
                null
        );
    }

    public PlayerStatEntriesRegistry addPlayerAttribute(
            VaultGearAttribute<?> attribute,
            ToDoubleFunction<Player> valueFn,
            @Nullable ToDoubleFunction<Player> capFn,
            @Nullable String overrideName
    ) {
        Objects.requireNonNull(attribute, "attribute");
        Objects.requireNonNull(valueFn, "valueFn");
        return addPlayerEntry(player -> {
            String name = overrideName != null ? overrideName : attribute.getReader().getModifierName();
            DoubleSupplier cap = capFn == null ? null : () -> capFn.applyAsDouble(player);
            return new PlayerStatEntry(
                    StatisticsElementContainerScreenData.attributeId(attribute),
                    name,
                    attribute,
                    attribute.getReader().getRgbColor(),
                    () -> valueFn.applyAsDouble(player),
                    cap,
                    amount -> StatisticsElementContainerScreenData.valueText(attribute, amount),
                    () -> ModConfigs.MENU_PLAYER_STAT_DESCRIPTIONS.getModGearAttributeDescriptionFor(attribute.getRegistryName()),
                    List.of()
            );
        });
    }

    public PlayerStatEntriesRegistry addVaultEntry(Function<StatTotals, PlayerStatEntry> factory) {
        this.vaultEntryFactories.add(Objects.requireNonNull(factory, "factory"));
        return this;
    }

    public PlayerStatEntriesRegistry addVaultStat(
            String key,
            String name,
            Function<StatTotals, DoubleSupplier> valueFactory,
            DoubleFunction<String> exactFormat
    ) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(valueFactory, "valueFactory");
        Objects.requireNonNull(exactFormat, "exactFormat");
        return addVaultEntry(totals -> new PlayerStatEntry(
                key,
                name,
                null,
                DEFAULT_VAULT_STAT_INK,
                valueFactory.apply(totals),
                null,
                exactFormat,
                () -> ModConfigs.MENU_PLAYER_STAT_DESCRIPTIONS.getVaultStatDescriptionFor(key),
                List.of()
        ));
    }

    public void appendPlayerEntries(Player player, List<PlayerStatEntry> target) {
        for (Function<Player, PlayerStatEntry> factory : this.playerEntryFactories) {
            PlayerStatEntry entry = factory.apply(player);
            if (entry != null) {
                target.add(entry);
            }
        }
    }

    public void appendVaultEntries(StatTotals totals, List<PlayerStatEntry> target) {
        for (Function<StatTotals, PlayerStatEntry> factory : this.vaultEntryFactories) {
            PlayerStatEntry entry = factory.apply(totals);
            if (entry != null) {
                target.add(entry);
            }
        }
    }
}