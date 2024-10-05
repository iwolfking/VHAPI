package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.item.modification.ReforgeTagModificationFocus;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = ReforgeTagModificationFocus.class, remap = false)
public interface ReforgeTagModificationFocusAccessor {
    @Accessor("ITEM_TO_NAME")
    public static Map<Item, String> getItemToName() {
        return null;
    }
}
