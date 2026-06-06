package xyz.iwolfking.vhapi.integration.jevh.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import iskallia.vault.init.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.integration.jevh.lib.CrystalWorkbenchRecipe;
import xyz.iwolfking.vhapi.integration.jevh.lib.VaultAltarRecipe;

public class CrystalWorkbenchRecipeCategory implements IRecipeCategory<CrystalWorkbenchRecipe> {
    public static final ResourceLocation UID = VHAPI.of("crystal_workbench");
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable altarIcon;

    public CrystalWorkbenchRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(120, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CRYSTAL_WORKBENCH));
        this.altarIcon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CRYSTAL_WORKBENCH));
    }

    @Override
    public ResourceLocation getUid() { return UID; }

    @Override
    public Class<? extends CrystalWorkbenchRecipe> getRecipeClass() { return CrystalWorkbenchRecipe.class; }

    @Override
    public Component getTitle() { return new TextComponent("Crystal Workbench Application"); }

    @Override
    public IDrawable getBackground() { return background; }

    @Override
    public IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrystalWorkbenchRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 20)
               .addItemStack(recipe.input());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 90, 20)
               .addItemStack(recipe.output());
    }

    @Override
    public void draw(CrystalWorkbenchRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        this.altarIcon.draw(stack, 50, 20);
        if (recipe.tooltips() != null && !recipe.tooltips().isEmpty()) {
            Minecraft minecraft = Minecraft.getInstance();
            int startY = 55;
            int lineHeight = 10;

            for (int i = 0; i < recipe.tooltips().size(); i++) {
                String line = recipe.tooltips().get(i);

                int textWidth = minecraft.font.width(line);
                int centerX = (120 - textWidth) / 2;

                minecraft.font.draw(stack, line, centerX, startY + (i * lineHeight), 0x404040);
            }
        }
    }

}