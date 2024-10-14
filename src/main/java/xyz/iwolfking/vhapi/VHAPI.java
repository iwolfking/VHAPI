package xyz.iwolfking.vhapi;

import com.mojang.logging.LogUtils;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.BoosterPackItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.events.vault.VaultEvents;
import xyz.iwolfking.vhapi.api.registry.VaultGearRegistry;
import xyz.iwolfking.vhapi.api.registry.VaultObjectiveRegistry;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;
import xyz.iwolfking.vhapi.mixin.accessors.BountyScreenAccessor;


import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("vhapi")
public class VHAPI {


    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    private final AtomicBoolean hasLoaded = new AtomicBoolean();
    public static final String MODID = "vhapi";

    public VHAPI() {
        VHAPILoggerUtils.debug("Initializing VHAPI!");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::worldLoad);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, LoaderRegistry::onAddListener);


        modEventBus.addListener(VaultObjectiveRegistry::newRegistry);
        modEventBus.addListener(VaultGearRegistry::newRegistry);

        MinecraftForge.EVENT_BUS.register(this);
        VaultEvents.init();
    }

    private void setup(final FMLCommonSetupEvent event)  {

    }

    public static ResourceLocation of(String name) {
        return new ResourceLocation(MODID, name);
    }

    private void worldLoad(final WorldEvent.Load event)  {
        if(hasLoaded.get()) {
            return;
        }

        if(hasLoaded.compareAndSet(false, true)) {
            VHAPILoggerUtils.debug("Rerunning Vault Configs load to patch them.");
            ModConfigs.register();

            if(event.getWorld().isClientSide()) {
                //Register bounty screen names
                if (BountyScreenAccessor.getObjectiveNames() != null) {
                    BountyScreenAccessor.getObjectiveNames().putAll(VaultObjectiveRegistry.CUSTOM_BOUNTY_SCREEN_NAMES);
                }
            }
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
    public static class Client {
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void textureStitch(TextureStitchEvent.Pre event) {
            if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
                ResourceManager manager = Minecraft.getInstance().getResourceManager();
                Collection<ResourceLocation> gearTextures = Minecraft.getInstance().getResourceManager().listResources("textures/item/gear", s -> s.endsWith(".png"));
                //Collection<ResourceLocation> boosterTextures = Minecraft.getInstance().getResourceManager().listResources("textures/item/booster_pack", s -> s.endsWith(".png"));
                for(ResourceLocation loc : gearTextures) {
                    if(loc.getNamespace().equals("vhapi")) {
                        VHAPILoggerUtils.debug("Stitching custom transmog texture: " + loc);
                        event.addSprite(ResourceLocUtils.stripLocationForSprite(loc));
                    }
                }
            }
        }
    }



}
