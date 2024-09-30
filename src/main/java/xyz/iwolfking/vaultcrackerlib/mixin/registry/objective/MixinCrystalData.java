package xyz.iwolfking.vaultcrackerlib.mixin.registry.objective;

import com.google.gson.JsonObject;
import iskallia.vault.VaultMod;
import iskallia.vault.core.data.adapter.basic.TypeSupplierAdapter;
import iskallia.vault.core.vault.modifier.VaultModifierStack;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.crystal.CrystalEntry;
import iskallia.vault.item.crystal.data.serializable.ISerializable;
import iskallia.vault.item.crystal.model.CrystalModel;
import iskallia.vault.item.crystal.modifiers.CrystalModifiers;
import iskallia.vault.item.crystal.objective.CrystalObjective;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import xyz.iwolfking.vaultcrackerlib.api.registry.generic.records.CustomVaultObjectiveEntry;
import xyz.iwolfking.vaultcrackerlib.api.registry.objective.CustomVaultObjectiveRegistry;

@Mixin(value = CrystalData.class, remap = false)
public abstract class MixinCrystalData extends CrystalEntry implements ISerializable<CompoundTag, JsonObject>
{

    @Shadow
    public static TypeSupplierAdapter<CrystalObjective> OBJECTIVE;
    @Shadow public static TypeSupplierAdapter<CrystalModel> MODEL;

    @Shadow private CrystalModifiers modifiers;

    static {
        for(CustomVaultObjectiveEntry entry : CustomVaultObjectiveRegistry.getCustomVaultObjectiveEntries()) {
            OBJECTIVE.register(entry.id(), entry.crystalObjective(), entry.crystalObjectiveSupplier());
        }

        //MODEL.register("unhinged", UnhingedCrystalModel.class, UnhingedCrystalModel::new);
    }
}
