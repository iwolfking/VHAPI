package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.gear.VaultGearWorkbenchConfig;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = VaultGearWorkbenchConfig.class, remap = false)
public interface MixinVaultGearWorkbenchAccessor {
    @Accessor("gearItem")
    void setGearItem(Item gearItem);

    @Accessor("craftableModifiers")
    Map<VaultGearModifier.AffixType, List<VaultGearWorkbenchConfig.CraftableModifierConfig>> getCraftableModifiers();
}
