package xyz.iwolfking.vaultcrackerlib.mixin.registry.modifiers;

import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.config.Config;
import iskallia.vault.config.VaultModifiersConfig;
import iskallia.vault.core.vault.abyss.LegacyAbyssVaultEffectModifier;
import iskallia.vault.core.vault.modifier.modifier.EmptyModifier;
import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Loaders;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultModifiersConfigAccessor;

@Mixin(value = VaultModifiersConfig.class, remap = false)
public abstract class MixinVaultModifiersConfig extends Config{
    @Shadow private VaultModifiersConfig.ModifierTypeGroups modifierTypeGroups;

    @Inject(method = "readConfig", at= @At(value = "INVOKE", target = "Ljava/util/stream/Stream;flatMap(Ljava/util/function/Function;)Ljava/util/stream/Stream;"))
    public void readConfig(CallbackInfoReturnable<?> cir) {
        VaultModifiersConfig config = super.readConfig();
        if(((VaultModifiersConfigAccessor)config).getModifierTypeGroups() != null) {
            System.out.println("MODIFIERS WEREN'T NULL");
            for(VaultModifiersConfig customConfig : Loaders.VAULT_MODIFIER_CONFIG_LOADER.CUSTOM_CONFIGS.values()) {
                System.out.println("Reading a custom config!");
                for(ResourceLocation key : ((VaultModifiersConfigAccessor)customConfig).getModifierTypeGroups().keySet()) {
                    System.out.println(key);
                    if((((VaultModifiersConfigAccessor)config).getModifierTypeGroups().containsKey(key))) {
                        System.out.println("Adding existing key!");
                        ((VaultModifiersConfigAccessor)config).getModifierTypeGroups().get(key).putAll(((VaultModifiersConfigAccessor) customConfig).getModifierTypeGroups().get(key));
                    }
                    else {
                        System.out.println("New key added!");
                        ((VaultModifiersConfigAccessor)config).getModifierTypeGroups().put(key, ((VaultModifiersConfigAccessor) customConfig).getModifierTypeGroups().get(key));
                    }
                }
            }
        }

    }
}
