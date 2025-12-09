package xyz.iwolfking.vhapi.integration.jevh;

import iskallia.vault.VaultMod;
import iskallia.vault.config.CompanionRelicsConfig;
import iskallia.vault.config.VaultCrystalConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.TemplatePoolKey;
import iskallia.vault.core.random.ChunkRandom;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.core.world.loot.LootPool;
import iskallia.vault.core.world.loot.LootTable;
import iskallia.vault.core.world.loot.entry.ItemLootEntry;
import iskallia.vault.core.world.template.data.DirectTemplateEntry;
import iskallia.vault.core.world.template.data.IndirectTemplateEntry;
import iskallia.vault.core.world.template.data.TemplateEntry;
import iskallia.vault.core.world.template.data.TemplatePool;
import iskallia.vault.gear.VaultGearState;
import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.ToolGearData;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.trinket.TrinketEffectRegistry;
import iskallia.vault.init.ModBlocks;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.*;
import iskallia.vault.item.data.InscriptionData;
import iskallia.vault.item.gear.TemporalShardItem;
import iskallia.vault.item.gear.TrinketItem;
import iskallia.vault.item.modification.ReforgeTagModificationFocus;
import iskallia.vault.item.tool.ToolItem;
import iskallia.vault.util.EnchantmentCost;
import iskallia.vault.util.VaultRarity;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.config.VHAPIConfig;
import xyz.iwolfking.vhapi.integration.the_vault.VaultSealHelper;
import xyz.iwolfking.vhapi.integration.wolds.WoldsSealHelper;
import xyz.iwolfking.vhapi.mixin.accessors.*;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static mezz.jei.api.recipe.RecipeIngredientRole.*;

@JeiPlugin
public class VHAPIJEIPlugin implements IModPlugin {
    public static final RecipeType<LabeledLootInfo> THEMES = RecipeType.create(VHAPI.MODID, "themes", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> OBJECTIVES = RecipeType.create(VHAPI.MODID, "objectives", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> MODIFIER_POOLS = RecipeType.create(VHAPI.MODID, "modifier_pools", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> TEMPORAL_RELICS = RecipeType.create(VHAPI.MODID, "temporal_relics", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> INSCRIPTION_POOLS = RecipeType.create(VHAPI.MODID, "inscription_pools", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> VAULT_CATALYST_POOLS = RecipeType.create(VHAPI.MODID, "catalyst_pools", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> ROOM_POOLS = RecipeType.create(VHAPI.MODID, "room_pools", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> TRINKETS = RecipeType.create(VHAPI.MODID, "trinkets", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> PREBUILT_TOOLS = RecipeType.create(VHAPI.MODID, "prebuilt_tools", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> COMPANION_RELICS = RecipeType.create(VHAPI.MODID, "companion_relics", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> VAULT_PORTAL_BLOCKS = RecipeType.create(VHAPI.MODID, "vault_portal_blocks", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> ABILITY_SCROLLS = RecipeType.create(VHAPI.MODID, "skill_scrolls", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> TALENT_SCROLLS = RecipeType.create(VHAPI.MODID, "talent_scrolls", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> MODIFIER_SCROLLS = RecipeType.create(VHAPI.MODID, "modifier_scrolls", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> ROYALE_LOOT = RecipeType.create(VHAPI.MODID, "royale_loot", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> ROYALE_PRESETS = RecipeType.create(VHAPI.MODID, "royale_presets", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> VAULT_DIFFUSER = RecipeType.create(VHAPI.MODID, "vault_diffuser", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> VAULT_CHEST_META = RecipeType.create(VHAPI.MODID, "vault_chest_meta", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> VAULT_ENCHANTER = RecipeType.create(VHAPI.MODID, "vault_enchanter", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> FACETED_FOCUS = RecipeType.create(VHAPI.MODID, "faceted_focus", LabeledLootInfo.class);


    @Override @SuppressWarnings("removal")
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModItems.VAULT_CRYSTAL), THEMES);
        registration.addRecipeCatalyst(new ItemStack(ModItems.VAULT_CRYSTAL), OBJECTIVES);
        registration.addRecipeCatalyst(new ItemStack(ModItems.VAULT_CRYSTAL), MODIFIER_POOLS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.TEMPORAL_SHARD), TEMPORAL_RELICS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.INSCRIPTION), INSCRIPTION_POOLS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.VAULT_CATALYST_INFUSED), VAULT_CATALYST_POOLS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.VAULT_CRYSTAL), ROOM_POOLS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.TRINKET), TRINKETS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.TOOL), PREBUILT_TOOLS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.COMPANION_RELIC), COMPANION_RELICS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.VAULT_CRYSTAL), VAULT_PORTAL_BLOCKS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.ABILITY_SCROLL), ABILITY_SCROLLS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.TALENT_SCROLL), TALENT_SCROLLS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.MODIFIER_SCROLL), MODIFIER_SCROLLS);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ROYALE_CRATE), ROYALE_LOOT);
        registration.addRecipeCatalyst(new ItemStack(ModItems.CRYSTAL_SEAL_VAULT_ROYALE), ROYALE_PRESETS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.CRYSTAL_SEAL_VAULT_ROYALE_PVP), ROYALE_PRESETS);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.VAULT_DIFFUSER), VAULT_DIFFUSER);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.VAULT_HARVESTER), VAULT_DIFFUSER);
        registration.addRecipeCatalyst(new ItemStack(ModItems.VAULT_CATALYST_FRAGMENT), VAULT_CHEST_META);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.VAULT_ENCHANTER), VAULT_ENCHANTER);
        registration.addRecipeCatalyst(new ItemStack(ModItems.FACETED_FOCUS), FACETED_FOCUS);
    }

    @Override @SuppressWarnings("removal")
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(THEMES, getThemesPerLevel());
        registration.addRecipes(OBJECTIVES, getObjectivesPerLevel());
        registration.addRecipes(MODIFIER_POOLS, getModifierPoolsPerLevel());
        registration.addRecipes(TEMPORAL_RELICS, getTemporalRelics());
        registration.addRecipes(INSCRIPTION_POOLS, getInscriptionPoolsPerLevel());
        registration.addRecipes(VAULT_CATALYST_POOLS, getCatalystModifierPoolsPerLevel());
        registration.addRecipes(ROOM_POOLS, getRoomPools());
        registration.addRecipes(TRINKETS, getTrinkets());
        registration.addRecipes(COMPANION_RELICS, getCompanionRelics());
        registration.addRecipes(PREBUILT_TOOLS, getPrebuiltTools());
        registration.addRecipes(VAULT_PORTAL_BLOCKS, getValidVaultPortalBlocks());
        registration.addRecipes(ABILITY_SCROLLS, getAbilityScrolls());
        registration.addRecipes(TALENT_SCROLLS, getTalentScrolls());
        registration.addRecipes(MODIFIER_SCROLLS, getModifierScrolls());
        registration.addRecipes(ROYALE_LOOT, getRoyaleLoot());
        registration.addRecipes(ROYALE_PRESETS, getRoyalePresets());
        registration.addRecipes(VAULT_DIFFUSER, getSoulValues());
        registration.addRecipes(VAULT_CHEST_META, getChestMetaValues());
        registration.addRecipes(VAULT_ENCHANTER, getGearEnchantments());
        registration.addRecipes(FACETED_FOCUS, getFacetedFoci());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        if(VHAPIConfig.CLIENT.enableDebugJEI.get()) {
            registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, VAULT_CHEST_META, ModItems.VAULT_CATALYST_FRAGMENT, new TextComponent("Catalyst Fragment Chance")));
        }

        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, THEMES, ModItems.AUGMENT, new TextComponent("Themes")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, OBJECTIVES, ModItems.CRYSTAL_SEAL_EMPTY, new TextComponent("Objectives")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, MODIFIER_POOLS, ModItems.VAULT_CATALYST_INFUSED, new TextComponent("Modifier Pools")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, TEMPORAL_RELICS, ModItems.TEMPORAL_SHARD, new TextComponent("Temporal Relics")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, INSCRIPTION_POOLS, ModItems.INSCRIPTION, new TextComponent("Inscription Pools")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, VAULT_CATALYST_POOLS, ModItems.VAULT_CATALYST_INFUSED, new TextComponent("Infused Vault Catalyst Pools")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, ROOM_POOLS, ModItems.INSCRIPTION, new TextComponent("Room Pools")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, TRINKETS, ModItems.TRINKET, new TextComponent("Trinkets")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, PREBUILT_TOOLS, ModItems.TOOL, new TextComponent("Prebuilt Tools")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, COMPANION_RELICS, ModItems.COMPANION_RELIC, new TextComponent("Companion Relics")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, VAULT_PORTAL_BLOCKS, ModBlocks.POLISHED_VAULT_STONE, new TextComponent("Vault Portal Blocks")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, ABILITY_SCROLLS, ModItems.ABILITY_SCROLL, new TextComponent("Ability Scrolls")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, TALENT_SCROLLS, ModItems.TALENT_SCROLL, new TextComponent("Talent Scrolls")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, MODIFIER_SCROLLS, ModItems.VAULT_MODIFIER, new TextComponent("Modifier Items")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, ROYALE_LOOT, ModBlocks.ROYALE_CRATE, new TextComponent("Royale Loot")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, ROYALE_PRESETS, ModItems.ABILITY_SCROLL, new TextComponent("Royale Presets")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, VAULT_DIFFUSER, ModBlocks.VAULT_DIFFUSER, new TextComponent("Soul Diffusing")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, VAULT_ENCHANTER, ModBlocks.VAULT_ENCHANTER, new TextComponent("Vault Enchanter")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, FACETED_FOCUS, ModItems.FACETED_FOCUS, new TextComponent("Faceted Focus")));
    }

    public static List<LabeledLootInfo> getFacetedFoci() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        List<ItemStack> items = new ArrayList<>();

        for(String tag : ModConfigs.VAULT_GEAR_TAG_CONFIG.getTags()) {
            ItemStack focus = new ItemStack(ModItems.FACETED_FOCUS);
            ReforgeTagModificationFocus.setModifierTag(focus, tag);
            items.add(focus);
        }

        lootInfo.add(LabeledLootInfo.of(items, new TextComponent("Faceted Foci"), null));

        return lootInfo;
    }

    public static List<LabeledLootInfo> getGearEnchantments() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        List<ItemStack> items = new ArrayList<>();


        for(Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
            if(((VaultGearEnchantmentConfigAccessor)ModConfigs.VAULT_GEAR_ENCHANTMENT_CONFIG).getCosts().containsKey(enchantment)) {
                EnchantmentCost cost = ((VaultGearEnchantmentConfigAccessor)ModConfigs.VAULT_GEAR_ENCHANTMENT_CONFIG).getCosts().get(enchantment);
                if(cost.getItems().get(0).getItem().equals(Blocks.COMMAND_BLOCK.asItem())) {
                    continue;
                }

                if(enchantment.isCurse()) {
                    continue;
                }

                ItemStack bookStack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, cost.getLevels()));
                items.add(formatEnchantmentCost(bookStack, cost.getItems()));
            }
        }

        lootInfo.add(LabeledLootInfo.of(items, new TextComponent("Vault Enchanter Enchantments"), null));

        return lootInfo;
    }

    public static List<LabeledLootInfo> getChestMetaValues() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        List<ItemStack> items = new ArrayList<>();

        ((VaultMetaChestConfigAccessor)ModConfigs.VAULT_CHEST_META).getCatalystChances().forEach((block, vaultRarityDoubleMap) -> {
            ItemStack stack = new ItemStack(block);
            vaultRarityDoubleMap.forEach(((vaultRarity, aDouble) -> {
                items.add(formatChestMetaValue(stack, vaultRarity, aDouble));
            }));
        });

        lootInfo.add(LabeledLootInfo.of(items, new TextComponent("Catalyst Fragment Chance - Level " + ModConfigs.VAULT_CHEST_META.getCatalystMinLevel()), null));

        return lootInfo;
    }

    public static List<LabeledLootInfo> getSoulValues() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        List<ItemStack> items = new ArrayList<>();

        ModConfigs.VAULT_DIFFUSER.getDiffuserOutputMap().forEach((id, value) -> {

            if(ForgeRegistries.ITEMS.containsKey(id)) {
                Item item = ForgeRegistries.ITEMS.getValue(id);
                items.add(new ItemStack(item));
            }
            else if(ForgeRegistries.BLOCKS.containsKey(id)) {
                Block block = ForgeRegistries.BLOCKS.getValue(id);
                items.add(new ItemStack(block));
            }
        });
        lootInfo.add(LabeledLootInfo.of(items, new TextComponent("Soul Values"), null));


        return lootInfo;
    }



    public static List<LabeledLootInfo> getTemporalRelics() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        ((TemporalShardConfigAccessor)ModConfigs.TEMPORAL_SHARD).getModifiers().forEach((level) -> {
            List<ItemStack> augments = new ArrayList<>();
            AtomicInteger totalWeight = new AtomicInteger();
            level.entries.forEach(modifierEntry -> {
                modifierEntry.modifiers.forEach((weightedModifier, aDouble) -> {
                    totalWeight.getAndAdd(aDouble.intValue());
                });
                modifierEntry.modifiers.forEach((weightedModifier, aDouble) -> {
                    if(aDouble > 0) {
                        ItemStack temporalRelic = createTemporalRelicStack(weightedModifier.modifier, weightedModifier.duration);
                        augments.add(formatItemStack(temporalRelic, 1, 1, aDouble, totalWeight.get(), 1));
                    }
                });
            });
            lootInfo.add(LabeledLootInfo.of(augments, new TextComponent("Level " + level.level), null));
        });
        return lootInfo;
    }

    private static List<ItemStack> processLootTableEntry(LootTable.Entry entry, @Nullable String rollText) {
        LootPool pool = entry.getPool();
        List<ItemStack> loot = processLootPool(pool, rollText, (double)1.0F);
        return new ArrayList<>(loot);
    }

    private static List<ItemStack> processLootPool(LootPool pool, String rollText, Double weightMultiplier) {
        List<ItemStack> stacks = new ArrayList<>();
        iskallia.vault.core.util.WeightedList<Object> children = pool.getChildren();

        for(Map.Entry<Object, Double> entry : children.entrySet()) {
            Object k = entry.getKey();
            Double weight = (Double)entry.getValue();
            if (k instanceof LootPool lootpool) {
                List<ItemStack> nestedStacks = processLootPool(lootpool, rollText, weight / children.getTotalWeight());
                stacks.addAll(nestedStacks);
            }

            if (k instanceof ItemLootEntry lootEntry) {
                ItemStack is = new ItemStack(lootEntry.getItem());
                CompoundTag nbt = lootEntry.getNbt();
                if (nbt != null) {
                    is.setTag(nbt.copy());
                }

                is = formatItemStack(is, lootEntry.getCount().getMin(), lootEntry.getCount().getMax(), weightMultiplier * weight, children.getTotalWeight(), (Integer)null, rollText);
                stacks.add(is);
            }
        }

        return stacks;
    }

    public static List<LabeledLootInfo> getRoyaleLoot() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        ModConfigs.ROYALE_LOOT.presets.forEach((resourceLocation, aDouble) -> {
            List<ItemStack> tableStacks = new ArrayList<>();
            LootTable lootTable = VaultRegistry.LOOT_TABLE.getKey(resourceLocation).get(Version.latest());
            lootTable.getEntries().forEach(entry -> {
                    tableStacks.addAll(processLootTableEntry(entry, null));
            });
            lootInfo.add(LabeledLootInfo.of(tableStacks, new TextComponent(ResourceLocUtils.formatReadableName(resourceLocation)), null));
        });

        return lootInfo;
    }

    public static List<LabeledLootInfo> getCompanionRelics() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        ModConfigs.COMPANION_RELICS.pools.forEach((level, pool) -> {
            List<ItemStack> relics = new ArrayList<>();
            AtomicInteger totalWeight = new AtomicInteger();
            pool.forEach(relicEntry -> {
                ((CompanionRelicPoolAccessor)relicEntry).getPool().forEach((weightedModifier, aDouble) -> {
                    totalWeight.getAndAdd(aDouble.intValue());
                });
                ((CompanionRelicPoolAccessor) relicEntry).getPool().forEach((weightedModifier, aDouble) -> {
                    if(aDouble > 0) {
                        CompanionRelicsConfig.ResolvedEntry resolvedEntry = weightedModifier.resolve(relicEntry.getLevel(), ChunkRandom.ofNanoTime());
                        ItemStack companionRelic = CompanionRelicItem.create(resolvedEntry.getModifiers(), resolvedEntry.getModel().getMin());
                        relics.add(formatItemStack(companionRelic, 1, 1, aDouble, totalWeight.get(), 1));
                    }
                });
                lootInfo.add(LabeledLootInfo.of(relics, new TextComponent(ResourceLocUtils.formatReadableName(level) + " - Level " + relicEntry.getLevel()), null));
            });
        });
        return lootInfo;
    }

    public static List<LabeledLootInfo> getThemesPerLevel() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        ModConfigs.VAULT_CRYSTAL.THEMES.forEach((key1, value1) -> {
            value1.forEach(themeEntry -> {
                List<ItemStack> augments = new ArrayList<>();
                AtomicInteger totalWeight = new AtomicInteger();
                themeEntry.pool.forEach((key, value) -> {
                    totalWeight.getAndAdd(value.intValue());
                });
                if(themeEntry.seasonalWeights != null) {
                    themeEntry.seasonalWeights.forEach((resourceLocation, seasonalWeights) -> {
                        totalWeight.getAndAdd((int) seasonalWeights.getAdditionalWeight(LocalDate.now()));
                    });
                }
                themeEntry.pool.forEach((key, value) -> {
                    boolean isSeasonalTheme = themeEntry.seasonalWeights != null && themeEntry.seasonalWeights.containsKey(key);
                    if (value > 0 && !isSeasonalTheme) {
                        ItemStack currentAugment = AugmentItem.create(key);
                        augments.add(formatItemStack(currentAugment, 1, 1, value, totalWeight.get(), null));
                    }
                    else if(isSeasonalTheme) {
                        ItemStack currentAugment = AugmentItem.create(key);
                        VaultCrystalConfig.ThemeEntry.SeasonalWeights weights = themeEntry.seasonalWeights.get(key);
                        double additionalWeight = weights.getAdditionalWeight(LocalDate.now());
                        StringBuilder builder = new StringBuilder();
                        builder.append("Seasonal");
                        weights.ranges.forEach(dateRangeWeight -> {
                            builder.append("\n").append(dateRangeWeight.from).append(" - ").append(dateRangeWeight.to).append(" (").append(dateRangeWeight.getWeight(LocalDate.now())).append(")");
                        });
                        augments.add(formatItemStack(currentAugment, 1, 1, value + additionalWeight, totalWeight.get(), null, builder.toString()));
                    }
                });
                lootInfo.add(LabeledLootInfo.of(augments, new TextComponent(ResourceLocUtils.formatReadableName(key1) + " - Level " + themeEntry.level), null));
            });
        });
        return lootInfo;
    }

    public static List<LabeledLootInfo> getObjectivesPerLevel() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        ModConfigs.VAULT_CRYSTAL.OBJECTIVES.forEach((key1, value1) -> {
            value1.forEach(objectiveEntry -> {
                    List<ItemStack> augments = new ArrayList<>();
                    AtomicInteger totalWeight = new AtomicInteger();
                    if(objectiveEntry.pool.size() > 1) {
                        objectiveEntry.pool.forEach((key, value) -> totalWeight.getAndAdd(value.intValue()));
                        objectiveEntry.pool.forEach((key, value) -> {
                            if (value > 0 || key1.equals(VaultMod.id("default"))) {
                                ItemStack sealStack;
                                if(LoadingModList.get().getModFileById("woldsvaults") != null) {
                                    sealStack = WoldsSealHelper.getSealFromObjective(key);
                                }
                                else {
                                    sealStack = VaultSealHelper.getSealFromObjective(key);
                                }
                                augments.add(formatItemStack(sealStack, 1, 1, value, totalWeight.get(), 1));
                            }
                        });
                        lootInfo.add(LabeledLootInfo.of(augments, new TextComponent(ResourceLocUtils.formatReadableName(key1) + " - Level " + objectiveEntry.level), null));
                    }
            });
        });
        return lootInfo;
    }

    public static List<LabeledLootInfo> getModifierPoolsPerLevel() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        ModConfigs.VAULT_MODIFIER_POOLS.pools.forEach((key1, value1) -> {
            value1.forEach(poolEntry -> {
                List<ItemStack> augments = new ArrayList<>();
                AtomicInteger totalWeight = new AtomicInteger();
                poolEntry.entries.forEach(entry -> {
                    entry.pool.forEach((modifier, weight) -> {
                        totalWeight.getAndAdd(weight.intValue());
                    });
                });
                poolEntry.entries.forEach(entry -> {
                    entry.pool.forEach((modifier, weight) -> {
                        if(weight > 0) {
                            if(modifier.equals(VaultMod.id("dummy")) || modifier.equals(VaultMod.id("empty"))) {
                                augments.add(formatItemStack(ItemStack.EMPTY, entry.min, entry.max, weight, totalWeight.get(), null));
                            }
                            else {
                                ItemStack modifierStack = createModifierItemStack(modifier);
                                augments.add(formatItemStack(modifierStack, entry.min, entry.max, weight, totalWeight.get(), null));
                            }
                        }
                    });
                });
                lootInfo.add(LabeledLootInfo.of(augments, new TextComponent(ResourceLocUtils.formatReadableName(key1) + " - Level " + poolEntry.level), null));
            });
        });
        return lootInfo;
    }

    public static List<LabeledLootInfo> getInscriptionPoolsPerLevel() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        ModConfigs.INSCRIPTION.pools.forEach((key1, value1) -> {
            value1.forEach(poolEntry -> {
                if(((InscriptionConfigPoolAccessor)poolEntry).getPool() == null ||  ((InscriptionConfigPoolAccessor)poolEntry).getPool().isEmpty()) {
                    return;
                }

                if(((InscriptionConfigPoolAccessor)poolEntry).getPool().size() <= 1) {
                    return;
                }


                List<ItemStack> augments = new ArrayList<>();

                int totalWeight = (int) ((InscriptionConfigPoolAccessor)poolEntry).getPool().getTotalWeight();


                ((InscriptionConfigPoolAccessor)poolEntry).getPool().forEach((entry, weight) -> {
                    if(weight > 0) {
                        if(entry == null) {
                            return;
                        }
                        InscriptionData inscriptionData = InscriptionData.empty();
                        inscriptionData.setSize(((InscriptionConfigEntryAccessor)entry).getSize().getMin());
                        inscriptionData.setModel(((InscriptionConfigEntryAccessor)entry).getModel().getMin());
                        inscriptionData.setColor(((InscriptionConfigEntryAccessor)entry).getColor());
                        ((InscriptionConfigEntryAccessor) entry).getEntries().forEach(inscriptionData::add);
                        ItemStack inscriptionStack = new ItemStack(ModItems.INSCRIPTION);
                        inscriptionData.write(inscriptionStack);
                        augments.add(formatItemStack(inscriptionStack, 1, 1, weight, totalWeight, null));
                    }
                });
                lootInfo.add(LabeledLootInfo.of(augments, new TextComponent(ResourceLocUtils.formatReadableName(key1) + " - Level " + poolEntry.getLevel()), null));
            });
        });
        return lootInfo;
    }

    private static List<ItemStack> inscriptionsFromTemplatePool(ResourceLocation templatePoolId, int inscriptionColor) {
        TemplatePoolKey templatePoolKey = VaultRegistry.TEMPLATE_POOL.getKey(templatePoolId);
        TemplatePool rooms = templatePoolKey.get(Version.latest());
        List<ItemStack> inscriptions = new ArrayList<>();

        int totalWeight = (int) rooms.getChildren().getTotalWeight();
        rooms.getChildren().forEach(((o, aDouble) -> {
            TemplateEntry entry = (TemplateEntry) o;
            ItemStack inscription = new ItemStack(ModItems.INSCRIPTION);
            InscriptionData inscriptionData = InscriptionData.empty();

            if(entry instanceof IndirectTemplateEntry indirectTemplateEntry) {
                inscriptionData.add(indirectTemplateEntry.getReferenceId(), 1, inscriptionColor);
                if(ModConfigs.INSCRIPTION.poolToModel.containsKey(indirectTemplateEntry.getReferenceId())) {
                    inscriptionData.setModel(ModConfigs.INSCRIPTION.getModel(indirectTemplateEntry.getReferenceId()));
                }
            }
            else if(entry instanceof DirectTemplateEntry directTemplateEntry) {
                inscriptionData.add(directTemplateEntry.getTemplate().getId(), 1, inscriptionColor);
                if(ModConfigs.INSCRIPTION.poolToModel.containsKey(directTemplateEntry.getTemplate().getId())) {
                    inscriptionData.setModel(ModConfigs.INSCRIPTION.getModel(directTemplateEntry.getTemplate().getId()));

                }
            }

            inscriptionData.setSize(10);
            inscriptionData.setColor(inscriptionColor);
            inscriptionData.write(inscription);
            inscriptions.add(formatItemStack(inscription, 1, 1, aDouble, totalWeight, null));
        }));

        return inscriptions;
    }

    private static final Map<ResourceLocation, Integer> TEMPLATE_POOLS = Map.of(VaultMod.id("vault/rooms/omega_rooms"), 7012096, VaultMod.id("vault/rooms/challenge_rooms"), 15769088);

    public static List<LabeledLootInfo> getRoomPools() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();

        TEMPLATE_POOLS.forEach(((resourceLocation, integer) -> {
            lootInfo.add(LabeledLootInfo.of(inscriptionsFromTemplatePool(resourceLocation, integer), new TextComponent(ResourceLocUtils.formatReadableName(resourceLocation)), null));
        }));

        return lootInfo;
    }

    public static List<LabeledLootInfo> getValidVaultPortalBlocks() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();

        List<ItemStack> blocks = new ArrayList<>();

        Arrays.stream(ModConfigs.VAULT_PORTAL.getValidFrameBlocks()).toList().forEach(s -> {
            blocks.add(new ItemStack(s));
        });

        lootInfo.add(LabeledLootInfo.of(blocks, new TextComponent("Vault Portal Blocks"), null));

        return lootInfo;
    }

    public static List<LabeledLootInfo> getAbilityScrolls() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();

        List<ItemStack> scrolls = new ArrayList<>();

        AtomicInteger abilityTotalWeight = new AtomicInteger();

        ((SkillScrollConfigAccessor)ModConfigs.SKILL_SCROLLS).getAbilities().forEach((s, number) -> {
            abilityTotalWeight.getAndAdd((Integer) number);
        });

        ((SkillScrollConfigAccessor)ModConfigs.SKILL_SCROLLS).getAbilities().forEach((s, number) -> {
            ItemStack scrollStack = new ItemStack(ModItems.ABILITY_SCROLL);
            scrollStack.getOrCreateTag().putString("Ability", s);
            scrolls.add(formatItemStack(scrollStack, 1, 1, (int)number, abilityTotalWeight.get(), null));
        });

        lootInfo.add(LabeledLootInfo.of(scrolls, new TextComponent("Ability Scrolls"), null));

        return lootInfo;
    }

    public static List<LabeledLootInfo> getTalentScrolls() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();

        List<ItemStack> scrolls = new ArrayList<>();

        AtomicInteger abilityTotalWeight = new AtomicInteger();

        ((SkillScrollConfigAccessor)ModConfigs.SKILL_SCROLLS).getTalents().forEach((s, number) -> {
            abilityTotalWeight.getAndAdd((Integer) number);
        });

        ((SkillScrollConfigAccessor)ModConfigs.SKILL_SCROLLS).getTalents().forEach((s, number) -> {
            ItemStack scrollStack = new ItemStack(ModItems.TALENT_SCROLL);
            scrollStack.getOrCreateTag().putString("Talent", s);
            scrolls.add(formatItemStack(scrollStack, 1, 1, (int)number, abilityTotalWeight.get(), null));
        });

        lootInfo.add(LabeledLootInfo.of(scrolls, new TextComponent("Talent Scrolls"), null));

        return lootInfo;
    }

    public static List<LabeledLootInfo> getModifierScrolls() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();

        List<ItemStack> scrolls = new ArrayList<>();

        AtomicInteger abilityTotalWeight = new AtomicInteger();

        ((SkillScrollConfigAccessor)ModConfigs.SKILL_SCROLLS).getModifiers().forEach((s, number) -> {
            abilityTotalWeight.getAndAdd((Integer) number);
        });

        ((SkillScrollConfigAccessor)ModConfigs.SKILL_SCROLLS).getModifiers().forEach((s, number) -> {
            ItemStack scrollStack = new ItemStack(ModItems.VAULT_MODIFIER);
            scrollStack.getOrCreateTag().putString("Modifier", s.toString());
            scrolls.add(formatItemStack(scrollStack, 1, 1, (int)number, abilityTotalWeight.get(), null));
        });

        lootInfo.add(LabeledLootInfo.of(scrolls, new TextComponent("Modifier Items"), null));

        return lootInfo;
    }

    public static List<LabeledLootInfo> getRoyalePresets() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();

        ModConfigs.PRESET_CONFIG.LEVELS.forEach(level -> {
            level.skillPresets.forEach((preset, aDouble) -> {
                List<ItemStack> presetItems = new ArrayList<>();
                preset.abilities.forEach(skillEntry -> {
                    ItemStack abilityScroll = new ItemStack(ModItems.ABILITY_SCROLL);
                    abilityScroll.getOrCreateTag().putString("Ability", skillEntry.getId());
                    presetItems.add(formatItemStack(abilityScroll, skillEntry.level.getMin(), skillEntry.level.getMax(), 0, 0));
                });
                preset.talents.forEach(skillEntry -> {
                    ItemStack talentScroll = new ItemStack(ModItems.TALENT_SCROLL);
                    talentScroll.getOrCreateTag().putString("Talent", skillEntry.getId());
                    presetItems.add(formatItemStack(talentScroll, skillEntry.level.getMin(), skillEntry.level.getMax(), 0, 0));
                });
                lootInfo.add(LabeledLootInfo.of(presetItems, new TextComponent(preset.name + " - Level " + level.level), null));
            });
        });

        return lootInfo;
    }



    public static List<LabeledLootInfo> getTrinkets() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();

        AtomicInteger totalWeight = new AtomicInteger(0);

        List<ItemStack> trinkets = new ArrayList<>();

        ModConfigs.TRINKET.TRINKETS.forEach((resourceLocation, trinket) -> {
            totalWeight.getAndAdd(trinket.getWeight());
        });

        ModConfigs.TRINKET.TRINKETS.forEach((resourceLocation, trinket) -> {
            ItemStack trinketStack = TrinketItem.createBaseTrinket(TrinketEffectRegistry.getEffect(resourceLocation));
            trinkets.add(formatItemStack(trinketStack, 1, 1, trinket.getWeight(), totalWeight.get(), null));
        });

        lootInfo.add(LabeledLootInfo.of(trinkets, new TextComponent("Trinkets"), null));

        return lootInfo;
    }

    public static List<LabeledLootInfo> getPrebuiltTools() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();

        List<ItemStack> tools = new ArrayList<>();

        ModConfigs.PRE_BUILT_TOOLS.getKeys().forEach((key) -> {
            ItemStack toolStack = ModItems.TOOL.defaultItem();
            toolStack.getOrCreateTag().putString("vaultPrebuiltTool", key);
            if(toolStack.getItem() instanceof ToolItem toolItem) {
                toolItem.initialize(toolStack, ChunkRandom.ofNanoTime());
                VaultGearData data = ToolGearData.read(toolStack);
                data.setState(VaultGearState.IDENTIFIED);
                data.write(toolStack);
            }
            tools.add(toolStack);
        });

        lootInfo.add(LabeledLootInfo.of(tools, new TextComponent("Prebuilt Tools"), null));

        return lootInfo;
    }

    public static List<LabeledLootInfo> getCatalystModifierPoolsPerLevel() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        ModConfigs.CATALYST.pools.forEach((key1, value1) -> {
            value1.forEach(poolEntry -> {
                if(((CatalystConfigPoolAccessor)poolEntry).getPool() == null ||  ((CatalystConfigPoolAccessor)poolEntry).getPool().isEmpty()) {
                    return;
                }

                if(((CatalystConfigPoolAccessor)poolEntry).getPool().size() <= 1) {
                    return;
                }


                List<ItemStack> augments = new ArrayList<>();

                int totalWeight = (int) ((CatalystConfigPoolAccessor)poolEntry).getPool().getTotalWeight();


                ((CatalystConfigPoolAccessor)poolEntry).getPool().forEach((entry, weight) -> {
                    if(weight > 0) {
                        if(entry == null) {
                            return;
                        }
                        ItemStack catalystStack = InfusedCatalystItem.create(((CatalystConfigEntryAccessor)entry).getSize().getMin(), ((CatalystConfigEntryAccessor)entry).getModifiers());
                        catalystStack.getOrCreateTag().putInt("model", ((CatalystConfigEntryAccessor) entry).getModel());
                        augments.add(formatItemStack(catalystStack, 1, 1, weight, totalWeight, null));
                    }
                });
                lootInfo.add(LabeledLootInfo.of(augments, new TextComponent(ResourceLocUtils.formatReadableName(key1) + " - Level " + poolEntry.getLevel()), null));
            });
        });
        return lootInfo;
    }

    public static ItemStack createModifierItemStack(ResourceLocation modifierId) {
        ItemStack modifierItem = new ItemStack(ModItems.VAULT_MODIFIER);
        modifierItem.getOrCreateTag().putString("Modifier", modifierId.toString());
        return modifierItem;
    }


    public static ItemStack createTemporalRelicStack(ResourceLocation modifierId, int duration) {
        ItemStack modifierItem = new ItemStack(ModItems.TEMPORAL_SHARD);
        TemporalShardItem.setModifierData(modifierItem, modifierId, duration);
        AttributeGearData data = AttributeGearData.read(modifierItem);
        data.createOrReplaceAttributeValue(ModGearAttributes.STATE, VaultGearState.IDENTIFIED);
        data.write(modifierItem);
        return modifierItem;
    }

    protected static ItemStack formatItemStack(ItemStack item, int amountMin, int amountMax, double weight, double totalWeight, @Nullable Integer amount) {
        return formatItemStack(item, amountMin, amountMax, weight, totalWeight, amount, (String)null);
    }

    private static ItemStack formatItemStack(ItemStack item, int amountMin, int amountMax, double weight, double totalWeight, @Nullable Integer amount, @Nullable String rollText) {
        ItemStack result = item.copy();
        if (item.isEmpty()) {
            result = new ItemStack(Items.BARRIER);
            result.setHoverName(new TextComponent("Nothing"));
        }

        result.setCount(amount == null ? amountMax : amount);
        double chance = weight / totalWeight * (double)100.0F;
        CompoundTag nbt = result.getOrCreateTagElement("display");
        ListTag list = nbt.getList("Lore", 8);
        MutableComponent chanceLabel = new TextComponent("Chance: ");
        chanceLabel.append(String.format("%.2f", chance));
        chanceLabel.append("%");
        list.add(StringTag.valueOf(Component.Serializer.toJson(chanceLabel.withStyle(ChatFormatting.YELLOW))));
        if (amountMin != amountMax) {
            MutableComponent countLabel = new TextComponent(amount == null ? "Count: " : "Cost: ");
            countLabel.append(amountMin + " - " + amountMax);
            list.add(StringTag.valueOf(Component.Serializer.toJson(countLabel)));
        }

        if (rollText != null) {
            MutableComponent rollLabel = new TextComponent(rollText);
            list.add(StringTag.valueOf(Component.Serializer.toJson(rollLabel.withStyle(ChatFormatting.DARK_AQUA))));
        }

        nbt.put("Lore", list);
        return result;
    }

    private static ItemStack formatChestMetaValue(ItemStack item, VaultRarity rarity, double chance) {
        ItemStack result = item.copy();
        if (item.isEmpty()) {
            result = new ItemStack(Items.BARRIER);
            result.setHoverName(new TextComponent("Nothing"));
        }
        CompoundTag nbt = result.getOrCreateTagElement("display");
        ListTag list = nbt.getList("Lore", 8);
        MutableComponent soulLabel = new TextComponent("Rarity: ");
        soulLabel.append(String.format("%s", rarity));
        list.add(StringTag.valueOf(Component.Serializer.toJson(soulLabel.withStyle(rarity.color))));
        MutableComponent chanceLabel = new TextComponent("Chance: ");
        chanceLabel.append(String.format("%.2f", chance * 100));
        chanceLabel.append("%");
        list.add(StringTag.valueOf(Component.Serializer.toJson(chanceLabel.withStyle(ChatFormatting.YELLOW))));
        nbt.put("Lore", list);
        return result;
    }

    private static ItemStack formatEnchantmentCost(ItemStack item, List<ItemStack> costItems) {
        ItemStack result = item.copy();
        if (item.isEmpty()) {
            result = new ItemStack(Items.BARRIER);
            result.setHoverName(new TextComponent("Nothing"));
        }
        CompoundTag nbt = result.getOrCreateTagElement("display");
        ListTag list = nbt.getList("Lore", 8);
        costItems.forEach(itemStack -> {
            MutableComponent costLabel = new TextComponent(itemStack.getCount() + "x ");
            costLabel.append(itemStack.getHoverName());
            list.add(StringTag.valueOf(Component.Serializer.toJson(costLabel.withStyle(itemStack.getHoverName().getStyle()))));

        });
        nbt.put("Lore", list);
        return result;
    }

    private static ItemStack formatItemStack(ItemStack item, int amountMin, int amountMax, double weight, double totalWeight) {
        return formatItemStack(item, amountMin, amountMax, weight, totalWeight, (Integer)null);
    }


    public static LabeledLootInfoRecipeCategory makeLabeledLootInfoCategory(IGuiHelper guiHelper, RecipeType<LabeledLootInfo> recipeType, ItemLike icon) {
        return new LabeledLootInfoRecipeCategory(guiHelper, recipeType, new ItemStack(icon), OUTPUT);
    }

    public static LabeledLootInfoRecipeCategory makeLabeledLootInfoCategory(IGuiHelper guiHelper, RecipeType<LabeledLootInfo> recipeType, ItemLike icon, Component title) {
        return new LabeledLootInfoRecipeCategory(guiHelper, recipeType, new ItemStack(icon), title, OUTPUT);
    }

    public static LabeledLootInfoRecipeCategory makeLabeledIngredientPoolCategory(IGuiHelper guiHelper, RecipeType<LabeledLootInfo> recipeType, ItemLike icon) {
        return new LabeledLootInfoRecipeCategory(guiHelper, recipeType, new ItemStack(icon), INPUT);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return VHAPI.of("vhapi_jei_integration");
    }
}
