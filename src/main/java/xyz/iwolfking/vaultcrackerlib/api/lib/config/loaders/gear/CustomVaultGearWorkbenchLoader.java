package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear;

import iskallia.vault.VaultMod;
import iskallia.vault.block.entity.ModifierWorkbenchTileEntity;
import iskallia.vault.client.gui.screen.block.ModifierWorkbenchScreen;
import iskallia.vault.config.ElixirConfig;
import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.config.gear.VaultGearWorkbenchConfig;
import iskallia.vault.container.ModifierWorkbenchContainer;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.crafting.ModifierWorkbenchHelper;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Loaders;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.ElixirConfigAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.MixinCraftableModifiersConfigAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.MixinVaultGearWorkbenchAccessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomVaultGearWorkbenchLoader extends VaultConfigDataLoader<VaultGearWorkbenchConfig> {
    public static final VaultGearWorkbenchConfig instance = new VaultGearWorkbenchConfig(ModItems.SWORD);

    public Map<Item, VaultGearWorkbenchConfig> WORKBENCH_CONFIGS = new HashMap<>();

    public CustomVaultGearWorkbenchLoader(String namespace) {
        super(instance, "gear_workbench", new HashMap<>(), namespace);
    }

    @Override
    protected void performFinalActions() {

    }

    @SubscribeEvent
    public static void onAddListeners(AddReloadListenerEvent event) {
        event.addListener(Loaders.CUSTOM_VAULT_GEAR_WORKBENCH_LOADER);
    }

    @SubscribeEvent
    public static void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : Loaders.CUSTOM_VAULT_GEAR_WORKBENCH_LOADER.CUSTOM_CONFIGS.keySet()) {
            System.out.println(key);
            Item gearItem = ForgeRegistries.ITEMS.getValue(key);
            System.out.println(gearItem.getRegistryName());
            VaultGearWorkbenchConfig config = Loaders.CUSTOM_VAULT_GEAR_WORKBENCH_LOADER.CUSTOM_CONFIGS.get(key);
            ((MixinVaultGearWorkbenchAccessor) config).setGearItem(gearItem);

            for (VaultGearModifier.AffixType affix : ((MixinVaultGearWorkbenchAccessor) config).getCraftableModifiers().keySet()) {
                System.out.println(affix);
                for (VaultGearWorkbenchConfig.CraftableModifierConfig craftableModifierConfig : ((MixinVaultGearWorkbenchAccessor) config).getCraftableModifiers().get(affix)) {
                    ((MixinCraftableModifiersConfigAccessor) craftableModifierConfig).setAffixGroup(VaultGearTierConfig.ModifierAffixTagGroup.ofAffixType(affix));
                    ((MixinCraftableModifiersConfigAccessor) craftableModifierConfig).setGearItem(gearItem);
                }
            }
            Loaders.CUSTOM_VAULT_GEAR_WORKBENCH_LOADER.WORKBENCH_CONFIGS.put(gearItem, config);
        }
        for(Item item : Loaders.CUSTOM_VAULT_GEAR_WORKBENCH_LOADER.WORKBENCH_CONFIGS.keySet()) {
                ModConfigs.VAULT_GEAR_WORKBENCH_CONFIG.put(item, Loaders.CUSTOM_VAULT_GEAR_WORKBENCH_LOADER.WORKBENCH_CONFIGS.get(item));
        }
    }



}
