package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog;

import iskallia.vault.dynamodel.DynamicBakedModel;
import iskallia.vault.dynamodel.DynamicModel;
import iskallia.vault.dynamodel.DynamicModelProperties;
import iskallia.vault.dynamodel.baked.JsonFileBakedModel;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistries;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModDynamicModels;
import iskallia.vault.item.InfusedCatalystItem;
import iskallia.vault.item.gear.VaultSwordItem;
import iskallia.vault.mixin.MixinModelBakery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.CallbackI;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib.GsonHandheldModel;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib.HandheldModelConfig;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.DynamicModelPropertiesAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.DynamicModelRegistryAccessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DynamicModelRegistryConfigLoader<T extends DynamicModelRegistry<HandHeldModel>> extends VaultConfigDataLoader<HandheldModelConfig> {

    public static final HandheldModelConfig instance = new HandheldModelConfig();

//    public static Map<ResourceLocation, BakedModel> modelRegistry;

    T registry;

    Item item;
    public DynamicModelRegistryConfigLoader(String namespace, DynamicModelRegistry<HandHeldModel> registry, @NotNull Item item) {
        super(instance, "gear/handheld_models/" + item.getRegistryName().getPath(), new HashMap<>(), namespace);
        this.registry = (T) registry;
        this.item = item;
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(HandheldModelConfig config : this.CUSTOM_CONFIGS.values()) {
            for(GsonHandheldModel model : config.MODELS) {
                DynamicModelProperties properties = new DynamicModelProperties();
                ((DynamicModelPropertiesAccessor)properties).setDiscoverOnRoll(model.isDiscoverOnRoll());
                ((DynamicModelPropertiesAccessor)properties).setAllowTransmogrification(model.isAllowTransmogrification());
                HandHeldModel handHeldModel = new HandHeldModel(model.getId(), model.getDisplayName()).properties(properties);
                registry.register(handHeldModel);
                if(Dist.CLIENT.isClient()) {
                    this.bakeModel(handHeldModel);
                }
            }

        }
        this.CUSTOM_CONFIGS.clear();
    }

    @OnlyIn(Dist.CLIENT)
    public void bakeModel(HandHeldModel handHeldModel) {
        ForgeModelBakery modelLoader = ForgeModelBakery.instance();
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        handHeldModel.getAssociatedModelLocations().forEach(modelResourceLocation -> {
            BlockModel unbakedModel = (BlockModel)modelLoader.getModel(modelResourceLocation);
            BakedModel bakedIcon;

            if(ModDynamicModels.jsonModelExists(resourceManager, DynamicModel.prependToId("item/", handHeldModel.getId()))) {
                System.out.println("Found json model!");
                bakedIcon = unbakedModel.bake(modelLoader, unbakedModel, ForgeModelBakery.defaultTextureGetter(), SimpleModelState.IDENTITY, modelResourceLocation, false);;
                bakedIcon = new JsonFileBakedModel(bakedIcon);
            }
            else {
                bakedIcon = handHeldModel.bakeModel(modelResourceLocation, modelLoader, unbakedModel);
            }
            ResourceLocation bakeId = new ResourceLocation(modelResourceLocation.getNamespace(), modelResourceLocation.getPath());
            this.registry.bakeIcon(bakeId, bakedIcon);
        });

//        if(modelRegistry != null) {
//            ModDynamicModels.REGISTRIES.getUniqueItems().forEach((item) -> {
//                ResourceLocation itemId = item.getRegistryName();
//                if (itemId == null) {
//                    throw new InternalError("Registry name does not exist for item -> " + item);
//                } else {
//                    ModelResourceLocation key = new ModelResourceLocation(itemId, "inventory");
//                    BakedModel oldModel = (BakedModel)modelRegistry.get(key);
//                    if (oldModel != null) {
//                        modelRegistry.put(key, new DynamicBakedModel(oldModel, modelLoader));
//                    }
//
//                }
//            });
//        }

    }




}
