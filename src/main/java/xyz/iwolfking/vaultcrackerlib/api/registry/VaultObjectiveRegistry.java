package xyz.iwolfking.vaultcrackerlib.api.registry;

import iskallia.vault.client.gui.screen.bounty.BountyScreen;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.item.crystal.CrystalData;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;
import xyz.iwolfking.vaultcrackerlib.api.registry.objective.CustomObjectiveRegistryEntry;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.BountyScreenAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.LootInfoGroupDefinitionRegistryAccessor;

import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class VaultObjectiveRegistry {
    public static Supplier<IForgeRegistry<CustomObjectiveRegistryEntry>> customObjectiveRegistry;


    public static void newRegistry(NewRegistryEvent event) {
        System.out.println("ADDING NEW REGISTRY");
        RegistryBuilder<CustomObjectiveRegistryEntry> builder = new RegistryBuilder<CustomObjectiveRegistryEntry>().setType(CustomObjectiveRegistryEntry.class).setName(new ResourceLocation("vaultcrackerlib", "custom_objective_registry")).add(new Callback());
        customObjectiveRegistry = event.create(builder);
    }

    public static class Callback implements IForgeRegistry.AddCallback<CustomObjectiveRegistryEntry> {
        @Override
        public void onAdd(IForgeRegistryInternal<CustomObjectiveRegistryEntry> iForgeRegistryInternal, RegistryManager registryManager, int i, CustomObjectiveRegistryEntry customObjectiveRegistryEntry, @Nullable CustomObjectiveRegistryEntry v1) {
            System.out.println("Registered custom objective: " + customObjectiveRegistryEntry.getId());
            VaultRegistry.OBJECTIVE.add(customObjectiveRegistryEntry.getKey());
            CrystalData.OBJECTIVE.register(customObjectiveRegistryEntry.getId(), customObjectiveRegistryEntry.getCrystalObjective(), customObjectiveRegistryEntry.getCrystalObjectiveSupplier());
            if(BountyScreenAccessor.getObjectiveNames() != null) {
                BountyScreenAccessor.getObjectiveNames().put(customObjectiveRegistryEntry.getId(), new TextComponent(customObjectiveRegistryEntry.getName()));
            }
            if(customObjectiveRegistryEntry.getCrateItem() != null) {
                LootInfoGroupDefinitionRegistryAccessor.invokeRegister("completion_crate" + customObjectiveRegistryEntry.getId(), () -> new ItemStack(customObjectiveRegistryEntry.getCrateItem()));
            }
        }
    }

    public void registerCustomObjectives(RegistryEvent.Register<CustomObjectiveRegistryEntry> event) {
        System.out.println("CALLED CUSTOM VAULT OBJECTIVE REGISTRY");
    }
}
