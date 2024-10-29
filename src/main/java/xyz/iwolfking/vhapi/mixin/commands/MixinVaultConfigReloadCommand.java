package xyz.iwolfking.vhapi.mixin.commands;

import com.mojang.brigadier.context.CommandContext;
import iskallia.vault.command.ReloadConfigsCommand;
import net.minecraft.commands.CommandSourceStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;

@Mixin(value = ReloadConfigsCommand.class, remap = false)
public class MixinVaultConfigReloadCommand {

    @Inject(method = "reloadConfigs", at = @At("HEAD"))
    private void onReloadNormalConfigs(CommandContext<CommandSourceStack> context, CallbackInfoReturnable<Integer> cir) {
        VHAPILoggerUtils.info("Reloading vault configs at runtime, this is unsupported, here be dragons!");
        LoaderRegistry.VHAPI_DATA_LOADER.gatherConfigsToProcessors();
    }

    @Inject(method = "reloadGenConfigs", at = @At("HEAD"))
    private void onReloadGenConfigs(CommandContext<CommandSourceStack> context, CallbackInfoReturnable<Integer> cir) {
        VHAPILoggerUtils.info("Reloading vault configs at runtime, this is unsupported, here be dragons!");
        LoaderRegistry.VHAPI_DATA_LOADER.gatherConfigsToProcessors();
    }
}
