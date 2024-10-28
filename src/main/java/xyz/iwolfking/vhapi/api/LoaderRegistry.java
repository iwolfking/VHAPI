package xyz.iwolfking.vhapi.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.ConfigDataLoaderEvent;
import xyz.iwolfking.vhapi.api.loaders.Processors;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.lib.core.processors.IConfigProcessor;
import xyz.iwolfking.vhapi.api.loaders.lib.core.GenFileProcessor;
import xyz.iwolfking.vhapi.api.loaders.lib.VHAPIDataLoader;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;

import java.util.*;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LoaderRegistry {
    private static int ordinal = -1;

    public static final Map<Integer, IConfigProcessor> CONFIG_PROCESSORS = new HashMap<>();

    /**
     * All Gen file data loaders (found under gen/ file path)
     */
    public static final Map<ResourceLocation, GenFileProcessor<?>> GEN_FILE_LOADERS = new HashMap<>();

    /**
     * The main data loader that handles processing all custom vault configs.
     */
    public static final VHAPIDataLoader VHAPI_DATA_LOADER = new VHAPIDataLoader();

    public static void onAddListener(AddReloadListenerEvent event) {
        initProcessors();
        VHAPILoggerUtils.info("Registering VHAPI datapack listener!");
        event.addListener(VHAPI_DATA_LOADER);

        MinecraftForge.EVENT_BUS.post(new ConfigDataLoaderEvent.Finish(VHAPI_DATA_LOADER));
    }

    public static void addConfigProcessor(IConfigProcessor processor) {
        ordinal += 1;
        CONFIG_PROCESSORS.put(ordinal, processor);
    }

    public static void initProcessors() {

        for(VaultConfigProcessor<?> transmogProcessor : Processors.TransmogConfigProcessors.getStandardTransmogProcessors()) {
            addConfigProcessor(transmogProcessor);
        }

        addConfigProcessor(Processors.GenerationFileProcessors.GEN_PALETTE_LOADER);
        addConfigProcessor(Processors.GenerationConfigProcessors.PALETTES_CONFIG_LOADER);

        addConfigProcessor(Processors.GenerationFileProcessors.GEN_TEMPLATE_POOL_LOADER);
        addConfigProcessor(Processors.GenerationConfigProcessors.TEMPLATE_POOLS_CONFIG_LOADER);

        addConfigProcessor(Processors.GenerationFileProcessors.GEN_LOOT_TABLE_LOADER);
        addConfigProcessor(Processors.GenerationConfigProcessors.LOOT_TABLE_CONFIG_LOADER);

        addConfigProcessor(Processors.RecipesConfigProcessors.CATALYST_RECIPES_LOADER);
        addConfigProcessor(Processors.RecipesConfigProcessors.INSCRIPTION_RECIPES_LOADER);
        addConfigProcessor(Processors.RecipesConfigProcessors.TRINKET_RECIPES_LOADER);
        addConfigProcessor(Processors.RecipesConfigProcessors.TOOL_RECIPES_LOADER);
        addConfigProcessor(Processors.RecipesConfigProcessors.GEAR_RECIPES_LOADER);

        addConfigProcessor(Processors.BuiltInLootBoxConfigProcessors.MOD_BOX_CONFIG_LOADER);
        addConfigProcessor(Processors.BuiltInLootBoxConfigProcessors.MYSTERY_BOX_CONFIG_LOADER);
        addConfigProcessor(Processors.BuiltInLootBoxConfigProcessors.MYSTERY_EGG_CONFIG_LOADER);
        addConfigProcessor(Processors.BuiltInLootBoxConfigProcessors.MYSTERY_HOSTILE_EGG_CONFIG_LOADER);
        addConfigProcessor(Processors.BuiltInLootBoxConfigProcessors.UNIDENTIFIED_TREASURE_KEY_CONFIG_LOADER);

        addConfigProcessor(Processors.VaultGearConfigProcessors.CUSTOM_VAULT_GEAR_LOADER);
        addConfigProcessor(Processors.VaultGearConfigProcessors.CUSTOM_VAULT_GEAR_WORKBENCH_LOADER);
        addConfigProcessor(Processors.VaultGearConfigProcessors.GEAR_MODEL_ROLL_RARITIES_CONFIG_LOADER);
        addConfigProcessor(Processors.VaultGearConfigProcessors.CUSTOM_GEAR_MODEL_ROLL_RARITIES_CONFIG_LOADER);
        addConfigProcessor(Processors.VaultGearConfigProcessors.GEAR_ENCHANTMENT_CONFIG_LOADER);
        addConfigProcessor(Processors.VaultGearConfigProcessors.TRINKET_CONFIG_LOADER);

        addConfigProcessor(Processors.VaultModifierConfigProcessors.VAULT_MODIFIER_CONFIG_LOADER);
        addConfigProcessor(Processors.VaultModifierConfigProcessors.VAULT_MODIFIER_POOLS_CONFIG_LOADER);

        addConfigProcessor(Processors.ResearchConfigProcessors.RESEARCH_CONFIG_LOADER);
        addConfigProcessor(Processors.ResearchConfigProcessors.RESEARCH_GROUP_CONFIG_LOADER);
        addConfigProcessor(Processors.ResearchConfigProcessors.RESEARCH_GUI_CONFIG_LOADER);
        addConfigProcessor(Processors.ResearchConfigProcessors.RESEARCH_GROUP_GUI_CONFIG_LOADER);

        addConfigProcessor(Processors.ObjectiveConfigProcessors.BINGO_CONFIG_LOADER);
        addConfigProcessor(Processors.ObjectiveConfigProcessors.ELIXIR_CONFIG_LOADER);
        addConfigProcessor(Processors.ObjectiveConfigProcessors.MONOLITH_CONFIG_LOADER);
        addConfigProcessor(Processors.ObjectiveConfigProcessors.SCAVENGER_CONFIG_LOADER);

        addConfigProcessor(Processors.SkillConfigProcessors.ABILITIES_CONFIG_LOADER);
        addConfigProcessor(Processors.SkillConfigProcessors.ABILITIES_GUI_CONFIG_LOADER);
        addConfigProcessor(Processors.SkillConfigProcessors.ABILITIES_DESCRIPTIONS_CONFIG_LOADER);
        addConfigProcessor(Processors.SkillConfigProcessors.SKILL_GATES_CONFIG_LOADER);
        addConfigProcessor(Processors.SkillConfigProcessors.SKILL_DESCRIPTIONS_CONFIG_LOADER);
        addConfigProcessor(Processors.SkillConfigProcessors.TALENT_CONFIG_LOADER);
        addConfigProcessor(Processors.SkillConfigProcessors.TALENTS_GUI_CONFIG_LOADER);
        addConfigProcessor(Processors.SkillConfigProcessors.EXPERTISE_CONFIG_LOADER);
        addConfigProcessor(Processors.SkillConfigProcessors.EXPERTISES_GUI_CONFIG_LOADER);

        addConfigProcessor(Processors.CardConfigProcessors.BOOSTER_PACKS_CONFIG_LOADER);
        addConfigProcessor(Processors.CardConfigProcessors.CARD_ESSENCE_EXTRACTOR_CONFIG_LOADER);
        addConfigProcessor(Processors.CardConfigProcessors.CARD_MODIFIERS_CONFIG_LOADER);
        addConfigProcessor(Processors.CardConfigProcessors.CARD_TASKS_CONFIG_LOADER);
        addConfigProcessor(Processors.CardConfigProcessors.DECKS_CONFIG_LOADER);

        addConfigProcessor(Processors.ToolConfigProcessors.PULVERIZING_CONFIG_LOADER);

        addConfigProcessor(Processors.VaultAltarIngredientConfigProcessors.VAULT_ALTAR_INGREDIENTS_CONFIG_LOADER);

        addConfigProcessor(Processors.WorkstationConfigProcessors.VAULT_RECYCLER_CONFIG_LOADER);
        addConfigProcessor(Processors.WorkstationConfigProcessors.VAULT_DIFFUSER_CONFIG_LOADER);
        addConfigProcessor(Processors.WorkstationConfigProcessors.BOUNTY_REWARDS_CONFIG_LOADER);

        addConfigProcessor(Processors.ShopConfigProcessors.SHOPPING_PEDESTAL_CONFIG_LOADER);
        addConfigProcessor(Processors.ShopConfigProcessors.NORMAL_BLACK_MARKET_CONFIG_LOADER);
        addConfigProcessor(Processors.ShopConfigProcessors.OMEGA_BLACK_MARKET_CONFIG_LOADER);

        addConfigProcessor(Processors.GenerationFileProcessors.GEN_THEME_LOADER);
        addConfigProcessor(Processors.GenerationConfigProcessors.THEME_CONFIG_LOADER);

        addConfigProcessor(Processors.GeneralVaultConfigProcessors.AUGMENT_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.INSCRIPTION_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.CATALYST_CONFIG_LOADER);

        addConfigProcessor(Processors.GeneralVaultConfigProcessors.VAULT_CRYSTAL_CATALYST_MODIFIERS_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.VAULT_CHEST_META_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.VAULT_GENERAL_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.VAULT_LEVELS_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.VAULT_MOBS_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.VAULT_PORTAL_BLOCK_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.TOOLTIP_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.VAULT_STATS_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.LEGACY_LOOT_TABLE_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.LOOT_INFO_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.CHAMPIONS_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.ENTITY_GROUPS_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.CUSTOM_TITLE_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.CUSTOM_ENTITY_SPAWNERS_LOADER);


        addConfigProcessor(Processors.GeneralVaultConfigProcessors.VAULT_ALTAR_CONFIG_LOADER);
        addConfigProcessor(Processors.GeneralVaultConfigProcessors.VAULT_CRYSTAL_CONFIG_LOADER);
    }
}
