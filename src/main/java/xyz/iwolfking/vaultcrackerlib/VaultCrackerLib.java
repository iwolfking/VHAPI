package xyz.iwolfking.vaultcrackerlib;

import com.mojang.logging.LogUtils;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.init.ModConfigs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import xyz.iwolfking.vaultcrackerlib.api.registry.generic.records.CustomVaultObjectiveEntry;
import xyz.iwolfking.vaultcrackerlib.api.registry.objective.CustomVaultObjectiveRegistry;


import java.util.concurrent.atomic.AtomicBoolean;

import static net.minecraft.world.level.dimension.DimensionType.OVERWORLD_EFFECTS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("vaultcrackerlib")
public class VaultCrackerLib {


    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    private final AtomicBoolean hasLoaded = new AtomicBoolean();




    public VaultCrackerLib() {

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::worldLoad);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)  {
        for(CustomVaultObjectiveEntry entry : CustomVaultObjectiveRegistry.getCustomVaultObjectiveEntries()) {
            VaultRegistry.OBJECTIVE.add(entry.key());
        }
    }

    public static void beforeConfigsLoad() {
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
