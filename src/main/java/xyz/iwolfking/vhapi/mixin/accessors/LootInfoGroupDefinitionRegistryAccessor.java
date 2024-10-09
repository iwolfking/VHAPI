package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.integration.jei.lootinfo.LootInfoGroupDefinitionRegistry;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Supplier;

@Mixin(value = LootInfoGroupDefinitionRegistry.class,  remap = false)
public interface LootInfoGroupDefinitionRegistryAccessor {
    @Invoker("register")
    public static void invokeRegister(String path, Supplier<ItemStack> catalystItemStackSupplier) {

    }
}
