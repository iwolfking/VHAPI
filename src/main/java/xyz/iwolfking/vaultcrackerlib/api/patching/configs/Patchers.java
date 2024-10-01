package xyz.iwolfking.vaultcrackerlib.api.patching.configs;

import com.google.gson.JsonElement;
import iskallia.vault.config.*;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.config.entry.ResearchGroupStyle;
import iskallia.vault.config.entry.SkillStyle;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.config.gear.VaultGearEnchantmentConfig;
import iskallia.vault.core.vault.player.Completion;
import iskallia.vault.core.vault.stat.VaultChestType;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.research.group.ResearchGroup;
import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;
import iskallia.vault.skill.SkillGates;
import iskallia.vault.util.VaultRarity;
import iskallia.vault.util.data.WeightedList;
import jdk.jfr.Experimental;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.BasicPatcher;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.MapInjector;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.SetInjector;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.LootBoxConfigInjector;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.custom.BlackMarketPatcher;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.custom.OmegaBlackMarketPatcher;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Patchers {

    public static Set<BasicPatcher> PATCHERS = new HashSet<>();
    public static final SetInjector<ModResearch> MOD_RESEARCH_PATCHER = new SetInjector<>();
    public static final SetInjector<CustomResearch> CUSTOM_RESEARCH_PATCHER = new SetInjector<>();


    public static final MapInjector<String, SkillStyle> RESEARCH_GUI_PATCHER = new MapInjector<>();
    public static final MapInjector<String, ResearchGroupStyle> RESEARCH_GROUP_GUI_PATCHER = new MapInjector<>();
    public static final MapInjector<String, ResearchGroup> RESEARCH_GROUP_PATCHER = new MapInjector<>();
    public static final MapInjector<String, JsonElement> SKILL_DESCRIPTIONS_PATCHER = new MapInjector<>();
    public static final MapInjector<ResourceLocation, Integer> VAULT_DIFFUSER_PATCHER = new MapInjector<>();
    public static final MapInjector<VaultChestType, Map<VaultRarity, Float>> VAULT_STATS_CHEST_PATCHER = new MapInjector<>();
    public static final MapInjector<ResourceLocation, Float> VAULT_STATS_BLOCKS_PATCHER = new MapInjector<>();
    public static final MapInjector<ResourceLocation, Float> VAULT_STATS_MOBS_PATCHER = new MapInjector<>();
    public static final MapInjector<String, Map<Completion, Float>> VAULT_STATS_COMPLETION_PATCHER = new MapInjector<>();

    public static final LootBoxConfigInjector<ProductEntry> MYSTERY_BOX_PATCHER = new LootBoxConfigInjector<>();
    public static final LootBoxConfigInjector<ProductEntry> MYSTERY_EGG_PATCHER = new LootBoxConfigInjector<>();
    public static final LootBoxConfigInjector<ProductEntry> MYSTERY_EGG_HOSTILE_PATCHER = new LootBoxConfigInjector<>();

    public static final SetInjector<String> ITEM_BLACKLIST_PATCHER = new SetInjector<>();
    public static final SetInjector<String> BLOCK_BLACKLIST_PATCHER = new SetInjector<>();

    public static final MapInjector<String, WeightedList<ProductEntry>> MOD_BOX_PATCHER = new MapInjector<>();
    public static final MapInjector<ResourceLocation, LevelEntryList<VaultCrystalConfig.SealEntry>> CRYSTAL_SEALS_PATCHER = new MapInjector<>();
    public static final MapInjector<EntityPredicate, Float> ENTITY_CHAMPION_CHANCE_PATCHER = new MapInjector<>();
    public static final MapInjector<ResourceLocation, LevelEntryList<VaultModifierPoolsConfig.Level>> VAULT_MODIFIER_POOLS_PATCHER = new MapInjector<>();

    public static final SetInjector<TooltipConfig.TooltipEntry> TOOLTIP_PATCHER = new SetInjector<>();
    public static final SetInjector<SoulShardConfig.Trades> NORMAL_BLACK_MARKET_TRADES_PATCHER = new BlackMarketPatcher();
    public static final SetInjector<OmegaSoulShardConfig.Trades> OMEGA_BLACK_MARKET_TRADES_PATCHER = new OmegaBlackMarketPatcher<>();

    public static final MapInjector<String, SkillGates.Entry> SKILL_GATES_PATCHER = new MapInjector<>();

    public static final MapInjector<PlayerTitlesConfig.Affix, Map<String, PlayerTitlesConfig.Title>> PLAYER_TITLES_PATCHER = new MapInjector<>();


    @Experimental
    public static final MapInjector<ResourceLocation, TrinketConfig.Trinket> TRINKET_PATCHER = new MapInjector<>();




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
        PATCHERS.add(MOD_BOX_PATCHER);
        PATCHERS.add(CRYSTAL_SEALS_PATCHER);
        PATCHERS.add(ENTITY_CHAMPION_CHANCE_PATCHER);
        PATCHERS.add(VAULT_MODIFIER_POOLS_PATCHER);
        PATCHERS.add(TOOLTIP_PATCHER);
        PATCHERS.add(SKILL_GATES_PATCHER);
        PATCHERS.add(NORMAL_BLACK_MARKET_TRADES_PATCHER);
        PATCHERS.add(OMEGA_BLACK_MARKET_TRADES_PATCHER);
        PATCHERS.add(PLAYER_TITLES_PATCHER);
    }

}
