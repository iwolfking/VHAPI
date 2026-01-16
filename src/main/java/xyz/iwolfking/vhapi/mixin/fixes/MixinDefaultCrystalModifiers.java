package xyz.iwolfking.vhapi.mixin.fixes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import iskallia.vault.core.vault.modifier.VaultModifierStack;
import iskallia.vault.item.crystal.modifiers.DefaultCrystalModifiers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Optional;

@Mixin(value = DefaultCrystalModifiers.class, remap = false)
public class MixinDefaultCrystalModifiers {

    @Shadow
    @Final
    private List<VaultModifierStack> list;
    @Shadow
    private boolean randomModifiers;
    @Shadow
    private boolean clarity;

    /**
     * @author iwolfking
     * @reason Fix StackOverflowException
     */
    @Overwrite
    public Optional<JsonObject> writeJson() {
        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();

        for (VaultModifierStack stack : this.list) {
            JsonObject element = new JsonObject();
            element.addProperty("modifier", stack.getModifierId().toString());
            element.addProperty("count", stack.getSize());
            array.add(element);
        }

        json.add("list", array);
        json.addProperty("random_modifiers", this.randomModifiers);
        json.addProperty("clarity", this.clarity);
        return Optional.of(json);
    }
}
