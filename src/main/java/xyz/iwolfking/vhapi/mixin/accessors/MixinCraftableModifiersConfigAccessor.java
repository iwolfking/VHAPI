package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.gear.VaultGearTierConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "iskallia.vault.config.gear.VaultGearWorkbenchConfig$CraftableModifierConfig", remap = false)
public interface MixinCraftableModifiersConfigAccessor {
    @Accessor("gearItem")
    void setGearItem(Item item);

    @Accessor("affixGroup")
    void setAffixGroup(VaultGearTierConfig.ModifierAffixTagGroup item);

}
