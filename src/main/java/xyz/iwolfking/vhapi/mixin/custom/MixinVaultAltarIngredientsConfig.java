package xyz.iwolfking.vhapi.mixin.custom;

import iskallia.vault.altar.RequiredItems;
import iskallia.vault.config.Config;
import iskallia.vault.config.altar.VaultAltarIngredientsConfig;
import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.config.entry.LevelEntryMap;
import iskallia.vault.research.ResearchTree;
import iskallia.vault.util.data.WeightedList;
import iskallia.vault.world.data.PlayerResearchesData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.init.ModConfigs;

import java.util.*;

@Mixin(value = VaultAltarIngredientsConfig.class, remap = false)
public class MixinVaultAltarIngredientsConfig {

    @Shadow private LevelEntryMap<Map<String, WeightedList<AltarIngredientEntry>>> LEVELS;


    /**
     * @author iwolfking
     * @reason Add modded items into pool for the player
     */

    @Unique
    private ServerPlayer vhapi$capturedPlayer;


    @Unique
    private Map<String, AltarIngredientEntry> vhapi$getEntriesWithModFilter(int vaultLevel, ServerPlayer player) {
        Optional<Map<String, WeightedList<AltarIngredientEntry>>> pool = this.LEVELS.getForLevel(vaultLevel);
        if (pool.isEmpty()) {
            throw new IllegalArgumentException("No entry found for the given level: " + vaultLevel);
        } else {
            Map<String, WeightedList<AltarIngredientEntry>> map = pool.get();
            Map<String, AltarIngredientEntry> recipe = new HashMap<>();
            PlayerResearchesData researchData = PlayerResearchesData.get(player.getLevel());
            ResearchTree playerResearchTree = researchData.getResearches(player);
            map.forEach((k, v) -> {
                List<AltarIngredientEntry> entriesToRemove = new ArrayList<>();
                v.forEach((altarIngredientEntry, number) -> {
                    List<ItemStack> itemsToRemove = new ArrayList<>();
                    for(ItemStack itemStack : altarIngredientEntry.getItems()) {
                        if (ModConfigs.VAULTAR_RESEARCH_GATES.entries.containsKey(Objects.requireNonNull(itemStack.getItem().getRegistryName()).getNamespace())) {
                            if (!playerResearchTree.isResearched(ModConfigs.VAULTAR_RESEARCH_GATES.entries.get(itemStack.getItem().getRegistryName().getNamespace()))) {
                                itemsToRemove.add(itemStack);
                            }
                            else {
                                break;
                            }
                        }
                    }
                    altarIngredientEntry.getItems().removeAll(itemsToRemove);
                    if(altarIngredientEntry.getItems().isEmpty()) {
                        entriesToRemove.add(altarIngredientEntry);
                    }
                } );
                entriesToRemove.forEach(v::removeEntry);
                recipe.put(k, v.getRandom(Config.rand));
            });
            return recipe;
        }
    }

    @Redirect(method = "getIngredients", at = @At(value = "INVOKE", target = "Liskallia/vault/config/altar/VaultAltarIngredientsConfig;getEntries(I)Ljava/util/Map;"))
    private Map<String, AltarIngredientEntry> modifyGetEntries(VaultAltarIngredientsConfig instance, int vaultLevel) {
        return vhapi$getEntriesWithModFilter(vaultLevel, vhapi$capturedPlayer);
    }

    @Inject(method = "getIngredients", at = @At("HEAD"))
    private void getPlayerFromGetIngredients(ServerPlayer player, BlockPos pos, CallbackInfoReturnable<List<RequiredItems>> cir) {
        vhapi$capturedPlayer = player;
    }
}
