package xyz.iwolfking.vhapi.integration.jevh;

import dev.attackeight.just_enough_vh.jei.LabeledLootInfo;
import iskallia.vault.VaultMod;
import iskallia.vault.gear.VaultGearState;
import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.AugmentItem;
import iskallia.vault.item.VaultModifierItem;
import iskallia.vault.item.gear.TemporalShardItem;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.loading.LoadingModList;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.integration.the_vault.VaultSealHelper;
import xyz.iwolfking.vhapi.integration.wolds.WoldsSealHelper;
import xyz.iwolfking.vhapi.mixin.accessors.TemporalShardConfigAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static mezz.jei.api.recipe.RecipeIngredientRole.INPUT;
import static mezz.jei.api.recipe.RecipeIngredientRole.OUTPUT;

@JeiPlugin
public class VHAPIJEIPlugin implements IModPlugin {
    public static final RecipeType<LabeledLootInfo> THEMES = RecipeType.create(VHAPI.MODID, "themes", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> OBJECTIVES = RecipeType.create(VHAPI.MODID, "objectives", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> MODIFIER_POOLS = RecipeType.create(VHAPI.MODID, "modifier_pools", LabeledLootInfo.class);
    public static final RecipeType<LabeledLootInfo> TEMPORAL_RELICS = RecipeType.create(VHAPI.MODID, "temporal_relics", LabeledLootInfo.class);


    @Override @SuppressWarnings("removal")
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModItems.VAULT_CRYSTAL), THEMES);
        registration.addRecipeCatalyst(new ItemStack(ModItems.VAULT_CRYSTAL), OBJECTIVES);
        registration.addRecipeCatalyst(new ItemStack(ModItems.VAULT_CRYSTAL), MODIFIER_POOLS);
        registration.addRecipeCatalyst(new ItemStack(ModItems.TEMPORAL_SHARD), TEMPORAL_RELICS);
    }

    @Override @SuppressWarnings("removal")
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(THEMES, getThemesPerLevel());
        registration.addRecipes(OBJECTIVES, getObjectivesPerLevel());
        registration.addRecipes(MODIFIER_POOLS, getModifierPoolsPerLevel());
        registration.addRecipes(TEMPORAL_RELICS, getTemporalRelics());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, THEMES, ModItems.AUGMENT, new TextComponent("Themes")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, OBJECTIVES, ModItems.CRYSTAL_SEAL_EMPTY, new TextComponent("Objectives")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, MODIFIER_POOLS, ModItems.VAULT_CATALYST_INFUSED, new TextComponent("Modifier Pools")));
        registration.addRecipeCategories(makeLabeledLootInfoCategory(guiHelper, TEMPORAL_RELICS, ModItems.TEMPORAL_SHARD, new TextComponent("Temporal Relics")));
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

    public static List<LabeledLootInfo> getThemesPerLevel() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();
        ModConfigs.VAULT_CRYSTAL.THEMES.forEach((key1, value1) -> {
            value1.forEach(themeEntry -> {
                List<ItemStack> augments = new ArrayList<>();
                AtomicInteger totalWeight = new AtomicInteger();
                themeEntry.pool.forEach((key, value) -> totalWeight.getAndAdd(value.intValue()));
                themeEntry.pool.forEach((key, value) -> {
                    if (value > 0) {
                        ItemStack currentAugment = AugmentItem.create(key);
                        augments.add(formatItemStack(currentAugment, 1, 1, value, totalWeight.get(), 1));
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
