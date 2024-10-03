package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders;

import iskallia.vault.block.entity.ModifierWorkbenchTileEntity;
import iskallia.vault.client.gui.screen.block.ModifierWorkbenchScreen;
import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.config.gear.VaultGearWorkbenchConfig;
import iskallia.vault.container.ModifierWorkbenchContainer;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.crafting.ModifierWorkbenchHelper;
import iskallia.vault.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.MixinCraftableModifiersConfigAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.MixinVaultGearWorkbenchAccessor;

import java.util.HashMap;
import java.util.List;

public class CustomVaultGearWorkbenchLoader extends VaultConfigDataLoader<VaultGearWorkbenchConfig> {
    public static final VaultGearWorkbenchConfig instance = new VaultGearWorkbenchConfig(ModItems.SWORD);
    public CustomVaultGearWorkbenchLoader(String namespace) {
        super(instance, "gear_workbench", new HashMap<>(), namespace);
    }

    @Override
    protected void performFinalActions() {
        for(ResourceLocation key : CUSTOM_CONFIGS.keySet()) {
            Item gearItem = ForgeRegistries.ITEMS.getValue(key);
            VaultGearWorkbenchConfig config = CUSTOM_CONFIGS.get(key);
            ((MixinVaultGearWorkbenchAccessor)config).setGearItem(gearItem);

            for(VaultGearModifier.AffixType affix : ((MixinVaultGearWorkbenchAccessor) config).getCraftableModifiers().keySet()) {
                for(VaultGearWorkbenchConfig.CraftableModifierConfig craftableModifierConfig : ((MixinVaultGearWorkbenchAccessor) config).getCraftableModifiers().get(affix)) {
                    ((MixinCraftableModifiersConfigAccessor)craftableModifierConfig).setAffixGroup(VaultGearTierConfig.ModifierAffixTagGroup.ofAffixType(affix));
                    ((MixinCraftableModifiersConfigAccessor)craftableModifierConfig).setGearItem(gearItem);
                }
            }
        }
        VaultGearWorkbenchConfig.registerConfigs();
    }
}
