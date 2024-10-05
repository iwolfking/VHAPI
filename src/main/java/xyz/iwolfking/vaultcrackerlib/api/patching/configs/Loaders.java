package xyz.iwolfking.vaultcrackerlib.api.patching.configs;

import iskallia.vault.config.ResearchConfig;
import iskallia.vault.dynamodel.DynamicBakedOverride;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.init.ModDynamicModels;
import org.apache.commons.lang3.reflect.TypeLiteral;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.CustomGearModelRollRaritiesConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.DynamicModelRegistryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.research.ResearchConfigLoader;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Loaders {





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
    public static final Set<DynamicModelRegistryConfigLoader<?>> DYNAMIC_MODEL_REGISTRY_CONFIG_LOADERS  = new HashSet<>();
}
