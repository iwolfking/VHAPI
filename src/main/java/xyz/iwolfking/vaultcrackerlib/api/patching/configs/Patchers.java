package xyz.iwolfking.vaultcrackerlib.api.patching.configs;

import com.google.gson.JsonElement;
import iskallia.vault.config.*;
import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.config.entry.ResearchGroupStyle;
import iskallia.vault.config.entry.SkillStyle;
import iskallia.vault.config.entry.recipe.ConfigCatalystRecipe;
import iskallia.vault.config.entry.recipe.ConfigGearRecipe;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.config.gear.VaultGearModificationConfig;
import iskallia.vault.config.gear.VaultGearWorkbenchConfig;
import iskallia.vault.config.recipe.CatalystRecipesConfig;
import iskallia.vault.config.recipe.GearRecipesConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.core.vault.player.Completion;
import iskallia.vault.core.vault.stat.VaultChestType;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.core.world.loot.LootTable;
import iskallia.vault.gear.crafting.recipe.VaultForgeRecipe;
import iskallia.vault.item.tool.Pulverizing;
import iskallia.vault.research.group.ResearchGroup;
import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;
import iskallia.vault.skill.SkillGates;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.util.EnchantmentCost;
import iskallia.vault.util.VaultRarity;
import iskallia.vault.util.data.WeightedList;
import jdk.jfr.Experimental;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.*;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.custom.altar.VaultAltarIngredientInjector;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.custom.market.BlackMarketPatcher;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.custom.market.OmegaBlackMarketPatcher;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Patchers {

    public static Set<BasicPatcher> PATCHERS = new HashSet<>();

    //Confirmed Working

    public static final SetInjector<ModResearch> MOD_RESEARCH_PATCHER = new SetInjector<>();
    public static final SetInjector<CustomResearch> CUSTOM_RESEARCH_PATCHER = new SetInjector<>();
    public static final MapInjector<String, SkillStyle> RESEARCH_GUI_PATCHER = new MapInjector<>();
    public static final MapInjector<String, ResearchGroupStyle> RESEARCH_GROUP_GUI_PATCHER = new MapInjector<>();
    public static final MapInjector<String, ResearchGroup> RESEARCH_GROUP_PATCHER = new MapInjector<>();
    public static final MapInjector<String, JsonElement> SKILL_DESCRIPTIONS_PATCHER = new MapInjector<>();
    public static final MapInjector<ResourceLocation, Integer> VAULT_DIFFUSER_PATCHER = new MapInjector<>();
    public static final WeightedListInjector<ProductEntry> MYSTERY_BOX_PATCHER = new WeightedListInjector<>();
    public static final WeightedListInjector<ProductEntry> MYSTERY_EGG_PATCHER = new WeightedListInjector<>();
    public static final WeightedListInjector<ProductEntry> MYSTERY_EGG_HOSTILE_PATCHER = new WeightedListInjector<>();
    public static final WeightedListInjector<ProductEntry> UNIDENTIFIED_TREASURE_KEY_PATCHER = new WeightedListInjector<>();
    public static final MapInjector<String, WeightedList<ProductEntry>> MOD_BOX_PATCHER = new MapInjector<>();
    public static final SetInjector<String> ITEM_BLACKLIST_PATCHER = new SetInjector<>();
    public static final SetInjector<String> BLOCK_BLACKLIST_PATCHER = new SetInjector<>();
    public static final MapInjector<EntityPredicate, Float> ENTITY_CHAMPION_CHANCE_PATCHER = new MapInjector<>();
    public static final SetInjector<TooltipConfig.TooltipEntry> TOOLTIP_PATCHER = new SetInjector<>();
    public static final MapInjector<PlayerTitlesConfig.Affix, Map<String, PlayerTitlesConfig.Title>> PLAYER_TITLES_PATCHER = new MapInjector<>();
    public static final SetInjector<AscensionForgeConfig.AscensionForgeListing> ASCENSION_FORGE_PATCHER = new SetInjector<>();
    public static final MapInjector<Enchantment, EnchantmentCost> VAULT_GEAR_ENCHANTMENT_PATCHER = new MapInjector<>();

    public static final MapInjector<Block, Map<VaultRarity, Double>> VAULT_CHEST_CATALYST_CHANCE_PATCHER = new MapInjector<>();

    public static final SetInjector<VaultLevelsConfig.VaultLevelMeta> VAULT_LEVELS_PATCHER = new SetInjector<>();
    public static final ConstantInjector<Integer> MAX_VAULT_LEVEL_PATCHER = new ConstantInjector<>();


    //Testing

    public static final MapInjector<VaultChestType, Map<VaultRarity, Float>> VAULT_STATS_CHEST_PATCHER = new MapInjector<>();
    public static final MapInjector<ResourceLocation, Float> VAULT_STATS_BLOCKS_PATCHER = new MapInjector<>();
    public static final MapInjector<ResourceLocation, Float> VAULT_STATS_MOBS_PATCHER = new MapInjector<>();
    public static final MapInjector<String, Map<Completion, Float>> VAULT_STATS_COMPLETION_PATCHER = new MapInjector<>();


    //Untested
    public static final MapInjector<ResourceLocation, LevelEntryList<VaultCrystalConfig.SealEntry>> CRYSTAL_SEALS_PATCHER = new MapInjector<>();

    public static final MapInjector<ResourceLocation, LevelEntryList<VaultModifierPoolsConfig.Level>> VAULT_MODIFIER_POOLS_PATCHER = new MapInjector<>();
    public static final MapInjector<String, SkillGates.Entry> SKILL_GATES_PATCHER = new MapInjector<>();
    public static final MapInjector<ResourceLocation, Map<String, Integer>> AUGMENT_PATCHER = new MapInjector<>();

    public static final MapInjector<String, TextColor> COLORS_PATCHER = new MapInjector<>();

    public static final MapInjector<String, List<CustomEntitySpawnerConfig.SpawnerGroup>> CUSTOM_ENTITY_SPAWNER_GROUPS_PATCHER = new MapInjector<>();
    public static final SetInjector<Skill> EXPERTISES_PATCHER = new SetInjector<>();
    public static final SetInjector<Skill> TALENTS_PATCHER = new SetInjector<>();
    public static final SetInjector<Skill> ABILITIES_PATCHER = new SetInjector<>();
    public static final MapInjector<String, AbilitiesGUIConfig.AbilityStyle> ABILITIES_GUI_PATCHER = new MapInjector<>();


    public static final MapInjector<ResourceLocation, Integer> INSCRIPTION_POOL_TO_MODEL_PATCHER = new MapInjector<>();

    public static final MapInjector<ResourceLocation, LootInfoConfig.LootInfo> LOOT_INFO_PATCHER = new MapInjector<>();

    public static final MapInjector<String, String> MENU_PLAYER_STAT_PROMINENT_PATCHER = new MapInjector<>();
    public static final MapInjector<ResourceLocation, String> MENU_PLAYER_STAT_GEAR_PATCHER = new MapInjector<>();
    public static final MapInjector<String, String> MENU_PLAYER_STATS_VAULT_PATCHER = new MapInjector<>();


    public static final MapInjector<String, AbilitiesDescriptionsConfig.DescriptionData> ABILITIES_DESCRIPTIONS_PATCHER = new MapInjector<>();
    public static final SetInjector<VaultAltarConfig.Interface> VAULT_ALTAR_PATCHER = new SetInjector<>();
    public static final MapInjector<ResourceLocation, List<LootTable.Entry>> TOOL_PULVERIZING_PATCHER = new MapInjector<>();


    //Unfinished


    public static final SetInjector<SoulShardConfig.Trades> NORMAL_BLACK_MARKET_TRADES_PATCHER = new BlackMarketPatcher();
    public static final SetInjector<OmegaSoulShardConfig.Trades> OMEGA_BLACK_MARKET_TRADES_PATCHER = new OmegaBlackMarketPatcher<>();
    @Experimental
    public static final MapInjector<ResourceLocation, TrinketConfig.Trinket> TRINKET_PATCHER = new MapInjector<>();


    //Not working
    public static final MapInjector<String, SkillStyle> EXPERTISES_GUI_PATCHER = new MapInjector<>();

    public static final MapInjector<String, SkillStyle> TALENTS_GUI_PATCHER = new MapInjector<>();
    public static final LeveledWeightedListInjector<AltarIngredientEntry, String> RESOURCE_VAULT_ALTAR_INGREDIENT_PATCHER = new LeveledWeightedListInjector<>("resource");
    public static final LeveledWeightedListInjector<AltarIngredientEntry, String> MOB_VAULT_ALTAR_INGREDIENT_PATCHER = new LeveledWeightedListInjector<>("mob");
    public static final LeveledWeightedListInjector<AltarIngredientEntry, String> FARMABLE_VAULT_ALTAR_INGREDIENT_PATCHER = new LeveledWeightedListInjector<>("farmable");
    public static final LeveledWeightedListInjector<AltarIngredientEntry, String> MISC_VAULT_ALTAR_INGREDIENT_PATCHER = new LeveledWeightedListInjector<>("misc");
    public static final VaultAltarIngredientInjector VAULT_ALTAR_INGREDIENT_PATCHER = new VaultAltarIngredientInjector();
    public static final SetInjector<ConfigGearRecipe> VAULT_GEAR_RECIPE_PATCHER = new SetInjector<>();

























    static {
        PATCHERS.add(MOD_RESEARCH_PATCHER);
        PATCHERS.add(CUSTOM_RESEARCH_PATCHER);
        PATCHERS.add(RESEARCH_GUI_PATCHER);
        PATCHERS.add(RESEARCH_GROUP_GUI_PATCHER);
        PATCHERS.add(RESEARCH_GROUP_PATCHER);
        PATCHERS.add(SKILL_DESCRIPTIONS_PATCHER);
        PATCHERS.add(TRINKET_PATCHER);
        PATCHERS.add(VAULT_DIFFUSER_PATCHER);
        PATCHERS.add(ITEM_BLACKLIST_PATCHER);
        PATCHERS.add(BLOCK_BLACKLIST_PATCHER);
        PATCHERS.add(VAULT_STATS_CHEST_PATCHER);
        PATCHERS.add(VAULT_STATS_BLOCKS_PATCHER);
        PATCHERS.add(VAULT_STATS_MOBS_PATCHER);
        PATCHERS.add(VAULT_STATS_COMPLETION_PATCHER);
        PATCHERS.add(MYSTERY_BOX_PATCHER);
        PATCHERS.add(MYSTERY_EGG_PATCHER);
        PATCHERS.add(MYSTERY_EGG_HOSTILE_PATCHER);
        PATCHERS.add(UNIDENTIFIED_TREASURE_KEY_PATCHER);
        PATCHERS.add(MOD_BOX_PATCHER);
        PATCHERS.add(CRYSTAL_SEALS_PATCHER);
        PATCHERS.add(ENTITY_CHAMPION_CHANCE_PATCHER);
        PATCHERS.add(VAULT_MODIFIER_POOLS_PATCHER);
        PATCHERS.add(TOOLTIP_PATCHER);
        PATCHERS.add(SKILL_GATES_PATCHER);
        PATCHERS.add(NORMAL_BLACK_MARKET_TRADES_PATCHER);
        PATCHERS.add(OMEGA_BLACK_MARKET_TRADES_PATCHER);
        PATCHERS.add(PLAYER_TITLES_PATCHER);
        PATCHERS.add(ASCENSION_FORGE_PATCHER);
        PATCHERS.add(AUGMENT_PATCHER);
        PATCHERS.add(COLORS_PATCHER);
        PATCHERS.add(CUSTOM_ENTITY_SPAWNER_GROUPS_PATCHER);
        PATCHERS.add(EXPERTISES_PATCHER);
        PATCHERS.add(EXPERTISES_GUI_PATCHER);
        PATCHERS.add(VAULT_GEAR_ENCHANTMENT_PATCHER);
        PATCHERS.add(INSCRIPTION_POOL_TO_MODEL_PATCHER);
        PATCHERS.add(LOOT_INFO_PATCHER);
        PATCHERS.add(MENU_PLAYER_STAT_GEAR_PATCHER);
        PATCHERS.add(MENU_PLAYER_STATS_VAULT_PATCHER);
        PATCHERS.add(MENU_PLAYER_STAT_PROMINENT_PATCHER);
        PATCHERS.add(TALENTS_PATCHER);
        PATCHERS.add(TALENTS_GUI_PATCHER);
        PATCHERS.add(ABILITIES_PATCHER);
        PATCHERS.add(ABILITIES_GUI_PATCHER);
        PATCHERS.add(ABILITIES_DESCRIPTIONS_PATCHER);
        PATCHERS.add(VAULT_ALTAR_PATCHER);
        PATCHERS.add(VAULT_CHEST_CATALYST_CHANCE_PATCHER);
        PATCHERS.add(VAULT_LEVELS_PATCHER);
        PATCHERS.add(MAX_VAULT_LEVEL_PATCHER);
        PATCHERS.add(RESOURCE_VAULT_ALTAR_INGREDIENT_PATCHER);
        PATCHERS.add(MOB_VAULT_ALTAR_INGREDIENT_PATCHER);
        PATCHERS.add(FARMABLE_VAULT_ALTAR_INGREDIENT_PATCHER);
        PATCHERS.add(MISC_VAULT_ALTAR_INGREDIENT_PATCHER);
        PATCHERS.add(VAULT_ALTAR_INGREDIENT_PATCHER);
        PATCHERS.add(TOOL_PULVERIZING_PATCHER);
        PATCHERS.add(VAULT_GEAR_RECIPE_PATCHER);
    }

}
