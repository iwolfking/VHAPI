package xyz.iwolfking.vhapi;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
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
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.events.vault.VaultEvents;
import xyz.iwolfking.vhapi.api.registry.VaultGearRegistry;
import xyz.iwolfking.vhapi.api.registry.VaultObjectiveRegistry;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPIUtils;
import xyz.iwolfking.vhapi.config.VHAPIConfig;
import xyz.iwolfking.vhapi.mixin.accessors.BountyScreenAccessor;
import xyz.iwolfking.vhapi.networking.VHAPISyncDescriptor;
import xyz.iwolfking.vhapi.networking.VHAPISyncNetwork;
import xyz.iwolfking.vhapi.proxy.IVHAPISyncProxy;
import xyz.iwolfking.vhapi.proxy.client.VHAPISyncClientProxy;
import xyz.iwolfking.vhapi.proxy.server.VHAPISyncServerProxy;


import java.io.IOException;
import java.util.*;
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
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, VHAPIConfig.SERVER_SPEC, "vhapi-server.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, VHAPIConfig.COMMON_SPEC, "vhapi-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, VHAPIConfig.CLIENT_SPEC, "vhapi-client.toml");

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::worldLoad);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::onLogin);


        MinecraftForge.EVENT_BUS.addListener(LoaderRegistry::onAddListener);


        modEventBus.addListener(VaultObjectiveRegistry::newRegistry);
        modEventBus.addListener(VaultGearRegistry::newRegistry);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            MinecraftForge.EVENT_BUS.addListener(Client::onClientLogin);
            MinecraftForge.EVENT_BUS.addListener(Client::onClientLogout);
        });

        MinecraftForge.EVENT_BUS.register(this);
        VaultEvents.init();

    }

    public static final IVHAPISyncProxy PROXY = DistExecutor.safeRunForDist(
            () -> VHAPISyncClientProxy::new,
            () -> VHAPISyncServerProxy::new);



    private void setup(final FMLCommonSetupEvent event)  {
        if(VHAPIConfig.SERVER.syncDatapackConfigs.get()) {
            PROXY.init();
        }
    }

    public static ResourceLocation of(String name) {
        return new ResourceLocation(MODID, name);
    }


    private void onLogin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getPlayer().getLevel().isClientSide() || isLanHost(event.getPlayer().getServer())) {
            if (VHAPIConfig.SERVER.syncDatapackConfigs.get()) {
                if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
                    VHAPISyncNetwork.syncVHAPIConfigs(
                            new VHAPISyncDescriptor(LoaderRegistry.VHAPI_DATA_LOADER.getCompressedConfigMap()),
                            serverPlayer
                    );
                }
            }
        }
    }

    public static boolean isLanHost(MinecraftServer server) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            return server.isSingleplayer() && server.isPublished();
        }
        return false;
    }

    private void worldLoad(final WorldEvent.Load event)  {
        //This should only run on dedicated servers, we just want to reload configs once initially.
        if(event.getWorld().isClientSide()) {
            if(BountyScreenAccessor.getObjectiveNames() != null) {
                BountyScreenAccessor.getObjectiveNames().putAll(VaultObjectiveRegistry.CUSTOM_BOUNTY_SCREEN_NAMES);
            }
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
    public static class Client {
        public static final Map<String, List<ResourceLocation>> CUSTOM_TEXTURE_ATLAS_MAP = new HashMap<>();

        public static void onClientLogin(final ClientPlayerNetworkEvent.LoggedInEvent event) {
            VHAPILoggerUtils.debug("Rerunning Vault Configs load client-side to patch them.");
            VHAPIUtils.loadConfigs();

            //Handles adding the new objective names to display in Bounty tasks.
            if (BountyScreenAccessor.getObjectiveNames() != null) {
                BountyScreenAccessor.getObjectiveNames().putAll(VaultObjectiveRegistry.CUSTOM_BOUNTY_SCREEN_NAMES);
            }

            //If an ability isn't present during client setup, then it won't get its keybind assigned, this addresses that issue by replicating what happens during the initial client setup on login.
//            ModConfigs.ABILITIES
//                    .get()
//                    .ifPresent(
//                            tree -> tree.iterate(
//                                    SpecializedSkill.class,
//                                    skill -> {
//                                        if (skill.getSpecializations()
//                                                .stream()
//                                                .anyMatch(
//                                                        specialization -> !(
//                                                                specialization instanceof TieredSkill tieredSkill
//                                                                        && (tieredSkill.getMaxLearnableTier() <= 0 || tieredSkill.getChild(1) instanceof RemovedSkill)
//                                                        )
//                                                )) {
//                                            String name = "quickselect." + skill.getId().toLowerCase().replace(' ', '_');
//                                            ModKeybinds.abilityQuickfireKey.put(skill.getId(), ModKeybindsAccessor.mapping(ModKeybindsAccessor.name(name), KeyConflictContext.IN_GAME));
//                                        }
//                                    }
//                            )
//                    );
        }

        public static void onClientLogout(final ClientPlayerNetworkEvent.LoggedOutEvent event) {
            if(event.getPlayer() != null && isLanHost(event.getPlayer().getServer())) {
                return;
            }

            //VHAPILoggerUtils.debug("Clearing cached config data.");

            //VHAPIUtils.purgeConfigs();
        }


        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void textureStitch(TextureStitchEvent.Pre event) {
            if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {

                //STITCH ALL VAULT MODIFIER ICONS
                addSpritesToAtlas("MODIFIERS", "/textures/gui/modifiers");

                //STITCH ALL ABILITY ICONS
                addSpritesToAtlas("ABILITY", "/textures/gui/abilities");

                //STITCH ALL RESEARCH ICONS
                addSpritesToAtlas("RESEARCH", "/textures/gui/researches");

                //STITCH ALL RESEARCH GROUP ICONS
                addSpritesToAtlas("RESEARCH_GROUP", "/textures/gui/research_groups");

                //STITCH ALL RESEARCH GROUP ICONS
                addSpritesToAtlas("SKILLS", "/textures/gui/skills");

                //STITCH ALL VAULT MAP ICONS
                addSpritesToAtlas("VAULT_MAP", "/textures/gui/map");


                //Stitch all Gear Textures
                Collection<ResourceLocation> gearTextures = Minecraft.getInstance().getResourceManager().listResources("textures/item/gear", s -> s.endsWith(".png"));


                Collection<ResourceLocation> boosterTextures = Minecraft.getInstance().getResourceManager().listResources("textures/item/booster_pack", s -> s.endsWith(".png"));
                for(ResourceLocation loc : boosterTextures) {
                    if(!loc.getNamespace().equals("the_vault")) {
                        VHAPILoggerUtils.debug("Stitching custom booster pack texture: " + loc);
                        event.addSprite(ResourceLocUtils.stripLocationForSprite(loc));
                    }
                }


                Collection<ResourceLocation> deckTextures = Minecraft.getInstance().getResourceManager().listResources("textures/item/deck", s -> s.endsWith(".png"));
                for(ResourceLocation loc : deckTextures) {
                    if(!loc.getNamespace().equals("the_vault")) {
                        VHAPILoggerUtils.debug("Stitching custom deck texture: " + loc);
                        event.addSprite(ResourceLocUtils.stripLocationForSprite(loc));
                    }
                }

                for(ResourceLocation loc : gearTextures) {
                    if(!loc.getNamespace().equals("the_vault")) {
                        VHAPILoggerUtils.debug("Stitching custom transmog texture: " + loc);
                        event.addSprite(ResourceLocUtils.stripLocationForSprite(loc));
                    }
                }
            }
        }


        private static void addSpritesToAtlas(String type, String directory) {
            Collection<ResourceLocation> textures = Minecraft.getInstance().getResourceManager().listResources(directory, s -> s.endsWith(".png"));
            List<ResourceLocation> textureLocations = new ArrayList<>();

            if(textures.isEmpty()) {
                return;
            }

            for(ResourceLocation loc : textures) {
                textureLocations.add(ResourceLocUtils.stripLocationForSprite(loc));
            }

            CUSTOM_TEXTURE_ATLAS_MAP.put(type, textureLocations);
        }
    }



}
