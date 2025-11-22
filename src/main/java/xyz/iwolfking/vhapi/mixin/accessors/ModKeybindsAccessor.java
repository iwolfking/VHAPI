package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.init.ModKeybinds;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.IKeyConflictContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ModKeybinds.class, remap = false)
public interface ModKeybindsAccessor {
    @Invoker("name")
    static String name(String name) {
        return null;
    }

    @Invoker("mapping")
    static KeyMapping mapping(String description, IKeyConflictContext conflictContext) {
        return null;
    }
}
