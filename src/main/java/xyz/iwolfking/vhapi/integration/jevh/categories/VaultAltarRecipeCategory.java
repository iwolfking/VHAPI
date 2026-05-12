package xyz.iwolfking.vhapi.integration.jevh.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import iskallia.vault.VaultMod;
import iskallia.vault.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.integration.jevh.lib.VaultAltarRecipe;

public class VaultAltarRecipeCategory implements IRecipeCategory<VaultAltarRecipe> {
    public static final ResourceLocation UID = VHAPI.of("vault_altar");
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable altarIcon;

    public VaultAltarRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(120, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.VAULT_ALTAR));
        this.altarIcon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.VAULT_ALTAR));
    }

    @Override
    public ResourceLocation getUid() { return UID; }

    @Override
    public Class<? extends VaultAltarRecipe> getRecipeClass() { return VaultAltarRecipe.class; }

    @Override
    public Component getTitle() { return new TextComponent("Vault Altar Transformation"); }

    @Override
    public IDrawable getBackground() { return background; }

    @Override
    public IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, VaultAltarRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 20)
               .addItemStack(recipe.input());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 90, 20)
               .addItemStack(recipe.output());
    }

    @Override
    public void draw(VaultAltarRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        this.altarIcon.draw(stack, 50, 20);

        if (recipe.isAscension()) {
            Minecraft mc = Minecraft.getInstance();
            mc.font.draw(stack, "Requires Level 65", 25, 45, 0xFFCC0000);
        }
    }
}