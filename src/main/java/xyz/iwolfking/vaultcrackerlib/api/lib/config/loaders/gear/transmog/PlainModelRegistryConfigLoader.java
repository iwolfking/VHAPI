package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog;

import iskallia.vault.dynamodel.DynamicModel;
import iskallia.vault.dynamodel.baked.JsonFileBakedModel;
import iskallia.vault.dynamodel.model.item.PlainItemModel;
import iskallia.vault.dynamodel.model.item.shield.ShieldModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.init.ModDynamicModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib.GsonHandheldModel;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib.HandheldModelConfig;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlainModelRegistryConfigLoader<T extends DynamicModelRegistry<PlainItemModel>> extends VaultConfigDataLoader<HandheldModelConfig> {

    public static final HandheldModelConfig instance = new HandheldModelConfig();

    T registry;

    Item item;
    public PlainModelRegistryConfigLoader(String namespace, DynamicModelRegistry<PlainItemModel> registry, @NotNull Item item) {
        super(new HandheldModelConfig(), "gear/plain_models/" + item.getRegistryName().getPath(), new HashMap<>(), namespace);
        this.registry = (T) registry;
        this.item = item;
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(HandheldModelConfig config : this.CUSTOM_CONFIGS.values()) {
            for(GsonHandheldModel model : config.MODELS) {
                PlainItemModel plainItemModel = model.getPlainModel();
                registry.register(plainItemModel);
                if(Dist.CLIENT.isClient()) {
                    this.bakeModel(plainItemModel);
                }
            }

        }
        this.CUSTOM_CONFIGS.clear();
    }


    @OnlyIn(Dist.CLIENT)
    public void bakeModel(PlainItemModel handHeldModel) {
        ForgeModelBakery modelLoader = ForgeModelBakery.instance();
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        handHeldModel.getAssociatedModelLocations().forEach(modelResourceLocation -> {
            BlockModel unbakedModel = (BlockModel)modelLoader.getModel(modelResourceLocation);
            BakedModel bakedIcon;

            if(ModDynamicModels.jsonModelExists(resourceManager, DynamicModel.prependToId("item/", handHeldModel.getId()))) {
                bakedIcon = unbakedModel.bake(modelLoader, unbakedModel, ForgeModelBakery.defaultTextureGetter(), SimpleModelState.IDENTITY, modelResourceLocation, false);;
                bakedIcon = new JsonFileBakedModel(bakedIcon);
            }
            else {
                bakedIcon = handHeldModel.bakeModel(modelResourceLocation, modelLoader, unbakedModel);
            }
            ResourceLocation bakeId = new ResourceLocation(modelResourceLocation.getNamespace(), modelResourceLocation.getPath());
            this.registry.bakeIcon(bakeId, bakedIcon);
        });

    }




}
