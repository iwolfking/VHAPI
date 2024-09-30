package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.item.InfusedCatalystItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Consumer;

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
