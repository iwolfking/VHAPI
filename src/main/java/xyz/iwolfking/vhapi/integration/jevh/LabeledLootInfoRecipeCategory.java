package xyz.iwolfking.vhapi.integration.jevh;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.attackeight.just_enough_vh.jei.LabeledLootInfo;
import iskallia.vault.VaultMod;
import iskallia.vault.util.LootInitialization;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class LabeledLootInfoRecipeCategory implements IRecipeCategory<LabeledLootInfo> {

    private static final ResourceLocation TEXTURE = VaultMod.id("textures/gui/jei/loot_info.png");
    private final RecipeType<LabeledLootInfo> recipeType;
    private final IDrawable background;
    private final Component titleComponent;
    private final IDrawable icon;
    private final RecipeIngredientRole role;

    public LabeledLootInfoRecipeCategory(IGuiHelper guiHelper, RecipeType<LabeledLootInfo> recipeType, ItemStack icon, RecipeIngredientRole role) {
        this(guiHelper, recipeType, icon, icon.getItem().getName(icon), role);
    }

    public LabeledLootInfoRecipeCategory(IGuiHelper guiHelper, RecipeType<LabeledLootInfo> recipeType, ItemStack icon, Component titleComponent, RecipeIngredientRole role) {
        this.recipeType = recipeType;
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 162, 108);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, icon);
        this.titleComponent = titleComponent;
        this.role = role;
    }

    @Override
    public Component getTitle() {
        return titleComponent;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public RecipeType<LabeledLootInfo> getRecipeType() {
        return recipeType;
    }

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return recipeType.getUid();
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends LabeledLootInfo> getRecipeClass() {
        return recipeType.getRecipeClass();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, LabeledLootInfo recipe, IFocusGroup focuses) {
        List<List<ItemStack>> items = recipe.itemStackList();
        int total = items.size();

        final int columns = 9;
        final int rows = 5;
        final int visible = columns * rows; // 45

        System.out.println("[JEI] LabeledLootInfo total entries = " + total + ", visible slots = " + visible);

        for (int slotIndex = 0; slotIndex < Math.min(total, visible); slotIndex++) {
            int x = 1 + 18 * (slotIndex % columns);
            int y = 1 + 18 * (slotIndex / columns);

            List<ItemStack> rotating = new java.util.ArrayList<>();

            for (int idx = slotIndex; idx < total; idx += visible) {
                List<ItemStack> group = items.get(idx);
                if (group == null || group.isEmpty()) continue;

                if (group.size() == 1) {
                    ItemStack base = group.get(0);
                    ItemStack first = LootInitialization.initializeVaultLoot(base, 0);

                    if (!first.equals(base)) {
                        rotating.add(first);

                        for (int n = 1; n < 10; n++) {
                            rotating.add(LootInitialization.initializeVaultLoot(base, 0));
                        }
                    } else {
                        rotating.add(first);
                    }
                } else {

                    for (ItemStack s : group) {
                        rotating.add(LootInitialization.initializeVaultLoot(s, 0));
                        if (rotating.size() >= 128) break;
                    }
                }

                if (rotating.size() >= 128) break;
            }

            if (rotating.isEmpty()) {
                ItemStack empty = new ItemStack(net.minecraft.world.item.Items.BARRIER);
                empty.setHoverName(new TextComponent("Nothing"));
                rotating.add(empty);
            }

            builder.addSlot(role, x, y).addItemStacks(rotating);
        }
    }


    @Override
    public void draw(LabeledLootInfo recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
        Minecraft minecraft = Minecraft.getInstance();
        int xPos = 0;
        int yPos = - (minecraft.font.lineHeight + 4);
        if (recipe.line2() != null) {
            minecraft.font.draw(stack, recipe.line2(), xPos, yPos, 0xFF000000);
            yPos -= minecraft.font.lineHeight + 2;
        }
        minecraft.font.draw(stack, recipe.label(), xPos, yPos, 0xFF000000);
    }
}