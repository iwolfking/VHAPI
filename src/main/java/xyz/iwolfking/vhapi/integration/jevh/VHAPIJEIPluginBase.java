package xyz.iwolfking.vhapi.integration.jevh;

import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModItems;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.mixin.accessors.SkillScrollConfigAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VHAPIJEIPluginBase {
    public static final RecipeType<LabeledLootInfo> MODIFIER_SCROLLS = RecipeType.create(VHAPI.MODID, "modifier_scrolls", LabeledLootInfo.class);


    public static void registerModifierScrolls(IRecipeCatalystRegistration catalystRegistration)  {
        var item = getModifierScrollItem();
        if (item != null) {
            catalystRegistration.addRecipeCatalyst(new ItemStack(item), MODIFIER_SCROLLS);
        }
    }

    public static void registerModifierScrollsRecipes(IRecipeRegistration registration) {
        registration.addRecipes(MODIFIER_SCROLLS, getModifierScrolls());
    }

    public static void registerModifierScrollsCategory(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        var modScroll = getModifierScrollItem();
        registration.addRecipeCategories(VHAPIJEIPlugin.makeLabeledLootInfoCategory(guiHelper, MODIFIER_SCROLLS, modScroll != null ? modScroll : ModItems.VAULT_MODIFIER, new TextComponent("Modifier Items")));
    }

    public static List<LabeledLootInfo> getModifierScrolls() {
        List<LabeledLootInfo> lootInfo = new ArrayList<>();

        List<ItemStack> scrolls = new ArrayList<>();

        AtomicInteger abilityTotalWeight = new AtomicInteger();

        ((SkillScrollConfigAccessor) ModConfigs.SKILL_SCROLLS).getModifiers().forEach((s, number) -> {
            abilityTotalWeight.getAndAdd((Integer) number);
        });

        ((SkillScrollConfigAccessor)ModConfigs.SKILL_SCROLLS).getModifiers().forEach((s, number) -> {
            ItemStack scrollStack = new ItemStack(ModItems.VAULT_MODIFIER);
            scrollStack.getOrCreateTag().putString("Modifier", s.toString());
            scrolls.add(VHAPIJEIPlugin.formatItemStack(scrollStack, 1, 1, (int)number, abilityTotalWeight.get(), null));
        });

        lootInfo.add(LabeledLootInfo.of(scrolls, new TextComponent("Modifier Items"), null));

        return lootInfo;
    }


    public static Item getModifierScrollItem() {
        try {
            var scrollField = ModItems.class.getDeclaredField("MODIFIER_SCROLL");
            return scrollField.get(null) instanceof Item scrollItem ? scrollItem : null;
        } catch (Exception ignored) {}
        return null;
    }
}
