package xyz.iwolfking.vaultcrackerlib;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import iskallia.vault.VaultMod;
import iskallia.vault.config.*;
import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.config.entry.SkillStyle;
import iskallia.vault.config.entry.recipe.ConfigCatalystRecipe;
import iskallia.vault.config.entry.recipe.ConfigGearRecipe;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.config.recipe.ForgeRecipeType;
import iskallia.vault.config.skillgate.ConstantSkillGate;
import iskallia.vault.config.skillgate.SkillGateType;
import iskallia.vault.core.vault.player.Completion;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.core.world.data.entity.PartialEntity;
import iskallia.vault.core.world.loot.LootPool;
import iskallia.vault.core.world.loot.LootTable;
import iskallia.vault.core.world.roll.IntRoll;
import iskallia.vault.gear.crafting.recipe.VaultForgeRecipe;
import iskallia.vault.init.ModBlocks;
import iskallia.vault.init.ModItems;
import iskallia.vault.skill.SkillGates;
import iskallia.vault.util.EnchantmentCost;
import iskallia.vault.util.VaultRarity;
import iskallia.vault.util.data.WeightedList;
import iskallia.vault.world.data.PlayerTitlesData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import xyz.iwolfking.vaultcrackerlib.api.helpers.workstations.AscensionForgeHelper;
import xyz.iwolfking.vaultcrackerlib.api.helpers.workstations.market.ShardTradeHelper;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;
import xyz.iwolfking.vaultcrackerlib.api.util.ItemStackUtils;

import java.util.*;

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

    private void setup(final FMLCommonSetupEvent event)  {
        WeightedList<SoulShardConfig.ShardTrade> ENTRIES = new WeightedList<>();
        //Unfinished
        ENTRIES.add(ShardTradeHelper.normalEntry(new ItemStack(Items.CARROT, 48), 1000, 10000), 300000);
        SoulShardConfig.Trades TRADE = new SoulShardConfig.Trades(0, ENTRIES, 1200);
        Patchers.NORMAL_BLACK_MARKET_TRADES_PATCHER.add(TRADE);

        //Doesn't seem to be working yet
        Patchers.MISC_VAULT_ALTAR_INGREDIENT_PATCHER.addEntry(0, new AltarIngredientEntry(List.of(new ItemStack(ModItems.SOUL_SHARD)), 2, 2, 1.0), 30000);
        Patchers.TALENTS_GUI_PATCHER.put("test", new SkillStyle(300, 300, new ResourceLocation("test")));

        Map<String, Map<Completion, Float>> completion = new LinkedHashMap();
        LinkedHashMap<Completion, Float> defaultPool = new LinkedHashMap();
        defaultPool.put(Completion.BAILED, 1000000.0F);
        defaultPool.put(Completion.FAILED, 1000000.0F);
        defaultPool.put(Completion.COMPLETED, 10000000.0F);
        completion.put("brutal_bosses", defaultPool);
        Patchers.VAULT_STATS_COMPLETION_PATCHER.put("brutal_bosses", defaultPool);
    }
}
