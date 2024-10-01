//package xyz.iwolfking.vaultcrackerlib.mixin.configs.descriptions;
//
//import com.google.gson.JsonElement;
//import iskallia.vault.config.AbilitiesDescriptionsConfig;
//import iskallia.vault.init.ModConfigs;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.MutableComponent;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;
//
//@Mixin(value = AbilitiesDescriptionsConfig.class, remap = false)
//public class MixinAbilitiesDescriptionsConfig {
//    @Inject(method = "getDescriptionFor", at = @At("HEAD"), cancellable = true)
//    public void getDescriptionFor(String skillName, CallbackInfoReturnable<MutableComponent> cir) {
//        AbilitiesDescriptionsConfig.DescriptionData element = Patchers.ABILITIES_DESCRIPTIONS_PATCHER.getAdditions().get(skillName);
//        if(element != null) {
//            cir.setReturnValue(Component.Serializer.fromJson(element));
//        }
//    }
//
//    @Inject(method = "readConfig", at = @At("TAIL"))
//    public void readConfig(CallbackInfoReturnable<?> cir) {
//        for (AbilitiesDescriptionsConfig.DescriptionData element : Patchers.ABILITIES_DESCRIPTIONS_PATCHER.getAdditions().values()) {
//            ModConfigs.COLORS.replaceColorStrings(element);
//        }
//    }
//}
