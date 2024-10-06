package xyz.iwolfking.vaultcrackerlib.api;

import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.dynamodel.model.item.shield.ShieldModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModDynamicModels;
import iskallia.vault.item.gear.VaultShieldItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.box.MappedWeightedProductEntryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.box.WeightedProductEntryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.CustomGearModelRollRaritiesConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.HandheldModelRegistryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.ShieldModelRegistryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.research.ResearchConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.titles.CustomTitleConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.crystal.VaultCrystalConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.CustomVaultGearLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.CustomVaultGearRecipesLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.CustomVaultGearWorkbenchLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives.BingoConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives.ElixirConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives.MonolithConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives.ScavengerConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.modifiers.VaultModifierConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.modifiers.VaultModifierPoolsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vaultar.VaultAltarIngredientsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.*;
@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LoaderRegistry {



    public static final Map<ResourceLocation, VaultConfigDataLoader<?>> LOADERS = new HashMap<>();

    public static Set<VaultConfigDataLoader<?>> getLoadersByType(Class<?> type) {
        Set<VaultConfigDataLoader<?>> loaders = new HashSet<>();
        for(VaultConfigDataLoader<?> loader : LOADERS.values()) {
            if(loader.getClass().equals(type)) {
                loaders.add(loader);
            }
        }
        return loaders;
    }



    public static void onAddListener(AddReloadListenerEvent event) {
        for(Item item : ModDynamicModels.REGISTRIES.getUniqueItems()) {
            if(item instanceof VaultShieldItem) {
                ShieldModelRegistryConfigLoader<DynamicModelRegistry<ShieldModel>> shieldModelLoader = new ShieldModelRegistryConfigLoader<>("the_vault", (DynamicModelRegistry<ShieldModel>) ModDynamicModels.REGISTRIES.getAssociatedRegistry(item).get(), item);
            }

            HandheldModelRegistryConfigLoader<DynamicModelRegistry<HandHeldModel>> configLoader = new HandheldModelRegistryConfigLoader<DynamicModelRegistry<HandHeldModel>>("the_vault", (DynamicModelRegistry<HandHeldModel>) ModDynamicModels.REGISTRIES.getAssociatedRegistry(item).get(), item);
        }

        for(VaultConfigDataLoader<?> loader : LOADERS.values()) {
            loader.onAddListeners(event);
        }

    }

    public static void onAddClientListener(RegisterClientReloadListenersEvent event) {

    }

    public static final CustomVaultGearLoader CUSTOM_VAULT_GEAR_LOADER = new CustomVaultGearLoader("the_vault");
    public static final CustomVaultGearWorkbenchLoader CUSTOM_VAULT_GEAR_WORKBENCH_LOADER = new CustomVaultGearWorkbenchLoader("the_vault");
    public static final CustomVaultGearRecipesLoader GEAR_RECIPES_LOADER = new CustomVaultGearRecipesLoader( "the_vault");
    public static final ElixirConfigLoader ELIXIR_CONFIG_LOADER = new ElixirConfigLoader( "the_vault");
    public static final ScavengerConfigLoader SCAVENGER_CONFIG_LOADER = new ScavengerConfigLoader( "the_vault");
    public static final MonolithConfigLoader MONOLITH_CONFIG_LOADER = new MonolithConfigLoader( "the_vault");
    public static final BingoConfigLoader BINGO_CONFIG_LOADER = new BingoConfigLoader( "the_vault");
    public static final VaultCrystalConfigLoader VAULT_CRYSTAL_CONFIG_LOADER = new VaultCrystalConfigLoader( "the_vault");
    public static final VaultModifierConfigLoader VAULT_MODIFIER_CONFIG_LOADER = new VaultModifierConfigLoader( "the_vault");
    public static final VaultModifierPoolsConfigLoader VAULT_MODIFIER_POOLS_CONFIG_LOADER = new VaultModifierPoolsConfigLoader( "the_vault");
    public static final ResearchConfigLoader RESEARCH_CONFIG_LOADER = new ResearchConfigLoader( "the_vault");
    public static final CustomGearModelRollRaritiesConfigLoader GEAR_MODEL_ROLL_RARITIES_CONFIG_LOADER = new CustomGearModelRollRaritiesConfigLoader( "the_vault");
    public static final CustomTitleConfigLoader CUSTOM_TITLE_CONFIG_LOADER = new CustomTitleConfigLoader( "the_vault");
    public static final VaultAltarIngredientsConfigLoader VAULT_ALTAR_INGREDIENTS_CONFIG_LOADER = new VaultAltarIngredientsConfigLoader( "the_vault");
    public static final WeightedProductEntryConfigLoader MYSTERY_BOX_CONFIG_LOADER = new WeightedProductEntryConfigLoader( "the_vault", () -> ModConfigs.MYSTERY_BOX.POOL, "mystery_box");
    public static final WeightedProductEntryConfigLoader MYSTERY_EGG_CONFIG_LOADER = new WeightedProductEntryConfigLoader( "the_vault", () -> ModConfigs.MYSTERY_EGG.POOL, "mystery_egg");
    public static final WeightedProductEntryConfigLoader MYSTERY_HOSTILE_EGG_CONFIG_LOADER = new WeightedProductEntryConfigLoader( "the_vault", () -> ModConfigs.MYSTERY_HOSTILE_EGG.POOL, "mystery_hostile_egg");
    public static final MappedWeightedProductEntryConfigLoader MOD_BOX_CONFIG_LOADER = new MappedWeightedProductEntryConfigLoader( "the_vault", () -> ModConfigs.MOD_BOX.POOL, "mod_box");
    public static final Set<HandheldModelRegistryConfigLoader<?>> DYNAMIC_MODEL_REGISTRY_CONFIG_LOADERS  = new HashSet<>();


}
