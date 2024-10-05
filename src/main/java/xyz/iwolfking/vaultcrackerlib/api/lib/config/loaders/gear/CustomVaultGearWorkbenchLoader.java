package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear;

import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.config.gear.VaultGearWorkbenchConfig;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.MixinCraftableModifiersConfigAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.MixinVaultGearWorkbenchAccessor;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomVaultGearWorkbenchLoader extends VaultConfigDataLoader<VaultGearWorkbenchConfig> {
    public static final VaultGearWorkbenchConfig instance = new VaultGearWorkbenchConfig(ModItems.SWORD);

    public Map<Item, VaultGearWorkbenchConfig> WORKBENCH_CONFIGS = new HashMap<>();

    public CustomVaultGearWorkbenchLoader(String namespace) {
        super(instance, "gear_workbench", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            Item gearItem = ForgeRegistries.ITEMS.getValue(key);
            VaultGearWorkbenchConfig config = this.CUSTOM_CONFIGS.get(key);
            ((MixinVaultGearWorkbenchAccessor) config).setGearItem(gearItem);

            for (VaultGearModifier.AffixType affix : ((MixinVaultGearWorkbenchAccessor) config).getCraftableModifiers().keySet()) {
                System.out.println(affix);
                for (VaultGearWorkbenchConfig.CraftableModifierConfig craftableModifierConfig : ((MixinVaultGearWorkbenchAccessor) config).getCraftableModifiers().get(affix)) {
                    ((MixinCraftableModifiersConfigAccessor) craftableModifierConfig).setAffixGroup(VaultGearTierConfig.ModifierAffixTagGroup.ofAffixType(affix));
                    ((MixinCraftableModifiersConfigAccessor) craftableModifierConfig).setGearItem(gearItem);
                }
            }
            this.WORKBENCH_CONFIGS.put(gearItem, config);
        }
        for(Item item : this.WORKBENCH_CONFIGS.keySet()) {
                ModConfigs.VAULT_GEAR_WORKBENCH_CONFIG.put(item, this.WORKBENCH_CONFIGS.get(item));
        }
    }



}
