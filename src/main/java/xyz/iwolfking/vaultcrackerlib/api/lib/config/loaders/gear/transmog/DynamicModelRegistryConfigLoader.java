package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import iskallia.vault.client.gui.screen.block.TransmogTableScreen;
import iskallia.vault.config.GearModelRollRaritiesConfig;
import iskallia.vault.config.ResearchConfig;
import iskallia.vault.container.TransmogTableContainer;
import iskallia.vault.dynamodel.DynamicModel;
import iskallia.vault.dynamodel.DynamicModelProperties;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModDynamicModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib.GsonHandheldModel;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib.HandheldModelConfig;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Loaders;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.DynamicModelPropertiesAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.GearModelRollRaritiesAccessor;

import java.util.HashMap;
@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DynamicModelRegistryConfigLoader extends VaultConfigDataLoader<HandheldModelConfig> {

    public static final HandheldModelConfig instance = new HandheldModelConfig();


    public DynamicModelRegistryConfigLoader(String namespace) {
        super(instance, "gear/handheld_models", new HashMap<>(), namespace);
    }

    @SubscribeEvent
    public static void onAddListeners(AddReloadListenerEvent event) {
        event.addListener(Loaders.DYNAMIC_MODEL_REGISTRY_CONFIG_LOADER);
    }

    @SubscribeEvent
    public static void afterConfigsLoad(VaultConfigEvent.End event) {
        for(HandheldModelConfig config : Loaders.DYNAMIC_MODEL_REGISTRY_CONFIG_LOADER.CUSTOM_CONFIGS.values()) {
            for(GsonHandheldModel model : config.MODELS) {
                DynamicModelProperties properties = new DynamicModelProperties();
                ((DynamicModelPropertiesAccessor)properties).setDiscoverOnRoll(model.isDiscoverOnRoll());
                ((DynamicModelPropertiesAccessor)properties).setAllowTransmogrification(model.isAllowTransmogrification());
                HandHeldModel handHeldModel = new HandHeldModel(model.getId(), model.getDisplayName()).properties(properties);
                ModDynamicModels.Axes.REGISTRY.register(handHeldModel);

                if(Dist.CLIENT.isClient()) {
                    ForgeModelBakery modelLoader = ForgeModelBakery.instance();
                    BlockModel unbakedModel = (BlockModel)modelLoader.getModel(DynamicModel.prependToId("item/", model.getId()));
                    BakedModel bakedIcon;
                    if(ModDynamicModels.jsonModelExists(Minecraft.getInstance().getResourceManager(), DynamicModel.prependToId("item/", model.getId()))) {
                        bakedIcon = unbakedModel.bake(modelLoader, unbakedModel, ForgeModelBakery.defaultTextureGetter(), SimpleModelState.IDENTITY, DynamicModel.prependToId("item/", model.getId()), false);
                    }
                    else {
                        bakedIcon = handHeldModel.bakeModel((ModelResourceLocation) DynamicModel.prependToId("item/", model.getId()), modelLoader, unbakedModel);
                    }
                    ResourceLocation bakeId = new ResourceLocation(handHeldModel.getId().getNamespace(), handHeldModel.getId().getNamespace());
                    ModDynamicModels.Axes.REGISTRY.bakeIcon(bakeId, bakedIcon);
                }
            }

        }
        Loaders.DYNAMIC_MODEL_REGISTRY_CONFIG_LOADER.CUSTOM_CONFIGS.clear();
    }



}
