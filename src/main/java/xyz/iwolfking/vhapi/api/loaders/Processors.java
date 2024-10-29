package xyz.iwolfking.vhapi.api.loaders;

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
import net.minecraft.world.item.Item;
import xyz.iwolfking.vhapi.api.loaders.bounty.BountyRewardsConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.box.MappedWeightedProductEntryConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.box.WeightedProductEntryConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.card.*;
import xyz.iwolfking.vhapi.api.loaders.expertises.ExpertiseConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.expertises.ExpertisesGUIConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.gear.CustomVaultGearLoader;
import xyz.iwolfking.vhapi.api.loaders.gear.CustomVaultGearWorkbenchLoader;
import xyz.iwolfking.vhapi.api.loaders.gear.GearEnchantmentConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.gear.TrinketConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.gear.transmog.CustomGearModelRollRaritiesConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.gear.transmog.GearModelRollRaritiesConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.gear.transmog.HandheldModelRegistryConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.gear.transmog.ShieldModelRegistryConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.gen.loot_table.LootTableConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.gen.palettes.PalettesConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.gen.template_pools.TemplatePoolsLoader;
import xyz.iwolfking.vhapi.api.loaders.gen.theme.ThemeConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.general.TooltipConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.general.VaultStatsConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.items.AugmentConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.items.InscriptionConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.lib.core.GenFileProcessor;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.loaders.loot.LegacyLootTableConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.loot.LootInfoConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.misc.ChampionsConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.misc.CustomEntitySpawnersLoader;
import xyz.iwolfking.vhapi.api.loaders.misc.EntityGroupsConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.objectives.BingoConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.objectives.ElixirConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.objectives.MonolithConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.objectives.ScavengerConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.recipes.*;
import xyz.iwolfking.vhapi.api.loaders.research.ResearchConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.research.ResearchGUIConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.research.groups.ResearchGroupConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.research.groups.ResearchGroupGUIConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.shops.NormalBlackMarketConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.shops.OmegaBlackMarketConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.shops.ShoppingPedestalConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.skills.AbilitiesConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.skills.AbilitiesGUIConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.skills.descriptions.AbilitiesDescriptionsConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.skills.descriptions.SkillDescriptionsConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.skills.gates.SkillGatesConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.talents.TalentConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.talents.TalentsGUIConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.titles.CustomTitleConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.tool.PulverizingConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.vault.altar.VaultAltarConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.vault.catalyst.CatalystConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.vault.chest.VaultChestMetaConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.vault.crystal.VaultCrystalCatalystModifiersConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.vault.crystal.VaultCrystalConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.vault.general.VaultGeneralConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.vault.general.VaultLevelsConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.vault.mobs.VaultMobsConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.vault.modifiers.VaultModifierConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.vault.modifiers.VaultModifierPoolsConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.vault.portal.VaultPortalBlockConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.vaultar.VaultAltarIngredientsConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.workstation.VaultDiffuserConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.workstation.VaultRecyclerConfigLoader;
import xyz.iwolfking.vhapi.mixin.accessors.UnidentifiedTreasureKeyAccessorConfig;

import java.util.HashSet;
import java.util.Set;

public class Processors {
    public static class GenerationFileProcessors {
        public static final GenFileProcessor<TemplatePool> GEN_TEMPLATE_POOL_LOADER = new GenFileProcessor<>( TemplatePool.class, "gen/template_pools");
        public static final GenFileProcessor<Theme> GEN_THEME_LOADER = new GenFileProcessor<>( Theme.class,"gen/themes");
        public static final GenFileProcessor<Palette> GEN_PALETTE_LOADER = new GenFileProcessor<>( Palette.class, "gen/palettes");
        public static final GenFileProcessor<LootTable> GEN_LOOT_TABLE_LOADER = new GenFileProcessor<>( LootTable.class, "gen/loot_tables");
    }

    public static class GenerationConfigProcessors {
        public static final PalettesConfigLoader PALETTES_CONFIG_LOADER = new PalettesConfigLoader();
        public static final TemplatePoolsLoader TEMPLATE_POOLS_CONFIG_LOADER = new TemplatePoolsLoader();
        public static final ThemeConfigLoader THEME_CONFIG_LOADER = new ThemeConfigLoader();
        public static final LootTableConfigLoader LOOT_TABLE_CONFIG_LOADER = new LootTableConfigLoader();
    }

    public static class RecipesConfigProcessors {
        public static final CatalystRecipesLoader CATALYST_RECIPES_LOADER = new CatalystRecipesLoader();
        public static final InscriptionRecipesLoader INSCRIPTION_RECIPES_LOADER = new InscriptionRecipesLoader();
        public static final TrinketRecipesLoader TRINKET_RECIPES_LOADER = new TrinketRecipesLoader();
        public static final ToolRecipesLoader TOOL_RECIPES_LOADER = new ToolRecipesLoader();
        public static final CustomVaultGearRecipesLoader GEAR_RECIPES_LOADER = new CustomVaultGearRecipesLoader();
    }


    public static class BuiltInLootBoxConfigProcessors {
        public static final WeightedProductEntryConfigLoader MYSTERY_BOX_CONFIG_LOADER = new WeightedProductEntryConfigLoader(() -> ModConfigs.MYSTERY_BOX.POOL, "mystery_box");
        public static final WeightedProductEntryConfigLoader MYSTERY_EGG_CONFIG_LOADER = new WeightedProductEntryConfigLoader(() -> ModConfigs.MYSTERY_EGG.POOL, "mystery_egg");
        public static final WeightedProductEntryConfigLoader MYSTERY_HOSTILE_EGG_CONFIG_LOADER = new WeightedProductEntryConfigLoader(() -> ModConfigs.MYSTERY_HOSTILE_EGG.POOL, "mystery_hostile_egg");
        public static final WeightedProductEntryConfigLoader UNIDENTIFIED_TREASURE_KEY_CONFIG_LOADER = new WeightedProductEntryConfigLoader(() -> ((UnidentifiedTreasureKeyAccessorConfig)ModConfigs.UNIDENTIFIED_TREASURE_KEY).getTreasureKeys(), "unidentified_treasure_key");
        public static final MappedWeightedProductEntryConfigLoader MOD_BOX_CONFIG_LOADER = new MappedWeightedProductEntryConfigLoader(() -> ModConfigs.MOD_BOX.POOL, "mod_box");
    }

    public static class VaultGearConfigProcessors {
        public static final CustomVaultGearLoader CUSTOM_VAULT_GEAR_LOADER = new CustomVaultGearLoader();
        public static final CustomVaultGearWorkbenchLoader CUSTOM_VAULT_GEAR_WORKBENCH_LOADER = new CustomVaultGearWorkbenchLoader();
        public static final GearModelRollRaritiesConfigLoader GEAR_MODEL_ROLL_RARITIES_CONFIG_LOADER = new GearModelRollRaritiesConfigLoader();
        public static final CustomGearModelRollRaritiesConfigLoader CUSTOM_GEAR_MODEL_ROLL_RARITIES_CONFIG_LOADER = new CustomGearModelRollRaritiesConfigLoader();
        public static final GearEnchantmentConfigLoader GEAR_ENCHANTMENT_CONFIG_LOADER = new GearEnchantmentConfigLoader();
        public static final TrinketConfigLoader TRINKET_CONFIG_LOADER = new TrinketConfigLoader();
    }

    public static class VaultModifierConfigProcessors {
        public static final VaultModifierConfigLoader VAULT_MODIFIER_CONFIG_LOADER = new VaultModifierConfigLoader();
        public static final VaultModifierPoolsConfigLoader VAULT_MODIFIER_POOLS_CONFIG_LOADER = new VaultModifierPoolsConfigLoader();
    }

    public static class ResearchConfigProcessors {
        public static final ResearchConfigLoader RESEARCH_CONFIG_LOADER = new ResearchConfigLoader();
        public static final ResearchGUIConfigLoader RESEARCH_GUI_CONFIG_LOADER = new ResearchGUIConfigLoader();
        public static final ResearchGroupConfigLoader RESEARCH_GROUP_CONFIG_LOADER = new ResearchGroupConfigLoader();
        public static final ResearchGroupGUIConfigLoader RESEARCH_GROUP_GUI_CONFIG_LOADER = new ResearchGroupGUIConfigLoader();
    }

    public static class ObjectiveConfigProcessors {
        public static final ElixirConfigLoader ELIXIR_CONFIG_LOADER = new ElixirConfigLoader();
        public static final ScavengerConfigLoader SCAVENGER_CONFIG_LOADER = new ScavengerConfigLoader();
        public static final MonolithConfigLoader MONOLITH_CONFIG_LOADER = new MonolithConfigLoader();
        public static final BingoConfigLoader BINGO_CONFIG_LOADER = new BingoConfigLoader();
    }

    public static class SkillConfigProcessors {
        public static final SkillGatesConfigLoader SKILL_GATES_CONFIG_LOADER = new SkillGatesConfigLoader();
        public static final AbilitiesConfigLoader ABILITIES_CONFIG_LOADER = new AbilitiesConfigLoader();
        public static final AbilitiesGUIConfigLoader ABILITIES_GUI_CONFIG_LOADER = new AbilitiesGUIConfigLoader();
        public static final TalentConfigLoader TALENT_CONFIG_LOADER = new TalentConfigLoader();
        public static final TalentsGUIConfigLoader TALENTS_GUI_CONFIG_LOADER = new TalentsGUIConfigLoader();
        public static final SkillDescriptionsConfigLoader SKILL_DESCRIPTIONS_CONFIG_LOADER = new SkillDescriptionsConfigLoader();
        public static final AbilitiesDescriptionsConfigLoader ABILITIES_DESCRIPTIONS_CONFIG_LOADER = new AbilitiesDescriptionsConfigLoader();
        public static final ExpertiseConfigLoader EXPERTISE_CONFIG_LOADER = new ExpertiseConfigLoader();
        public static final ExpertisesGUIConfigLoader EXPERTISES_GUI_CONFIG_LOADER = new ExpertisesGUIConfigLoader();
    }

    public static class CardConfigProcessors {
        public static final CardEssenceExtractorConfigLoader CARD_ESSENCE_EXTRACTOR_CONFIG_LOADER = new CardEssenceExtractorConfigLoader();
        public static final BoosterPacksConfigLoader BOOSTER_PACKS_CONFIG_LOADER = new BoosterPacksConfigLoader();
        public static final CardModifiersConfigLoader CARD_MODIFIERS_CONFIG_LOADER = new CardModifiersConfigLoader();
        public static final DecksConfigLoader DECKS_CONFIG_LOADER = new DecksConfigLoader();
        public static final CardTasksConfigLoader CARD_TASKS_CONFIG_LOADER = new CardTasksConfigLoader();
    }

    public static class ToolConfigProcessors {
        public static final PulverizingConfigLoader PULVERIZING_CONFIG_LOADER = new PulverizingConfigLoader();
    }

    public static class VaultAltarIngredientConfigProcessors {
        public static final VaultAltarIngredientsConfigLoader VAULT_ALTAR_INGREDIENTS_CONFIG_LOADER = new VaultAltarIngredientsConfigLoader();
    }

    public static class WorkstationConfigProcessors {
        public static final VaultRecyclerConfigLoader VAULT_RECYCLER_CONFIG_LOADER = new VaultRecyclerConfigLoader();
        public static final VaultDiffuserConfigLoader VAULT_DIFFUSER_CONFIG_LOADER = new VaultDiffuserConfigLoader();
        public static final BountyRewardsConfigLoader BOUNTY_REWARDS_CONFIG_LOADER = new BountyRewardsConfigLoader();
    }

    public static class ShopConfigProcessors {
        public static final NormalBlackMarketConfigLoader NORMAL_BLACK_MARKET_CONFIG_LOADER = new NormalBlackMarketConfigLoader();
        public static final OmegaBlackMarketConfigLoader OMEGA_BLACK_MARKET_CONFIG_LOADER = new OmegaBlackMarketConfigLoader();
        public static final ShoppingPedestalConfigLoader SHOPPING_PEDESTAL_CONFIG_LOADER = new ShoppingPedestalConfigLoader();
    }

    public static class GeneralVaultConfigProcessors {
        public static final VaultAltarConfigLoader VAULT_ALTAR_CONFIG_LOADER = new VaultAltarConfigLoader();
        public static final CatalystConfigLoader CATALYST_CONFIG_LOADER = new CatalystConfigLoader();
        public static final VaultCrystalCatalystModifiersConfigLoader VAULT_CRYSTAL_CATALYST_MODIFIERS_CONFIG_LOADER = new VaultCrystalCatalystModifiersConfigLoader("vhapi");
        public static final VaultChestMetaConfigLoader VAULT_CHEST_META_CONFIG_LOADER = new VaultChestMetaConfigLoader();
        public static final VaultGeneralConfigLoader VAULT_GENERAL_CONFIG_LOADER = new VaultGeneralConfigLoader();
        public static final VaultLevelsConfigLoader VAULT_LEVELS_CONFIG_LOADER = new VaultLevelsConfigLoader();
        public static final VaultMobsConfigLoader VAULT_MOBS_CONFIG_LOADER = new VaultMobsConfigLoader();
        public static final AugmentConfigLoader AUGMENT_CONFIG_LOADER = new AugmentConfigLoader();
        public static final InscriptionConfigLoader INSCRIPTION_CONFIG_LOADER = new InscriptionConfigLoader();
        public static final VaultPortalBlockConfigLoader VAULT_PORTAL_BLOCK_CONFIG_LOADER = new VaultPortalBlockConfigLoader();
        public static final TooltipConfigLoader TOOLTIP_CONFIG_LOADER = new TooltipConfigLoader();
        public static final VaultStatsConfigLoader VAULT_STATS_CONFIG_LOADER = new VaultStatsConfigLoader();
        public static final LegacyLootTableConfigLoader LEGACY_LOOT_TABLE_CONFIG_LOADER = new LegacyLootTableConfigLoader();
        public static final LootInfoConfigLoader LOOT_INFO_CONFIG_LOADER = new LootInfoConfigLoader();
        public static final ChampionsConfigLoader CHAMPIONS_CONFIG_LOADER = new ChampionsConfigLoader();
        public static final CustomEntitySpawnersLoader CUSTOM_ENTITY_SPAWNERS_LOADER = new CustomEntitySpawnersLoader();
        public static final EntityGroupsConfigLoader ENTITY_GROUPS_CONFIG_LOADER = new EntityGroupsConfigLoader();
        public static final CustomTitleConfigLoader CUSTOM_TITLE_CONFIG_LOADER = new CustomTitleConfigLoader();
        public static final VaultCrystalConfigLoader VAULT_CRYSTAL_CONFIG_LOADER = new VaultCrystalConfigLoader();
    }

    public static class TransmogConfigProcessors {
        public static Set<VaultConfigProcessor<?>>getStandardTransmogProcessors() {
            Set<VaultConfigProcessor<?>> result = new HashSet<>();
            for(Item item : ModDynamicModels.REGISTRIES.getUniqueItems()) {
                if(item instanceof VaultShieldItem) {
                    ShieldModelRegistryConfigLoader<DynamicModelRegistry<ShieldModel>> shieldTransmogLoader = new ShieldModelRegistryConfigLoader<>("vhapi", (DynamicModelRegistry<ShieldModel>) ModDynamicModels.REGISTRIES.getAssociatedRegistry(item).get(), item);
                    result.add(shieldTransmogLoader);

                }
                else {
                    HandheldModelRegistryConfigLoader<DynamicModelRegistry<HandHeldModel>> transmogLoader = new HandheldModelRegistryConfigLoader<>((DynamicModelRegistry<HandHeldModel>) ModDynamicModels.REGISTRIES.getAssociatedRegistry(item).get(), item);
                    result.add(transmogLoader);
                }
            }
            return result;
        }
    }
}
