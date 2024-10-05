package xyz.iwolfking.vaultcrackerlib;

import com.mojang.logging.LogUtils;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModDynamicModels;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.reflect.TypeLiteral;
import org.slf4j.Logger;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.DynamicModelRegistryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib.HandheldModelConfig;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Loaders;
import xyz.iwolfking.vaultcrackerlib.api.registry.VaultGearRegistry;
import xyz.iwolfking.vaultcrackerlib.api.registry.VaultObjectiveRegistry;
import xyz.iwolfking.vaultcrackerlib.api.registry.objective.CustomObjectiveRegistryEntry;


import java.util.concurrent.atomic.AtomicBoolean;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("vaultcrackerlib")
public class VaultCrackerLib {


    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    private final AtomicBoolean hasLoaded = new AtomicBoolean();


    public VaultCrackerLib() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::worldLoad);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, this::onAddListener);
        modEventBus.addListener(VaultObjectiveRegistry::newRegistry);
        modEventBus.addListener(VaultGearRegistry::newRegistry);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)  {

    }
    private void onAddListener(AddReloadListenerEvent event) {
        for(Item item : ModDynamicModels.REGISTRIES.getUniqueItems()) {
            System.out.println(item.getRegistryName());
            DynamicModelRegistryConfigLoader<DynamicModelRegistry<HandHeldModel>> configLoader = new DynamicModelRegistryConfigLoader<DynamicModelRegistry<HandHeldModel>>("the_vault", (DynamicModelRegistry<HandHeldModel>) ModDynamicModels.REGISTRIES.getAssociatedRegistry(item).get(), item);
            configLoader.onAddListeners(event);
        }
    }
    private void worldLoad(final WorldEvent.Load event)  {
        if(hasLoaded.get()) {
            return;
        }

        if(hasLoaded.compareAndSet(false, true)) {
            ModConfigs.register();
        }

    }


}
