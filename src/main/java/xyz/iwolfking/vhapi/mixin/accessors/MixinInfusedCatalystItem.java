package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.item.InfusedCatalystItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = InfusedCatalystItem.class, remap = false)
public interface MixinInfusedCatalystItem {

    @Accessor("MODELS")
    static int getModels() {
        throw new AssertionError();
    }

    @Accessor("MODELS")
    static void setModels(int modelCount) {
        throw new AssertionError();
    }


}
