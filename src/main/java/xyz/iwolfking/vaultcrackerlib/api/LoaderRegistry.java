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
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.bounty.BountyRewardsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.box.MappedWeightedProductEntryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.box.WeightedProductEntryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.card.*;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.expertises.ExpertiseConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.expertises.ExpertisesGUIConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.GearEnchantmentConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.TrinketConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.CustomGearModelRollRaritiesConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.GearModelRollRaritiesConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.HandheldModelRegistryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.ShieldModelRegistryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.loot_table.LootTableConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.palettes.PalettesConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.template_pools.TemplatePoolsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.theme.ThemeConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.general.TooltipConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.general.VaultStatsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.items.AugmentConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.items.InscriptionConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.loot.LegacyLootTableConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.loot.LootInfoConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.misc.ChampionsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.misc.CustomEntitySpawnersLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.misc.EntityGroupsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.recipes.*;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.research.ResearchConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.research.ResearchGUIConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.research.groups.ResearchGroupConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.research.groups.ResearchGroupGUIConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.shops.NormalBlackMarketConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.shops.OmegaBlackMarketConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.shops.ShoppingPedestalConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.skills.AbilitiesConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.skills.AbilitiesGUIConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.skills.descriptions.AbilitiesDescriptionsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.skills.descriptions.SkillDescriptionsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.skills.gates.SkillGatesConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.talents.TalentConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.talents.TalentsGUIConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.titles.CustomTitleConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.tool.PulverizingConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.altar.VaultAltarConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.catalyst.CatalystConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.chest.VaultChestMetaConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.crystal.VaultCrystalCatalystModifiersConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.crystal.VaultCrystalConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.CustomVaultGearLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.CustomVaultGearWorkbenchLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives.BingoConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives.ElixirConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives.MonolithConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives.ScavengerConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.general.VaultGeneralConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.general.VaultLevelsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.mobs.VaultMobsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.modifiers.VaultModifierConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.modifiers.VaultModifierPoolsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.portal.VaultPortalBlockConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vaultar.VaultAltarIngredientsConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.workstation.VaultDiffuserConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.workstation.VaultRecyclerConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.GenFileDataLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.api.util.vhapi.VHAPILoggerUtils;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.UnidentifiedTreasureKeyAccessorConfig;

import java.util.*;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LoaderRegistry {

    /**
     * All normal config data loaders (not under the gen/ file path)
     */
    public static final Map<ResourceLocation, VaultConfigDataLoader<?>> LOADERS = new HashMap<>();
    /**
     * All Gen file data loaders (found under gen/ file path)
     */
    public static final Map<ResourceLocation, GenFileDataLoader<?>> GEN_FILE_LOADERS = new HashMap<>();

    /**
     *
     * @param type The class type of the VaultConfigDataLoader to retrieve.
     * @return A Set of VaultConfigDataLoaders in the registry that match the type passed in.
     */
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

    public static final ResearchGUIConfigLoader RESEARCH_GUI_CONFIG_LOADER = new ResearchGUIConfigLoader("vhapi");
    public static final ResearchGroupConfigLoader RESEARCH_GROUP_CONFIG_LOADER = new ResearchGroupConfigLoader("vhapi");

    public static final ResearchGroupGUIConfigLoader RESEARCH_GROUP_GUI_CONFIG_LOADER = new ResearchGroupGUIConfigLoader("vhapi");

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
    public static final GearModelRollRaritiesConfigLoader GEAR_MODEL_ROLL_RARITIES_CONFIG_LOADER = new GearModelRollRaritiesConfigLoader( "vhapi");
    public static final CustomGearModelRollRaritiesConfigLoader CUSTOM_GEAR_MODEL_ROLL_RARITIES_CONFIG_LOADER = new CustomGearModelRollRaritiesConfigLoader("vhapi");
    public static final GearEnchantmentConfigLoader GEAR_ENCHANTMENT_CONFIG_LOADER = new GearEnchantmentConfigLoader("vhapi");

    //Trinkets
    public static final TrinketConfigLoader TRINKET_CONFIG_LOADER = new TrinketConfigLoader("vhapi");

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
    public static final TooltipConfigLoader TOOLTIP_CONFIG_LOADER = new TooltipConfigLoader("vhapi");

    public static final VaultStatsConfigLoader VAULT_STATS_CONFIG_LOADER = new VaultStatsConfigLoader("vhapi");

    public static final LegacyLootTableConfigLoader LEGACY_LOOT_TABLE_CONFIG_LOADER = new LegacyLootTableConfigLoader("vhapi");

    public static final LootInfoConfigLoader LOOT_INFO_CONFIG_LOADER = new LootInfoConfigLoader("vhapi");

    public static final ChampionsConfigLoader CHAMPIONS_CONFIG_LOADER = new ChampionsConfigLoader("vhapi");

    public static final CustomEntitySpawnersLoader CUSTOM_ENTITY_SPAWNERS_LOADER = new CustomEntitySpawnersLoader("vhapi");

    public static final EntityGroupsConfigLoader ENTITY_GROUPS_CONFIG_LOADER = new EntityGroupsConfigLoader("vhapi");

    //Cards
    public static final CardEssenceExtractorConfigLoader CARD_ESSENCE_EXTRACTOR_CONFIG_LOADER = new CardEssenceExtractorConfigLoader("vhapi");

    public static final BoosterPacksConfigLoader BOOSTER_PACKS_CONFIG_LOADER = new BoosterPacksConfigLoader("vhapi");

    public static final CardModifiersConfigLoader CARD_MODIFIERS_CONFIG_LOADER = new CardModifiersConfigLoader("vhapi");

    public static final DecksConfigLoader DECKS_CONFIG_LOADER = new DecksConfigLoader("vhapi");

    public static final CardTasksConfigLoader CARD_TASKS_CONFIG_LOADER = new CardTasksConfigLoader("vhapi");

    //Expertises
    public static final ExpertiseConfigLoader EXPERTISE_CONFIG_LOADER = new ExpertiseConfigLoader("vhapi");

    public static final ExpertisesGUIConfigLoader EXPERTISES_GUI_CONFIG_LOADER = new ExpertisesGUIConfigLoader("vhapi");

    //Augment
    public static final AugmentConfigLoader AUGMENT_CONFIG_LOADER = new AugmentConfigLoader("vhapi");

    //Inscription
    public static final InscriptionConfigLoader INSCRIPTION_CONFIG_LOADER = new InscriptionConfigLoader("vhapi");

    //Recipes
    public static final CatalystRecipesLoader CATALYST_RECIPES_LOADER = new CatalystRecipesLoader("vhapi");
    public static final InscriptionRecipesLoader INSCRIPTION_RECIPES_LOADER = new InscriptionRecipesLoader("vhapi");
    public static final TrinketRecipesLoader TRINKET_RECIPES_LOADER = new TrinketRecipesLoader("vhapi");
    public static final ToolRecipesLoader TOOL_RECIPES_LOADER = new ToolRecipesLoader("vhapi");

    public static final NormalBlackMarketConfigLoader NORMAL_BLACK_MARKET_CONFIG_LOADER = new NormalBlackMarketConfigLoader("vhapi");

    public static final OmegaBlackMarketConfigLoader OMEGA_BLACK_MARKET_CONFIG_LOADER = new OmegaBlackMarketConfigLoader("vhapi");

    public static final ShoppingPedestalConfigLoader SHOPPING_PEDESTAL_CONFIG_LOADER = new ShoppingPedestalConfigLoader("vhapi");

    //Tool
    public static final PulverizingConfigLoader PULVERIZING_CONFIG_LOADER = new PulverizingConfigLoader("vhapi");

    //Descriptions
    public static final SkillDescriptionsConfigLoader SKILL_DESCRIPTIONS_CONFIG_LOADER = new SkillDescriptionsConfigLoader("vhapi");

    public static final AbilitiesDescriptionsConfigLoader ABILITIES_DESCRIPTIONS_CONFIG_LOADER = new AbilitiesDescriptionsConfigLoader("vhapi");

    public static final SkillGatesConfigLoader SKILL_GATES_CONFIG_LOADER = new SkillGatesConfigLoader("vhapi");

    //Abilities
    public static final AbilitiesConfigLoader ABILITIES_CONFIG_LOADER = new AbilitiesConfigLoader("vhapi");

    public static final AbilitiesGUIConfigLoader ABILITIES_GUI_CONFIG_LOADER = new AbilitiesGUIConfigLoader("vhapi");

    //Talents
    public static final TalentConfigLoader TALENT_CONFIG_LOADER = new TalentConfigLoader("vhapi");

    public static final TalentsGUIConfigLoader TALENTS_GUI_CONFIG_LOADER = new TalentsGUIConfigLoader("vhapi");

    //Altar
    public static final VaultAltarConfigLoader VAULT_ALTAR_CONFIG_LOADER = new VaultAltarConfigLoader("vhapi");

    //Catalyst
    public static final CatalystConfigLoader CATALYST_CONFIG_LOADER = new CatalystConfigLoader("vhapi");

    public static final VaultCrystalCatalystModifiersConfigLoader VAULT_CRYSTAL_CATALYST_MODIFIERS_CONFIG_LOADER = new VaultCrystalCatalystModifiersConfigLoader("vhapi");

    public static final VaultChestMetaConfigLoader VAULT_CHEST_META_CONFIG_LOADER = new VaultChestMetaConfigLoader("vhapi");
    public static final VaultGeneralConfigLoader VAULT_GENERAL_CONFIG_LOADER = new VaultGeneralConfigLoader("vhapi");

    public static final VaultLevelsConfigLoader VAULT_LEVELS_CONFIG_LOADER = new VaultLevelsConfigLoader("vhapi");

    public static final VaultMobsConfigLoader VAULT_MOBS_CONFIG_LOADER = new VaultMobsConfigLoader("vhapi");

    public static final VaultDiffuserConfigLoader VAULT_DIFFUSER_CONFIG_LOADER = new VaultDiffuserConfigLoader("vhapi");

    //Bounty
    public static final BountyRewardsConfigLoader BOUNTY_REWARDS_CONFIG_LOADER = new BountyRewardsConfigLoader("vhapi");
}
