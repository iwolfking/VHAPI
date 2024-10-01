package xyz.iwolfking.vaultcrackerlib.mixin.configs.loot;

import iskallia.vault.config.LootInfoConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.Map;

@Mixin(value = LootInfoConfig.class, remap = false)
public class MixinLootInfoConfig {
    @Shadow private Map<ResourceLocation, LootInfoConfig.LootInfo> lootInfoMap;

    @Inject(method = "getLootInfoMap", at = @At("HEAD"))
    public void getLootInfoMap(CallbackInfoReturnable<Map<ResourceLocation, LootInfoConfig.LootInfo>> cir) {
        Patchers.LOOT_INFO_PATCHER.doPatches(lootInfoMap);
    }
}
