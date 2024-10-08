package xyz.iwolfking.vaultcrackerlib.api;

import com.mojang.logging.LogUtils;
import iskallia.vault.core.world.generator.theme.Theme;
import iskallia.vault.core.world.loot.LootTable;
import iskallia.vault.core.world.processor.Palette;
import iskallia.vault.core.world.template.data.TemplatePool;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.dynamodel.model.item.shield.ShieldModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModDynamicModels;
import iskallia.vault.item.gear.VaultShieldItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import xyz.iwolfking.vaultcrackerlib.api.events.ConfigDataLoaderEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.box.MappedWeightedProductEntryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.box.WeightedProductEntryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.CustomGearModelRollRaritiesConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.HandheldModelRegistryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.ShieldModelRegistryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.loot_table.LootTableConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.palettes.PalettesConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.template_pools.TemplatePoolsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.theme.ThemeConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.research.ResearchConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.titles.CustomTitleConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.crystal.VaultCrystalConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.CustomVaultGearLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.recipes.CustomVaultGearRecipesLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.CustomVaultGearWorkbenchLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives.BingoConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives.ElixirConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives.MonolithConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives.ScavengerConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.modifiers.VaultModifierConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.modifiers.VaultModifierPoolsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.portal.VaultPortalBlockConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vaultar.VaultAltarIngredientsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.workstation.VaultRecyclerConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.GenFileDataLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.api.util.vhapi.VHAPILoggerUtils;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.UnidentifiedTreasureKeyAccessorConfig;

import java.util.*;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LoaderRegistry {

    Logger LOGGER = LogUtils.getLogger();

    public static final Map<ResourceLocation, VaultConfigDataLoader<?>> LOADERS = new HashMap<>();
    public static final Map<ResourceLocation, GenFileDataLoader<?>> GEN_FILE_LOADERS = new HashMap<>();

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
        VHAPILoggerUtils.info("Registering datapack vault config listeners!");
        //Register a loader for each gear item present in the model registry.
        for(Item item : ModDynamicModels.REGISTRIES.getUniqueItems()) {
            if(item instanceof VaultShieldItem) {
                VHAPILoggerUtils.debug("Registered a custom shield transmog data listener for" + item.getRegistryName());
                ShieldModelRegistryConfigLoader<DynamicModelRegistry<ShieldModel>> shieldModelLoader = new ShieldModelRegistryConfigLoader<>("vhapi", (DynamicModelRegistry<ShieldModel>) ModDynamicModels.REGISTRIES.getAssociatedRegistry(item).get(), item);
                continue;
            }
            VHAPILoggerUtils.debug("Registered a custom transmog data listener for" + item.getRegistryName());
            HandheldModelRegistryConfigLoader<DynamicModelRegistry<HandHeldModel>> configLoader = new HandheldModelRegistryConfigLoader<DynamicModelRegistry<HandHeldModel>>("vhapi", (DynamicModelRegistry<HandHeldModel>) ModDynamicModels.REGISTRIES.getAssociatedRegistry(item).get(), item);
        }

        GEN_PALETTE_LOADER.onAddListeners(event);
        GEN_TEMPLATE_POOL_LOADER.onAddListeners(event);
        GEN_LOOT_TABLE_LOADER.onAddListeners(event);


        //Load all normal config loaders
        for(VaultConfigDataLoader<?> loader : LOADERS.values()) {
            VHAPILoggerUtils.debug("Registered " + loader.getName() + " data listener.");
            if(loader instanceof ThemeConfigLoader) {
                continue;
            }
            MinecraftForge.EVENT_BUS.post(new ConfigDataLoaderEvent.Init(loader));
            loader.onAddListeners(event);
        }

        //Themes rely on Template Pools to already be registered, so we must make sure they load last.
        GEN_THEME_LOADER.onAddListeners(event);
        THEME_CONFIG_LOADER.onAddListeners(event);

        MinecraftForge.EVENT_BUS.post(new ConfigDataLoaderEvent.Finish(LOADERS));
        VHAPILoggerUtils.info("Finished registering listeners. Registered " + LOADERS.size() + " listeners.");
    }


    //Objective Configs
    public static final ElixirConfigLoader ELIXIR_CONFIG_LOADER = new ElixirConfigLoader( "vhapi");
    public static final ScavengerConfigLoader SCAVENGER_CONFIG_LOADER = new ScavengerConfigLoader( "vhapi");
    public static final MonolithConfigLoader MONOLITH_CONFIG_LOADER = new MonolithConfigLoader( "vhapi");
    public static final BingoConfigLoader BINGO_CONFIG_LOADER = new BingoConfigLoader( "vhapi");

    //Research Configs

    public static final ResearchConfigLoader RESEARCH_CONFIG_LOADER = new ResearchConfigLoader( "vhapi");

    //Title Configs
    public static final CustomTitleConfigLoader CUSTOM_TITLE_CONFIG_LOADER = new CustomTitleConfigLoader( "vhapi");

    //Vault Crystal Configs
    public static final VaultCrystalConfigLoader VAULT_CRYSTAL_CONFIG_LOADER = new VaultCrystalConfigLoader( "vhapi");




    //Vault Altar Configs
    public static final VaultAltarIngredientsConfigLoader VAULT_ALTAR_INGREDIENTS_CONFIG_LOADER = new VaultAltarIngredientsConfigLoader( "vhapi");

    //Vault Modifier Configs
    public static final VaultModifierConfigLoader VAULT_MODIFIER_CONFIG_LOADER = new VaultModifierConfigLoader( "vhapi");
    public static final VaultModifierPoolsConfigLoader VAULT_MODIFIER_POOLS_CONFIG_LOADER = new VaultModifierPoolsConfigLoader( "vhapi");

    //Vault Gear Configs
    public static final CustomVaultGearLoader CUSTOM_VAULT_GEAR_LOADER = new CustomVaultGearLoader("vhapi");
    public static final CustomVaultGearWorkbenchLoader CUSTOM_VAULT_GEAR_WORKBENCH_LOADER = new CustomVaultGearWorkbenchLoader("vhapi");
    public static final CustomVaultGearRecipesLoader GEAR_RECIPES_LOADER = new CustomVaultGearRecipesLoader( "vhapi");
    public static final CustomGearModelRollRaritiesConfigLoader GEAR_MODEL_ROLL_RARITIES_CONFIG_LOADER = new CustomGearModelRollRaritiesConfigLoader( "vhapi");

    //"Loot Box" Configs
    public static final WeightedProductEntryConfigLoader MYSTERY_BOX_CONFIG_LOADER = new WeightedProductEntryConfigLoader( "vhapi", () -> ModConfigs.MYSTERY_BOX.POOL, "mystery_box");
    public static final WeightedProductEntryConfigLoader MYSTERY_EGG_CONFIG_LOADER = new WeightedProductEntryConfigLoader( "vhapi", () -> ModConfigs.MYSTERY_EGG.POOL, "mystery_egg");
    public static final WeightedProductEntryConfigLoader MYSTERY_HOSTILE_EGG_CONFIG_LOADER = new WeightedProductEntryConfigLoader( "vhapi", () -> ModConfigs.MYSTERY_HOSTILE_EGG.POOL, "mystery_hostile_egg");
    public static final WeightedProductEntryConfigLoader UNIDENTIFIED_TREASURE_KEY_CONFIG_LOADER = new WeightedProductEntryConfigLoader( "vhapi", () -> ((UnidentifiedTreasureKeyAccessorConfig)ModConfigs.UNIDENTIFIED_TREASURE_KEY).getTreasureKeys(), "unidentified_treasure_key");
    public static final MappedWeightedProductEntryConfigLoader MOD_BOX_CONFIG_LOADER = new MappedWeightedProductEntryConfigLoader( "vhapi", () -> ModConfigs.MOD_BOX.POOL, "mod_box");

    //Vault Generation Config Files
    public static final PalettesConfigLoader PALETTES_CONFIG_LOADER = new PalettesConfigLoader( "vhapi");
    public static final TemplatePoolsConfigLoader TEMPLATE_POOLS_CONFIG_LOADER = new TemplatePoolsConfigLoader( "vhapi");
    public static final ThemeConfigLoader THEME_CONFIG_LOADER = new ThemeConfigLoader( "vhapi");
    public static final LootTableConfigLoader LOOT_TABLE_CONFIG_LOADER = new LootTableConfigLoader( "vhapi");

    //Workstations
    public static final VaultRecyclerConfigLoader VAULT_RECYCLER_CONFIG_LOADER = new VaultRecyclerConfigLoader("vhapi");

    //Vault Generation Files
    public static final GenFileDataLoader<TemplatePool> GEN_TEMPLATE_POOL_LOADER = new GenFileDataLoader<>( TemplatePool.class, "gen/template_pools", new HashMap<>(), "vhapi");
    public static final GenFileDataLoader<Theme> GEN_THEME_LOADER = new GenFileDataLoader<>( Theme.class, "gen/themes", new HashMap<>(), "vhapi");
    public static final GenFileDataLoader<Palette> GEN_PALETTE_LOADER = new GenFileDataLoader<>( Palette.class, "gen/palettes", new HashMap<>(), "vhapi");
    public static final GenFileDataLoader<LootTable> GEN_LOOT_TABLE_LOADER = new GenFileDataLoader<>( LootTable.class, "gen/loot_tables", new HashMap<>(), "vhapi");

    //Misc Configs
    public static final VaultPortalBlockConfigLoader VAULT_PORTAL_BLOCK_CONFIG_LOADER = new VaultPortalBlockConfigLoader("vhapi");

}
