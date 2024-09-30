package xyz.iwolfking.vaultcrackerlib.mixin.registry.objective;

import iskallia.vault.VaultMod;
import iskallia.vault.integration.jei.lootinfo.LootInfo;
import iskallia.vault.integration.jei.lootinfo.LootInfoGroupDefinition;
import iskallia.vault.integration.jei.lootinfo.LootInfoGroupDefinitionRegistry;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import xyz.iwolfking.vaultcrackerlib.api.registry.generic.records.CustomVaultObjectiveEntry;
import xyz.iwolfking.vaultcrackerlib.api.registry.objective.CustomVaultObjectiveRegistry;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(value = LootInfoGroupDefinitionRegistry.class, remap = false)
public abstract class MixinLootInfoGroupDefinitionRegistry {

    @Shadow
    @Final
    private static Map<ResourceLocation, LootInfoGroupDefinition> MAP;

    static {
        for(CustomVaultObjectiveEntry entry : CustomVaultObjectiveRegistry.getCustomVaultObjectiveEntries()) {
            vaultCrackerLib$register("completion_crate_" +  entry.id(), () -> new ItemStack(entry.crate()));
        }
    }

    @Unique
    private static void vaultCrackerLib$register(String path, Supplier<ItemStack> catalystItemStackSupplier) {
        MAP.put(VaultMod.id(path), new LootInfoGroupDefinition(catalystItemStackSupplier,

       vaultCrackerLib$recipeType(path), () -> new TranslatableComponent("jei.the_vault." + path + "_loot")));
   }

    @Unique
    private static mezz.jei.api.recipe.RecipeType<LootInfo> vaultCrackerLib$recipeType(String path) {
        return RecipeType.create("the_vault", path, LootInfo.class);
    }



}
