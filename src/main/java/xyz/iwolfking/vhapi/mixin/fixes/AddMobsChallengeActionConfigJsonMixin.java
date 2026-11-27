package xyz.iwolfking.vhapi.mixin.fixes;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import iskallia.vault.core.vault.challenge.action.AddMobsChallengeAction;
import iskallia.vault.core.world.roll.IntRoll;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(value = AddMobsChallengeAction.Config.class, remap = false)
public abstract class AddMobsChallengeActionConfigJsonMixin {

    @Shadow
    private IntRoll count;

    @Inject(method = "writeJson", at = @At("RETURN"))
    private void vhapi$flattenConstantCountJson(CallbackInfoReturnable<Optional<JsonObject>> cir) {
        if (this.count == null) {
            return;
        }
        cir.getReturnValue().ifPresent(json -> {
            if (this.count instanceof IntRoll.Constant constant) {
                json.add("count", new JsonPrimitive(constant.getCount()));
            }
        });
    }
}