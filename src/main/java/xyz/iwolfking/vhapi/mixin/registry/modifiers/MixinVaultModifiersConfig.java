package xyz.iwolfking.vhapi.mixin.registry.modifiers;

import iskallia.vault.config.Config;
import iskallia.vault.config.VaultModifiersConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.loaders.Processors;
import xyz.iwolfking.vhapi.api.loaders.vault.modifiers.VaultModifierConfigLoader;
import xyz.iwolfking.vhapi.api.loaders.lib.depr.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.mixin.accessors.VaultModifiersConfigAccessor;

@Mixin(value = VaultModifiersConfig.class, remap = false)
public abstract class MixinVaultModifiersConfig extends Config{
    @Shadow private VaultModifiersConfig.ModifierTypeGroups modifierTypeGroups;

    @Inject(method = "readConfig", at= @At(value = "INVOKE", target = "Ljava/util/stream/Stream;flatMap(Ljava/util/function/Function;)Ljava/util/stream/Stream;"))
    public void readConfig(CallbackInfoReturnable<?> cir) {
        VaultModifiersConfig config = super.readConfig();
        if(((VaultModifiersConfigAccessor)config).getModifierTypeGroups() != null) {
            VaultModifierConfigLoader loader = Processors.VaultModifierConfigProcessors.VAULT_MODIFIER_CONFIG_LOADER;
                for(Config customConfig : loader.CUSTOM_CONFIGS.values()) {
                    for(ResourceLocation key : ((VaultModifiersConfigAccessor) customConfig).getModifierTypeGroups().keySet()) {
                        if(((VaultModifiersConfigAccessor) config).getModifierTypeGroups().containsKey(key)) {
                            ((VaultModifiersConfigAccessor) config).getModifierTypeGroups().get(key).putAll(((VaultModifiersConfigAccessor) customConfig).getModifierTypeGroups().get(key));
                        }
                        else {
                            ((VaultModifiersConfigAccessor) config).getModifierTypeGroups().put(key, ((VaultModifiersConfigAccessor) customConfig).getModifierTypeGroups().get(key));
                        }
                    }
                }
        }

    }
}
