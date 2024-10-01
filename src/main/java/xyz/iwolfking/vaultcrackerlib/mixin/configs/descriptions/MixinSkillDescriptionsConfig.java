package xyz.iwolfking.vaultcrackerlib.mixin.configs.descriptions;

import com.google.gson.JsonElement;
import iskallia.vault.config.Config;
import iskallia.vault.config.SkillDescriptionsConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.HashMap;

@Mixin(value = SkillDescriptionsConfig.class, remap = false)
public abstract class MixinSkillDescriptionsConfig extends Config{

    @Inject(method = "getDescriptionFor", at = @At("HEAD"), cancellable = true)
    public void getDescriptionFor(String skillName, CallbackInfoReturnable<MutableComponent> cir) {
        JsonElement element = Patchers.SKILL_DESCRIPTIONS_PATCHER.getAdditions().get(skillName);
        if(element != null) {
            cir.setReturnValue(Component.Serializer.fromJson(element));
        }
    }

    @Inject(method = "readConfig", at = @At("TAIL"))
    public void readConfig(CallbackInfoReturnable<?> cir) {
        for (JsonElement element : Patchers.SKILL_DESCRIPTIONS_PATCHER.getAdditions().values()) {
            ModConfigs.COLORS.replaceColorStrings(element);
        }
    }
}
