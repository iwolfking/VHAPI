package xyz.iwolfking.vaultcrackerlib;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import iskallia.vault.VaultMod;
import iskallia.vault.config.*;
import iskallia.vault.config.entry.ItemEntry;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.config.entry.SkillStyle;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.core.world.generator.layout.VaultLayout;
import iskallia.vault.gear.trinket.TrinketEffect;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.crystal.layout.CrystalLayout;
import iskallia.vault.item.crystal.objective.BingoCrystalObjective;
import iskallia.vault.item.crystal.objective.CakeCrystalObjective;
import iskallia.vault.item.crystal.objective.CrystalObjective;
import iskallia.vault.item.crystal.objective.MonolithCrystalObjective;
import iskallia.vault.research.type.ModResearch;
import iskallia.vault.util.data.WeightedList;
import iskallia.vault.world.data.PlayerTitlesData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import xyz.iwolfking.vaultcrackerlib.api.helpers.market.ShardTradeHelper;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.configs.TrinketAccessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("vaultcrackerlib")
public class VaultCrackerLib {

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static WeightedList<ProductEntry> MISC_TEST = new WeightedList<>();
    static {
        MISC_TEST.add(new ProductEntry(new ItemStack(ModItems.SOUL_SHARD, 64)), 1000);
    }




    public VaultCrackerLib() {

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        Patchers.VAULT_DIFFUSER_PATCHER.put(new ResourceLocation("minecraft:cobblestone"), 1000);
        Patchers.ITEM_BLACKLIST_PATCHER.add("quark:*");
        Patchers.BLOCK_BLACKLIST_PATCHER.add("quark:*");
        Patchers.MYSTERY_BOX_PATCHER.add(new ProductEntry(new ItemStack(ModItems.SOUL_DUST, 64)), 10000);
        Patchers.MOD_BOX_PATCHER.put("Misc Bag Upgrades", MISC_TEST);
        Patchers.TOOLTIP_PATCHER.add(new TooltipConfig.TooltipEntry("minecraft:cobblestone", "You are beautiful."));
        WeightedList<SoulShardConfig.ShardTrade> ENTRIES = new WeightedList<>();
        ENTRIES.add(ShardTradeHelper.normalEntry(new ItemStack(Items.CARROT, 48), 1000, 10000), 300000);
        SoulShardConfig.Trades TRADE = new SoulShardConfig.Trades(0, ENTRIES, 1200);
        Patchers.NORMAL_BLACK_MARKET_TRADES_PATCHER.add(TRADE);
        Map<String, PlayerTitlesConfig.Title> WOLD_TITLE = new HashMap<String, PlayerTitlesConfig.Title>();
        WOLD_TITLE.put("wold", (new PlayerTitlesConfig.Title(1)).put(PlayerTitlesData.Type.TAB_LIST, new PlayerTitlesConfig.Display("Wold ", "#AAAAAA")).put(PlayerTitlesData.Type.CHAT, new PlayerTitlesConfig.Display("Wold ", (String)null)));
        Patchers.PLAYER_TITLES_PATCHER.put(PlayerTitlesConfig.Affix.PREFIX, WOLD_TITLE);

    }
}
