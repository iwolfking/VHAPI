package xyz.iwolfking.vhapi.api.registry;

import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.item.crystal.CrystalData;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.Nullable;
import xyz.iwolfking.vhapi.api.registry.objective.CustomObjectiveRegistryEntry;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.LootInfoGroupDefinitionRegistryAccessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class VaultObjectiveRegistry {
    public static Supplier<IForgeRegistry<CustomObjectiveRegistryEntry>> customObjectiveRegistry;

    public static Map<String, TextComponent> CUSTOM_BOUNTY_SCREEN_NAMES = new HashMap<>();

    public static Set<CustomObjectiveRegistryEntry> ENTRIES = new HashSet<>();


    public static void newRegistry(NewRegistryEvent event) {
        RegistryBuilder<CustomObjectiveRegistryEntry> builder = new RegistryBuilder<CustomObjectiveRegistryEntry>().setType(CustomObjectiveRegistryEntry.class).setName(new ResourceLocation("vhapi", "custom_objective_registry")).add(new Callback());
        customObjectiveRegistry = event.create(builder);
    }

    public static class Callback implements IForgeRegistry.AddCallback<CustomObjectiveRegistryEntry> {
        @Override
        public void onAdd(IForgeRegistryInternal<CustomObjectiveRegistryEntry> iForgeRegistryInternal, RegistryManager registryManager, int i, CustomObjectiveRegistryEntry customObjectiveRegistryEntry, @Nullable CustomObjectiveRegistryEntry v1) {
            if(!ENTRIES.contains(customObjectiveRegistryEntry)) {
                ENTRIES.add(customObjectiveRegistryEntry);
            }
            else {
                return;
            }
            ((KeyRegistryAccessor)VaultRegistry.OBJECTIVE).setLocked(false);
            VaultRegistry.OBJECTIVE.register(customObjectiveRegistryEntry.getKey());
            CrystalData.OBJECTIVE.register(customObjectiveRegistryEntry.getId(), customObjectiveRegistryEntry.getCrystalObjective(), customObjectiveRegistryEntry.getCrystalObjectiveSupplier());
            CUSTOM_BOUNTY_SCREEN_NAMES.put(customObjectiveRegistryEntry.getId(), new TextComponent(customObjectiveRegistryEntry.getName()));
            if(customObjectiveRegistryEntry.getCrateItem() != null) {
                LootInfoGroupDefinitionRegistryAccessor.invokeRegister("completion_crate" + customObjectiveRegistryEntry.getId(), () -> new ItemStack(customObjectiveRegistryEntry.getCrateItem()));
            }
        }
    }

}
