package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.config.gear.VaultGearWorkbenchConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = VaultGearWorkbenchConfig.CraftableModifierConfig.class, remap = false)
public interface MixinCraftableModifiersConfigAccessor {
    @Accessor("gearItem")
    void setGearItem(Item item);

    @Accessor("affixGroup")
    void setAffixGroup(VaultGearTierConfig.ModifierAffixTagGroup item);

}
