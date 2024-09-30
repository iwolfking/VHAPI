package xyz.iwolfking.vaultcrackerlib.api.registry.generic.records;

import iskallia.vault.core.data.key.SupplierKey;
import iskallia.vault.core.vault.objective.Objective;
import iskallia.vault.item.crystal.objective.CrystalObjective;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public record CustomVaultObjectiveEntry(String id, String name, Class<? extends CrystalObjective> crystalObjective, Supplier<? extends CrystalObjective> crystalObjectiveSupplier, SupplierKey<Objective> key, ItemLike crate) {
}
