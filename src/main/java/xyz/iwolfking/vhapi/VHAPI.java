package xyz.iwolfking.vhapi;

import com.mojang.logging.LogUtils;
import iskallia.vault.init.ModConfigs;
import lv.id.bonne.vaulthunters.serversync.proxy.client.SyncModClientProxy;
import lv.id.bonne.vaulthunters.serversync.proxy.server.SyncModServerProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.events.vault.VaultEvents;
import xyz.iwolfking.vhapi.api.registry.VaultGearRegistry;
import xyz.iwolfking.vhapi.api.registry.VaultObjectiveRegistry;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;
import xyz.iwolfking.vhapi.config.VHAPIConfig;
import xyz.iwolfking.vhapi.mixin.accessors.BountyScreenAccessor;
import xyz.iwolfking.vhapi.networking.VHAPISyncDescriptor;
import xyz.iwolfking.vhapi.networking.VHAPISyncNetwork;
import xyz.iwolfking.vhapi.proxy.IVHAPISyncProxy;
import xyz.iwolfking.vhapi.proxy.client.VHAPISyncClientProxy;
import xyz.iwolfking.vhapi.proxy.server.VHAPISyncServerProxy;


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
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, VHAPIConfig.SERVER_SPEC, "vhapi-server.toml");
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::worldLoad);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::onLogin);

        MinecraftForge.EVENT_BUS.addListener(LoaderRegistry::onAddListener);


        modEventBus.addListener(VaultObjectiveRegistry::newRegistry);
        modEventBus.addListener(VaultGearRegistry::newRegistry);

        MinecraftForge.EVENT_BUS.register(this);
        VaultEvents.init();
        PROXY.init();

        //Config Sync from Server

    }

    public static final IVHAPISyncProxy PROXY = DistExecutor.safeRunForDist(
            () -> VHAPISyncClientProxy::new,
            () -> VHAPISyncServerProxy::new);



    private void setup(final FMLCommonSetupEvent event)  {

    }

    public static ResourceLocation of(String name) {
        return new ResourceLocation(MODID, name);
    }

    private void onLogin(final PlayerEvent.PlayerLoggedInEvent event) {
        //Only servers ever need to send datapack syncs
        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
            if(VHAPIConfig.SERVER.syncDatapackConfigs.get()) {
                VHAPILoggerUtils.info("Sending VHAPI datapacks to " + event.getPlayer().getName().getString() + ".");
                //We are on server so send configs
                VHAPISyncNetwork.syncVHAPIConfigs(new VHAPISyncDescriptor(LoaderRegistry.VHAPI_DATA_LOADER.getCompressedConfigMap()), (ServerPlayer) event.getPlayer());
            }
        });

        //We don't want to reload configs on server every player login, this should only run client-side.
        if(event.getPlayer().level.isClientSide()) {
            VHAPILoggerUtils.debug("Rerunning Vault Configs load client-side to patch them.");
            ModConfigs.register();
            //Register bounty screen names
            if (BountyScreenAccessor.getObjectiveNames() != null) {
                BountyScreenAccessor.getObjectiveNames().putAll(VaultObjectiveRegistry.CUSTOM_BOUNTY_SCREEN_NAMES);
            }
        }
    }

    private void worldLoad(final WorldEvent.Load event)  {
        //This should only run on dedicated servers, we just want to reload configs once initially.
        if(event.getWorld().isClientSide()) {
            if(BountyScreenAccessor.getObjectiveNames() != null) {
                BountyScreenAccessor.getObjectiveNames().putAll(VaultObjectiveRegistry.CUSTOM_BOUNTY_SCREEN_NAMES);
            }
            return;
        }

        if(hasLoaded.get()) {
            return;
        }

        if(hasLoaded.compareAndSet(false, true)) {
            VHAPILoggerUtils.debug("Rerunning Vault Configs load server-side to patch them.");
            ModConfigs.register();
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
