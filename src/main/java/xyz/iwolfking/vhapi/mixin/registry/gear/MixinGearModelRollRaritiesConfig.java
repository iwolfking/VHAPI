package xyz.iwolfking.vhapi.mixin.registry.gear;


import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.config.Config;
import iskallia.vault.config.GearModelRollRaritiesConfig;
import iskallia.vault.dynamodel.DynamicModel;
import iskallia.vault.dynamodel.model.armor.ArmorPieceModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.init.ModDynamicModels;
import iskallia.vault.item.gear.VaultArmorItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.data.api.CustomGearModelRolls;


import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mixin(value = GearModelRollRaritiesConfig.class, remap = false)
public abstract class MixinGearModelRollRaritiesConfig extends Config {

    @Shadow
    public abstract Map<String, List<String>> getRolls(Item item);

    @Inject(method = "getRolls(Lnet/minecraft/world/item/Item;)Ljava/util/Map;", at = @At("HEAD"), cancellable = true)
    private void getRollsHook(CallbackInfoReturnable<Map<String, List<String>>> cir, @Local(argsOnly = true) Item stack) {
        if(CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.containsKey(stack.getRegistryName())) {
            cir.setReturnValue(CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.get(stack.getRegistryName()));
        }
    }

    /**
     * @author iwolfking
     * @reason Allow for Vault Gear Rarity Maps
     */
    @Overwrite
    public VaultGearRarity getRarityOf(Item stack, ResourceLocation modelId) {
        Map<String, List<String>> rolls = this.getRolls(stack);
        if (rolls != null) {
            if (stack instanceof VaultArmorItem) {
                modelId = ModDynamicModels.Armor.PIECE_REGISTRY.get(modelId).map(ArmorPieceModel::getArmorModel).map(DynamicModel::getId).orElse(modelId);
            }

            boolean isSpecial = false;
            Optional<DynamicModelRegistry<?>> registry = ModDynamicModels.REGISTRIES.getAssociatedRegistry(stack);
            if (registry.isPresent()) {
                DynamicModelRegistry<?> reg = registry.get();
                isSpecial = reg.get(modelId).map((m) -> m.getModelProperties().doesRequireRewards()).orElse(false);
            }

            if (isSpecial) {
                return VaultGearRarity.SPECIAL;
            }

            for (int i = VaultGearRarity.values().length - 1; i >= 0; --i) {
                VaultGearRarity rarity = VaultGearRarity.values()[i];
                List<String> modelIds = rolls.get(rarity.name());
                if (modelIds == null) {
                    modelIds = rolls.get(rarity);
                }
                if (modelIds != null && modelIds.contains(modelId.toString())) {
                    return rarity;
                }
            }

        }

        return VaultGearRarity.SCRAPPY;
    }

}
