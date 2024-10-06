package xyz.iwolfking.vaultcrackerlib;

import com.mojang.logging.LogUtils;
import iskallia.vault.dynamodel.DynamicModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModDynamicModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathResourcePack;
import org.cef.network.CefRequest;
import org.slf4j.Logger;
import xyz.iwolfking.vaultcrackerlib.api.LoaderRegistry;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.DynamicModelRegistryConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.registry.VaultGearRegistry;
import xyz.iwolfking.vaultcrackerlib.api.registry.VaultObjectiveRegistry;


import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("vaultcrackerlib")
public class VaultCrackerLib {


    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    private final AtomicBoolean hasLoaded = new AtomicBoolean();
    public static final String MODID = "vaultcrackerlib";

    public VaultCrackerLib() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::worldLoad);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, LoaderRegistry::onAddListener);


        modEventBus.addListener(VaultObjectiveRegistry::newRegistry);
        modEventBus.addListener(VaultGearRegistry::newRegistry);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)  {

    }

    @SubscribeEvent
    public static void textureStitch(TextureStitchEvent.Pre event) {
        System.out.println("STITCHING CAT");
        event.addSprite(new ResourceLocation("vaultcrackerlib", "textures/item/gear/wand/cat_wand.png"));
    }

    public static ResourceLocation of(String name) {
        return new ResourceLocation(MODID, name);
    }

//    public void addPackFinders(AddPackFindersEvent event) {
//        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
//            registerBuiltinResourcePack(event, new TextComponent("Custom Transmogs"), "custom_transmogs");
//        }
//    }
//
//    private static void registerBuiltinResourcePack(AddPackFindersEvent event, MutableComponent name, String folder) {
//        event.addRepositorySource((consumer, constructor) -> {
//            String path = VaultCrackerLib.of(folder).toString();
//            IModFile file = ModList.get().getModFileById(VaultCrackerLib.MODID).getFile();
//            try (PathResourcePack pack = new PathResourcePack(
//                    path,
//                    file.findResource("resourcepacks/" + folder));) {
//
//                consumer.accept(constructor.create(
//                        VaultCrackerLib.of(folder).toString(),
//                        name,
//                        false,
//                        () -> pack,
//                        Objects.requireNonNull(pack.getMetadataSection(PackMetadataSection.SERIALIZER)),
//                        Pack.Position.TOP,
//                        PackSource.BUILT_IN,
//                        false));
//
//            } catch (IOException e) {
//                if (!DatagenModLoader.isRunningDataGen())
//                    e.printStackTrace();
//            }
//        });
//    }


    private void worldLoad(final WorldEvent.Load event)  {
        if(hasLoaded.get()) {
            return;
        }

        if(hasLoaded.compareAndSet(false, true)) {
            ModConfigs.register();
        }

    }
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
    public static class Client {
        @SubscribeEvent
        public static void textureStitch(TextureStitchEvent.Pre event) {
            if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
                ResourceManager manager = Minecraft.getInstance().getResourceManager();
                Collection<ResourceLocation> textures = Minecraft.getInstance().getResourceManager().listResources("textures/item/gear", s -> s.endsWith(".png"));
                for(ResourceLocation loc : textures) {
                    if(loc.getNamespace().equals("custom_transmogs")) {
                        System.out.println("Stitching " + stripLocationForSprite(loc));
                        event.addSprite(stripLocationForSprite(loc));
                    }
                }
            }

        }
    }

    public static ResourceLocation stripLocationForSprite(ResourceLocation loc) {
        return VaultCrackerLib.removeSuffixFromId(".png", DynamicModel.removePrefixFromId("textures/", loc));
    }

    public static ResourceLocation removeSuffixFromId(String suffix, ResourceLocation id) {
        return id.getPath().endsWith(suffix) ? new ResourceLocation(id.getNamespace(), id.getPath().replaceFirst(suffix, "")) : id;
    }

}
