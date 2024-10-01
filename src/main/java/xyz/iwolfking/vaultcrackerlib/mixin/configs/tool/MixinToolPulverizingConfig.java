package xyz.iwolfking.vaultcrackerlib.mixin.configs.tool;

import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.core.world.loot.LootTable;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.List;
import java.util.Map;

@Mixin(value = ToolPulverizingConfig.class, remap = false)
public class MixinToolPulverizingConfig {
    @Shadow private Map<ResourceLocation, List<LootTable.Entry>> loot;

    @Inject(method = "getLoot", at = @At("HEAD"))
    private void addNewPulverizingConfigs(CallbackInfoReturnable<Map<ResourceLocation, List<LootTable.Entry>>> cir) {
        Patchers.TOOL_PULVERIZING_PATCHER.doPatches(this.loot);
    }
}
